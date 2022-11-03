package edu.cynanthus.microservice.net.http.server;

import com.sun.net.httpserver.HttpHandler;
import edu.cynanthus.common.net.http.HttpException;
import edu.cynanthus.common.net.http.HttpStatusCode;
import edu.cynanthus.common.security.Encryption;
import edu.cynanthus.common.security.SystemRole;
import edu.cynanthus.common.security.SystemUser;
import edu.cynanthus.microservice.SystemUserManagement;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * El tipo Http security manager.
 */
public final class HttpSecurityManager {

    /**
     * El User management.
     */
    private final SystemUserManagement userManagement;
    /**
     * El Context logger.
     */
    private final Logger contextLogger;

    /**
     * El Current user.
     */
    private SystemUser currentUser;

    /**
     * El Enabled.
     */
    private boolean enabled;

    /**
     * Instancia un nuevo Http security manager.
     *
     * @param userManagement el user management
     * @param contextLogger  el context logger
     */
    public HttpSecurityManager(SystemUserManagement userManagement, Logger contextLogger) {
        this.userManagement = Objects.requireNonNull(userManagement);
        this.contextLogger = Objects.requireNonNull(contextLogger);

        this.currentUser = userManagement.getMainUser();

        this.enabled = true;
    }

    /**
     * Permite obtener current user.
     *
     * @return el current user
     */
    public SystemUser getCurrentUser() {
        return currentUser;
    }

    /**
     * Is enabled boolean.
     *
     * @return el boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Permite establecer enabled.
     *
     * @param enabled el enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Log user action.
     *
     * @param level   el level
     * @param message el message
     */
    public void logUserAction(Level level, String message) {
        contextLogger.log(level, getCurrentUser() + " " + message);
    }

    /**
     * Wrap handler http handler.
     *
     * @param httpHandler           el http handler
     * @param authorizedSystemRoles el authorized system roles
     * @return el http handler
     */
    public HttpHandler wrapHandler(
        HttpHandler httpHandler,
        SystemRole[] authorizedSystemRoles
    ) {
        Objects.requireNonNull(httpHandler);
        Objects.requireNonNull(authorizedSystemRoles);
        return exchange -> {
            try {
                if (isEnabled()) {
                    List<String> credentials = exchange.getRequestHeaders().get("Credentials");
                    if (credentials != null && !credentials.isEmpty()) {
                        String credential = credentials.get(0);
                        authorize(currentUser = authenticate(credential.split(" ")), authorizedSystemRoles);
                    } else throw new HttpException(HttpStatusCode.UNAUTHORIZED);
                }
                httpHandler.handle(exchange);
            } catch (Throwable th) {
                currentUser = userManagement.getMainUser();
                throw th;
            }
        };
    }

    /**
     * Authenticate system user.
     *
     * @param credentials el credentials
     * @return el system user
     * @throws HttpException el http exception
     */
    private SystemUser authenticate(String[] credentials) throws HttpException {
        if (credentials.length == 2) {
            SystemUser systemUser;

            if (currentUser.getUsername().equals(credentials[0])) systemUser = currentUser;
            else systemUser = userManagement.getSystemUser(credentials[0]);

            if (systemUser != null) try {
                if (Encryption.decrypt(systemUser.getPassword()).equals(credentials[1]))
                    return systemUser;
            } catch (GeneralSecurityException e) {
                logUserAction(Level.WARNING, "Error de seguridad");
            }
        }
        contextLogger.warning("[user:" + credentials[0] + "] Intento fallido de auntenticación.");
        throw new HttpException(HttpStatusCode.UNAUTHORIZED);
    }

    /**
     * Authorize.
     *
     * @param systemUser            el system user
     * @param authorizedSystemRoles el authorized system roles
     * @throws HttpException el http exception
     */
    private void authorize(SystemUser systemUser, SystemRole[] authorizedSystemRoles) throws HttpException {
        if (authorizedSystemRoles.length > 0 && !systemUser.hasAnyRoles(authorizedSystemRoles)) {
            contextLogger.warning(systemUser + " Intento de petición de usuario no autorizado.");
            throw new HttpException(HttpStatusCode.FORBIDDEN);
        }
    }

}
