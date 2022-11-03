package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpServer;
import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.microservice.net.http.server.HttpContextHandler;
import edu.cynanthus.microservice.net.http.server.HttpErrorManager;
import edu.cynanthus.microservice.net.http.server.HttpSecurityManager;

import java.util.Map;
import java.util.Objects;

/**
 * La interface Http context controller.
 */
public interface HttpContextController {

    /**
     * Permite obtener http error manager.
     *
     * @return el http error manager
     */
    HttpErrorManager getHttpErrorManager();

    /**
     * Permite obtener http security manager.
     *
     * @return el http security manager
     */
    HttpSecurityManager getHttpSecurityManager();

    /**
     * Load context definitions map.
     *
     * @return el map
     */
    default Map<String, Map<RequestMethod, HandlerSpecification>> loadContextDefinitions() {
        throw new UnsupportedOperationException();
    }

    /**
     * Bind context handlers.
     *
     * @param httpServer el http server
     * @param controller el controller
     */
    static void bindContextHandlers(HttpServer httpServer, HttpContextController controller) {
        Objects.requireNonNull(httpServer);

        try {
            Map<String, Map<RequestMethod, HandlerSpecification>> contextDefinitions =
                controller.loadContextDefinitions();

            HttpErrorManager httpErrorManager = controller.getHttpErrorManager();
            HttpSecurityManager httpSecurityManager = controller.getHttpSecurityManager();

            contextDefinitions.forEach(
                (name, contexts) -> {
                    HttpContextHandler httpContextHandler = new HttpContextHandler();
                    contexts.forEach((requestMethod, handlerSpecification) ->
                        httpContextHandler.getMethodHandlers().put(
                            requestMethod,
                            httpSecurityManager.wrapHandler(
                                handlerSpecification.getHttpHandler(),
                                handlerSpecification.getAuthorizedRoleNames()
                            )
                        )
                    );
                    httpServer.createContext(name, httpErrorManager.wrapHandler(httpContextHandler));
                }
            );

        } catch (UnsupportedOperationException ex) {
            try (ReflexiveLinkUtility utility = new ReflexiveLinkUtilityImpl()) {
                bindContextHandlers(httpServer, utility.toReflexiveHttpContextController(controller));
            }
        }
    }

}
