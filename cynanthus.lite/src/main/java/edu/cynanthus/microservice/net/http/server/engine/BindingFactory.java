package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpHandler;

import java.lang.reflect.Method;

/**
 * La interface Binding factory.
 */
@FunctionalInterface
public interface BindingFactory {

    /**
     * Create bound http handler http handler.
     *
     * @param method   el method
     * @param instance el instance
     * @return el http handler
     */
    HttpHandler createBoundHttpHandler(Method method, Object instance);

}
