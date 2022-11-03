package edu.cynanthus.microservice;

import edu.cynanthus.bean.Config;
import edu.cynanthus.bean.FieldInformationSeeker;
import edu.cynanthus.common.security.AgentUser;
import edu.cynanthus.microservice.nanoservice.NanoService;
import edu.cynanthus.microservice.nanoservice.SingleNanoService;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * El tipo Micro service.
 */
public abstract class MicroService extends SingleNanoService {

    /**
     * El Service.
     */
    private final NanoService service;

    /**
     * Instancia un nuevo Micro service.
     *
     * @param name                   el name
     * @param configClass            el config class
     * @param mainUser               el main user
     * @param fieldInformationSeeker el field information seeker
     * @param args                   el args
     */
    public MicroService(
        String name,
        Class<? extends Config> configClass,
        AgentUser mainUser,
        Function<Field, String[]> fieldInformationSeeker,
        String[] args
    ) {
        super(name);
        Context context = new ArchiveableContextImpl(
            getClass().getModule().getName(),
            configClass,
            mainUser,
            fieldInformationSeeker,
            args
        );
        this.service = loadNanoService(context);
    }

    /**
     * Instancia un nuevo Micro service.
     *
     * @param name        el name
     * @param configClass el config class
     * @param mainUser    el main user
     * @param args        el args
     */
    public MicroService(
        String name,
        Class<? extends Config> configClass,
        AgentUser mainUser,
        String[] args
    ) {
        this(name, configClass, mainUser, FieldInformationSeeker.INSTANCE, args);
    }

    /**
     * Start.
     */
    @Override
    public final void start() {
        if (!working) {
            service.start();
            working = true;
        }
    }

    /**
     * Stop.
     */
    @Override
    public final void stop() {
        if (working) {
            service.stop();
            working = false;
        }
    }

    /**
     * Load nano service nano service.
     *
     * @param context el context
     * @return el nano service
     */
    public abstract NanoService loadNanoService(Context context);

}
