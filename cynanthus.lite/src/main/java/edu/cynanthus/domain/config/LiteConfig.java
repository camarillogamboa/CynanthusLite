package edu.cynanthus.domain.config;

import edu.cynanthus.bean.Config;
import edu.cynanthus.bean.JProperty;
import edu.cynanthus.bean.Patterns;
import edu.cynanthus.bean.ValidInfo;

import javax.validation.constraints.*;
import java.util.Objects;

public class LiteConfig implements Config {

    @NotNull(message = "#{NotNull.liteConfig.port}")
    @Min(value = 0, groups = ValidInfo.class, message = "#{Min.liteConfig.port}")
    @Max(value = 65536, groups = ValidInfo.class, message = "#{Max.liteConfig.port}")
    @JProperty(
        alias = "cynanthus.lite.webServer.port",
        defaultValue = "8001",
        info = "Puerto del servidor web"
    )
    private Integer port;

    @NotEmpty(message = "#{NotEmpty.liteConfig.nodeMac}")
    @Pattern(regexp = Patterns.MAC, groups = ValidInfo.class, message = "#{Pattern.liteConfig.nodeMac}")
    @JProperty(
        alias = "cynanthus.lite.webServer.nodeMac",
        defaultValue = "00:00:00:00:00:00",
        info = "Dirección mac del nodo que enviará las muestras. Esta mac será utilizada para establecer\n un filtro" +
            "con el que se denegará la recepción de los datos si el cliente no cumple con la mac especificada.\n"
            + "Si no se desea establecer el filtrado por mac, comente esta propiedad."
    )
    private String nodeMac;

    @NotNull(message = "#{NotNull.liteConfig.tempLimit}")
    @Min(value = 0, groups = ValidInfo.class, message = "#{Min.liteConfig.tempLimit}")
    @Max(value = 100, groups = ValidInfo.class, message = "#{Max.liteConfig.tempLimit}")
    @JProperty(
        alias = "cynanthus.lite.notificationSender.tempLimit",
        defaultValue = "26.0",
        info = "Límite de temperatura, que de ser superado, el programa emitirá una notificación vía email"
    )
    private Float tempLimit;

    @NotEmpty(message = "#{NotEmpty.liteConfig.username}")
    @JProperty(
        alias = "cynanthus.lite.notificationSender.username",
        defaultValue = "username@mail.com",
        info = "Nombre de usuario utilizado para iniciar sesión en los servicios de correo electrónico"
    )
    private String username;

    @NotEmpty(message = "#{NotEmpty.liteConfig.password}")
    @JProperty(
        alias = "cynanthus.lite.notificationSender.password",
        defaultValue = "*****",
        info = "Contraseña utilizada para iniciar sesión en los servicios de correo electrónico"
    )
    private String password;

    @NotEmpty(message = "#{NotEmpty.liteConfig.emailTemplatePath}")
    @JProperty(
        alias = "cynanthus.lite.notificationSender.emailTemplatePath",
        defaultValue = "emailTemplate.json",
        info = "Ruta al archivo de plantilla de correo electrónico"
    )
    private String emailTemplatePath;

