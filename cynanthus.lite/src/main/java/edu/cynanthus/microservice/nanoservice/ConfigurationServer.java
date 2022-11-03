package edu.cynanthus.microservice.nanoservice;

import edu.cynanthus.bean.BeanValidation;
import edu.cynanthus.bean.Config;
import edu.cynanthus.bean.FieldAliasFinder;
import edu.cynanthus.bean.ValidInfo;
import edu.cynanthus.common.net.http.HttpException;
import edu.cynanthus.common.net.http.HttpStatusCode;
import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.common.resource.FileAccesObject;
import edu.cynanthus.common.resource.ResourceException;
import edu.cynanthus.common.security.SystemRole;
import edu.cynanthus.common.security.SystemUser;
import edu.cynanthus.microservice.ArchiveableContext;
import edu.cynanthus.microservice.Context;
import edu.cynanthus.microservice.SystemUserManagement;
import edu.cynanthus.microservice.net.http.server.engine.RequestHandler;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;

/**
 * El tipo Configuration server.
 *
 * @param <T> el parámetro de tipo
 */
public class ConfigurationServer<T extends Config> extends WebServer {

    /**
     * El Identifier generator.
     */
    private final Function<Field, String> fieldAliasFinder;

    /**
     * Instancia un nuevo Configuration server.
     *
     * @param id               el id
     * @param context          el context
     * @param fieldAliasFinder el field alias finder
     */
    public ConfigurationServer(String id, Context context, Function<Field, String> fieldAliasFinder) {
        super(id, context);
        this.fieldAliasFinder = Objects.requireNonNull(fieldAliasFinder);
    }

    /**
     * Instancia un nuevo Configuration server.
     *
     * @param id      el id
     * @param context el context
     */
    public ConfigurationServer(String id, Context context) {
        this(id, context, FieldAliasFinder.INSTANCE);
    }

    /**
     * Permite obtener config.
     *
     * @param string el string
     * @return el config
     * @throws HttpException el http exception
     */
    @RequestHandler(context = "/config", method = RequestMethod.GET, roles = SystemRole.ROLE_AGENT)
    public final T getConfig(String string) throws HttpException {
        try {
            return context.getMetaPropertiesAsConfigObject();
        } catch (Exception e) {
            throw new HttpException(HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update config string.
     *
     * @param configObject el config object
     * @return el string
     * @throws HttpException el http exception
     */
    @RequestHandler(context = "/config", method = RequestMethod.PUT, roles = SystemRole.ROLE_AGENT)
    public final Boolean updateConfig(T configObject) throws HttpException {
        if (configObject != null) {
            BeanValidation.validateAndThrow(configObject, ValidInfo.class);
            if (context.updateMetaPropertiesFrom(configObject, fieldAliasFinder)) {
                httpSecurityManager.logUserAction(Level.INFO, "Cambió la configuración del programa");
                return true;
            }
            return false;
        }
        throw new HttpException(HttpStatusCode.BAD_REQUEST);
    }

    /**
     * Get log files string [ ].
     *
     * @param string el string
     * @return el string [ ]
     * @throws HttpException el http exception
     */
    @RequestHandler(context = "/log/files", method = RequestMethod.GET, roles = SystemRole.ROLE_AGENT)
    public final String[] getLogFiles(String string) throws HttpException {
        ArchiveableContext archiveableContext = (ArchiveableContext) context;
        File logDirectory = archiveableContext.getLogDirectory();
        if (logDirectory.exists())
            return logDirectory.list((dir, name) -> name.endsWith(".log"));
        throw new HttpException(HttpStatusCode.NOT_FOUND);
    }

    /**
     * Permite obtener log content.
     *
     * @param logFileName el log file name
     * @return el log content
     * @throws HttpException el http exception
     */
    @RequestHandler(context = "/log", method = RequestMethod.GET, roles = SystemRole.ROLE_AGENT)
    public final InputStream getLogContent(String logFileName) throws HttpException {
        ArchiveableContext archiveableContext = (ArchiveableContext) context;
        File logDirectory = archiveableContext.getLogDirectory();
        if (logDirectory.exists()) {
            File logFile = new File(logDirectory + "/" + logFileName);
            if (logFile.exists())
                try {
                    return FileAccesObject.INPUT_STREAM_FAO.read(logFile);
                } catch (ResourceException e) {
                    throw new HttpException(HttpStatusCode.INTERNAL_SERVER_ERROR, e);
                }
        }
        throw new HttpException(HttpStatusCode.NOT_FOUND);
    }

    /**
     * Update user string.
     *
     * @param systemUsers el system user
     * @return el string
     * @throws HttpException el http exception
     */
    @RequestHandler(context = "/user", method = RequestMethod.PUT, roles = SystemRole.ROLE_AGENT)
    public final Boolean updateUser(SystemUser[] systemUsers) throws HttpException {
        SystemUserManagement userManagement = context.getUserManagement();

        userManagement.saveSystemUsers();
        return true;
    }

}
