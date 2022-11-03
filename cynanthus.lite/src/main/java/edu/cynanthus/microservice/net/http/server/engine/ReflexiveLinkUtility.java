package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpHandler;
import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.microservice.net.http.server.HttpErrorManager;
import edu.cynanthus.microservice.net.http.server.HttpSecurityManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * El tipo Reflexive link utility.
 */
public abstract class ReflexiveLinkUtility implements Predicate<Method>, AutoCloseable {

    /**
     * To reflexive http context controller http context controller.
     *
     * @param controller el controller
     * @return el http context controller
     */
    public final HttpContextController toReflexiveHttpContextController(HttpContextController controller) {
        return new HttpContextController() {

            final Map<String, Map<RequestMethod, HandlerSpecification>> contextDefinitions;

            {
                this.contextDefinitions = new HashMap<>();

                ServerPath serverPathNote = controller.getClass().getAnnotation(ServerPath.class);
                String serverPath = serverPathNote != null && serverPathNote.path() != null ?
                    serverPathNote.path() : "";

                for (Method method : controller.getClass().getMethods()) {
                    RequestHandler note = method.getAnnotation(RequestHandler.class);

                    if (note != null && test(method)) {
                        HttpHandler methodHandler;
                        for (EvaluableBindingFactory factory : getFactories())
                            if ((methodHandler = factory.tryCreateRequestController(method, controller)) != null) {
                                Map<RequestMethod, HandlerSpecification> contexts = contextDefinitions.computeIfAbsent(
                                    serverPath + note.context(), k -> new TreeMap<>()
                                );
                                contexts.put(
                                    note.method(),
                                    new HandlerSpecification(note.roles(), methodHandler)
                                );
                                break;
                            }
                    }
                }
            }

            @Override
            public HttpErrorManager getHttpErrorManager() {
                return controller.getHttpErrorManager();
            }

            @Override
            public HttpSecurityManager getHttpSecurityManager() {
                return controller.getHttpSecurityManager();
            }

            @Override
            public Map<String, Map<RequestMethod, HandlerSpecification>> loadContextDefinitions() {
                return contextDefinitions;
            }

        };
    }

    /**
     * Get factories evaluable binding factory [ ].
     *
     * @return el evaluable binding factory [ ]
     */
    public abstract EvaluableBindingFactory[] getFactories();

    /**
     * Close.
     */
    @Override
    public abstract void close();

}
