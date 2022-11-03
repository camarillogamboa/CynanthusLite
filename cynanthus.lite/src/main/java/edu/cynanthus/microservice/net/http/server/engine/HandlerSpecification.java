package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpHandler;
import edu.cynanthus.common.security.SystemRole;

import java.util.Objects;

/**
 * El tipo Handler specification.
 */
public final class HandlerSpecification {

    /**
     * El Authorized role names.
     */
    private final SystemRole[] authorizedRoleNames;
    /**
     * El Http handler.
     */
    private final HttpHandler httpHandler;

    /**
     * Instancia un nuevo Handler specification.
     *
     * @param authorizedRoleNames el authorized role names
     * @param httpHandler         el http handler
     */
    public HandlerSpecification(SystemRole[] authorizedRoleNames, HttpHandler httpHandler) {
        this.authorizedRoleNames = Objects.requireNonNull(authorizedRoleNames);
        this.httpHandler = Objects.requireNonNull(httpHandler);
    }

    /**
     * Get authorized role names system role [ ].
     *
     * @return el system role [ ]
     */
    public SystemRole[] getAuthorizedRoleNames() {
        return authorizedRoleNames;
    }

    /**
     * Permite obtener http handler.
     *
     * @return el http handler
     */
    public HttpHandler getHttpHandler() {
        return httpHandler;
    }

}
