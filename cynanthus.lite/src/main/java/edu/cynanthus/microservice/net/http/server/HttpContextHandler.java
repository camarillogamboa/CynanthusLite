package edu.cynanthus.microservice.net.http.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.cynanthus.common.net.http.HttpException;
import edu.cynanthus.common.net.http.HttpStatusCode;
import edu.cynanthus.common.net.http.RequestMethod;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * El tipo Http context handler.
 */
public final class HttpContextHandler implements HttpHandler {

    /**
     * El Method handlers.
     */
    private final Map<RequestMethod, HttpHandler> methodHandlers;

    /**
     * Instancia un nuevo Http context handler.
     */
    public HttpContextHandler() {
        this.methodHandlers = new TreeMap<>(RequestMethod::compareTo);
    }

    /**
     * Permite obtener method handlers.
     *
     * @return el method handlers
     */
    public Map<RequestMethod, HttpHandler> getMethodHandlers() {
        return methodHandlers;
    }

    /**
     * Handle.
     *
     * @param exchange el exchange
     * @throws IOException el io exception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RequestMethod requestMethod;
        try {
            requestMethod = RequestMethod.valueOf(exchange.getRequestMethod());
        } catch (IllegalArgumentException ex) {
            throw new HttpException(HttpStatusCode.NOT_IMPLEMENTED, ex);
        }
        HttpHandler methodHandler = methodHandlers.get(requestMethod);
        if (methodHandler != null) methodHandler.handle(exchange);
        else throw new HttpException(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

}
