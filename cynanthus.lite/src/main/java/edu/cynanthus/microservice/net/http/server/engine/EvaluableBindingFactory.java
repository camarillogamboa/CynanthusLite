package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpHandler;

import java.lang.reflect.Method;

/**
 * La interface Evaluable binding factory.
 */
public interface EvaluableBindingFactory extends BindingFactory {

    /**
     * Check signature boolean.
     *
     * @param method     el method
     * @param ownerClass el owner class
     * @return el boolean
     */
    boolean checkSignature(Method method, Class<?> ownerClass);

    /**
     * Try create request controller http handler.
     *
     * @param method   el method
     * @param instance el instance
     * @return el http handler
     */
    default HttpHandler tryCreateRequestController(Method method, Object instance) {
        return checkSignature(method, instance.getClass()) ? createBoundHttpHandler(method, instance) : null;
    }

}