    @NotEmpty(message = "#{NotEmpty.liteConfig.javaMailConfigPath}")
    @JProperty(
        alias = "cynanthus.lite.notificationSender.javaMailPropertiesPath",
        defaultValue = "javamail.properties",
        info = "Ruta al archivo de configuración del API JavaMail"
    )
    private String javaMailPropertiesPath;
    @NotNull(message = "#{NotNull.liteConfig.rate}")
    @JProperty(
        alias = "cynanthus.lite.notificationSender.rate",
        defaultValue = "30000",
        info = "Tiempo de retardo utilizado como medida precautoria ante posibles envios\n" +
            "repetidos de notificaciones de temperatura. Cuando la temperatura mínima es rebasada,\n" +
            "se envía una notificación al respecto. Mientras el dispositivo de sensado\n" +
            "siga enviando valores de temperatura superiores al límite, no se enviará ninguna otra notificación.\n" +
            "Cuando los valores de temperatura bajen y vuelvan a ser menores al límite la posibilidad de enviar otra notificación\n" +
            "de temperatura volverá a estar abierta, asì, cuando los valores vuelvan a subir por encima del límite, \n" +
            "una nueva notificación será disparada. Existe la posibilidad de que los valores de temperatura oscilen \n" +
            "muy cercanamente por arriba y por debajo del límite, esto produciría el envío de repetidas notificaciones \n" +
            "muy seguidas una de la otra. Para evitar este problema, se esteblece un valor de tolerancia o \"retraso\" (delay)\n" +
            "para cuando la temperatura, luego de haber estado por encima del límite, se encuentre por debajo del límite, se produzca\n" +
            "un \"tiempo muerto\". Si dentro de ese intervalo de \"tiempo muerto\" la temperatura vuelve a subir por encima del límite,\n" +
            "no se enviará ninguna notificación. Si la temperatura sube sobre el límite despues de haber concluido el retraso o \"tiempo muerto\",\n" +
            "la notificación será disparada. Para ilustrar este proceso observe la imagen incluida en el mismo directorio\n" +
            "donde se encuentra este archivo."
    )
    private Long delay;

    public LiteConfig(
        Integer port,
        String nodeMac,
        Float tempLimit,
        String username,
        String password,
        String emailTemplatePath,
        String javaMailPropertiesPath,
        Long delay
    ) {
        this.port = port;
        this.nodeMac = nodeMac;
        this.tempLimit = tempLimit;
        this.username = username;
        this.password = password;
        this.emailTemplatePath = emailTemplatePath;
        this.javaMailPropertiesPath = javaMailPropertiesPath;
        this.delay = delay;
    }

    public LiteConfig() {
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getNodeMac() {
        return nodeMac;
    }

    public void setNodeMac(String nodeMac) {
        this.nodeMac = nodeMac;
    }

    public Float getTempLimit() {
        return tempLimit;
    }

    public void setTempLimit(Float tempLimit) {
        this.tempLimit = tempLimit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailTemplatePath() {
        return emailTemplatePath;
    }

    public void setEmailTemplatePath(String emailTemplatePath) {
        this.emailTemplatePath = emailTemplatePath;
    }

    public String getJavaMailPropertiesPath() {
        return javaMailPropertiesPath;
    }

    public void setJavaMailPropertiesPath(String javaMailPropertiesPath) {
        this.javaMailPropertiesPath = javaMailPropertiesPath;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    @Override
    public LiteConfig clone() {
        return new LiteConfig(port, nodeMac, tempLimit, username, password, emailTemplatePath, javaMailPropertiesPath, delay);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteConfig that = (LiteConfig) o;
        return Objects.equals(port, that.port)
            && Objects.equals(nodeMac, that.nodeMac)
            && Objects.equals(tempLimit, that.tempLimit)
            && Objects.equals(username, that.username)
            && Objects.equals(password, that.password)
            && Objects.equals(emailTemplatePath, that.emailTemplatePath)
            && Objects.equals(javaMailPropertiesPath, that.javaMailPropertiesPath)
            && Objects.equals(delay, that.delay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, nodeMac, tempLimit, username, password, emailTemplatePath, javaMailPropertiesPath, delay);
    }

    @Override
    public String toString() {
        return "{" +
            "port:" + port +
            ",nodeMac:'" + nodeMac + '\'' +
            ",tempLimit:" + tempLimit +
            ",username:'" + username + '\'' +
            ",password:'" + "*******" + '\'' +
            ",emailTemplatePath:'" + emailTemplatePath + '\'' +
            ",javaMailPropertiesPath:'" + javaMailPropertiesPath + '\'' +
            ",rate:" + delay +
            '}';
    }

}
