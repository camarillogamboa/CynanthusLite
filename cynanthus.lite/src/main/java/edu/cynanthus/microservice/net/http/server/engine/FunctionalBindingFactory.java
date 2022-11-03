package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpHandler;
import edu.cynanthus.common.resource.Exchanger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * El tipo Functional binding factory.
 *
 * @param <T> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
public class FunctionalBindingFactory<T, R> extends SingularParameterBindingFactory {

    /**
     * Instancia un nuevo Functional binding factory.
     *
     * @param parameterType el parameter type
     * @param returnType    el return type
     * @param factory       el factory
     */
    public FunctionalBindingFactory(
        Type parameterType,
        Type returnType,
        Function<Exchanger<T, R>, HttpHandler> factory
    ) {
        super(
            parameterType,
            returnType,
            (method, instance) -> factory.apply(param -> invoke(method, instance, param))
        );
    }

    /**
     * Invoke t.
     *
     * @param <T>        el parámetro de tipo
     * @param method     el method
     * @param instance   el instance
     * @param parameters el parameters
     * @return el t
     * @throws IOException el io exception
     */
    private static <T> T invoke(Method method, Object instance, Object... parameters) throws IOException {
        try {
            return (T) method.invoke(instance, parameters);
        } catch (IllegalAccessException ex) {
            throw new IOException(ex);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException) throwable;
            }
            if (throwable instanceof IOException) {
                throw (IOException) throwable;
            } else throw new IOException(throwable);
        }
    }

}
