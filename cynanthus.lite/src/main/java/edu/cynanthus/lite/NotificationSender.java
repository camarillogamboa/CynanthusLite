package edu.cynanthus.lite;

import edu.cynanthus.bean.BeanValidation;
import edu.cynanthus.common.LogicToggle;
import edu.cynanthus.domain.EmailTemplate;
import edu.cynanthus.domain.LiteSample;
import edu.cynanthus.microservice.Context;
import edu.cynanthus.microservice.nanoservice.ProcessNanoService;
import edu.cynanthus.microservice.property.ObservableProperty;
import edu.cynanthus.microservice.property.ReadOnlyProperty;

import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;

public final class NotificationSender extends ProcessNanoService {

    private final LinkedList<LiteSample> sampleBuffer;

    private final ReadOnlyProperty<Double> tempLimit;
    private final ReadOnlyProperty<Long> delay;

    private final EmailSender emailSender;
    private final EmailTemplate emailTemplate;

    private final LogicToggle toggle;

    private boolean limitExceeded;
    private Thread delayTask;

    public NotificationSender(String id, Context context, LinkedList<LiteSample> sampleBuffer) {
        super(id, context);
        this.sampleBuffer = sampleBuffer;

        this.tempLimit = getProperty("tempLimit").asReadOnlyDoubleProperty();
        ObservableProperty<String> username = getProperty("username");
        ObservableProperty<String> password = getProperty("password");

        ReadOnlyProperty<String> emailTemplatePath = getProperty("emailTemplatePath");
        ReadOnlyProperty<String> javaMailConfigPath = getProperty("javaMailPropertiesPath");

        this.delay = getProperty("delay").asReadOnlyLongProperty();

        this.emailTemplate = context.loadObject(emailTemplatePath.getValue(), EmailTemplate.class);
        if (emailTemplate == null) {
            logger.severe("Error al cargar la plantilla de correo electrónico," +
                "favor de verificar que el archivo exista y que contenga formato json correcto");
            System.exit(-1);
        }

        if (!BeanValidation.validate(emailTemplate).isEmpty()) {
            logger.severe("La plantilla de correo electrónico no cumple con los datos mínimos.");
            System.exit(-1);
        }

        Properties javaMailConfig = context.loadProperties(javaMailConfigPath.getValue());
        if (javaMailConfig == null) {
            logger.severe("Error al cargar el archivo de configuración de API JavaMail," +
                "favor de verificar que el archivo exista y que contenga formato de propiedades correcto");
            System.exit(-1);
        }

        this.emailSender = new EmailSender(
            username.getValue(),
            password.getValue(),
            javaMailConfig
        );

        this.limitExceeded = false;
        this.toggle = new LogicToggle();

        username.addAsObserver(emailSender::setUsername);
        password.addAsObserver(emailSender::setPassword);

        this.delayTask = null;
    }

    @Override
    public void processLoop() {

        if (!sampleBuffer.isEmpty()) {
            LiteSample sample;
            synchronized (sampleBuffer) {
                sample = sampleBuffer.pop();
            }
            processSample(sample);
        }

    }

    private void processSample(LiteSample sample) {
        if (isWorking()) {
            limitExceeded = sample.getTemp() > tempLimit.getValue();
            if (limitExceeded) {
                if (!toggle.isToggle()) {
                    toggle.doToggle();
                    sendNotification(sample);
                }
            } else if (delayTask == null && toggle.isToggle())
                (delayTask = new Thread(() -> {
                    try {
                        Thread.sleep(delay.getValue());
                        if (!limitExceeded)
                            toggle.untoggle();
                        delayTask = null;
                    } catch (InterruptedException e) {
                        logger.severe(e.getMessage());
                    }
                })).start();

        }
    }

    private void sendNotification(LiteSample lastSample) {
        Map<String, Object> values = new TreeMap<>(String::compareTo);

        values.put("temp", lastSample.getTemp());
        values.put("rssi", lastSample.getRssi());
        values.put("mac", lastSample.getMac());
        values.put("tempLimit", tempLimit.getValue());

        try {
            emailSender.sendEmail(emailTemplate.instance(values));
            logger.info("Correo electrónico de notificación enviado correctamente");
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
