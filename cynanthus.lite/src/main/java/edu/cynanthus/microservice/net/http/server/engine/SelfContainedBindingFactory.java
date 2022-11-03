package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * El tipo Self contained binding factory.
 */
public class SelfContainedBindingFactory extends TypeableBindingFactory {

    /**
     * El Binding factory.
     */
    private final BindingFactory bindingFactory;

    /**
     * Instancia un nuevo Self contained binding factory.
     *
     * @param parameterTypes el parameter types
     * @param returnType     el return type
     * @param bindingFactory el binding factory
     */
    public SelfContainedBindingFactory(
        Type[] parameterTypes,
        Type returnType,
        BindingFactory bindingFactory
    ) {
        super(parameterTypes, returnType);
        this.bindingFactory = Objects.requireNonNull(bindingFactory);
    }

    /**
     * Create bound http handler http handler.
     *
     * @param method   el method
     * @param instance el instance
     * @return el http handler
     */
    @Override
    public final HttpHandler createBoundHttpHandler(Method method, Object instance) {
        return bindingFactory.createBoundHttpHandler(method, instance);
    }

}
