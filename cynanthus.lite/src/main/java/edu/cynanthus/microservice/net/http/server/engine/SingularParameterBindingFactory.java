package edu.cynanthus.microservice.net.http.server.engine;

import java.lang.reflect.Type;

/**
 * El tipo Singular parameter binding factory.
 */
public class SingularParameterBindingFactory extends SelfContainedBindingFactory {

    /**
     * Instancia un nuevo Singular parameter binding factory.
     *
     * @param parameterType  el parameter type
     * @param returnType     el return type
     * @param bindingFactory el binding factory
     */
    public SingularParameterBindingFactory(Type parameterType, Type returnType, BindingFactory bindingFactory) {
        super(new Type[]{parameterType}, returnType, bindingFactory);
    }

}
