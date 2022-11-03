package edu.cynanthus.microservice.net.http.server.engine;

import edu.cynanthus.common.reflect.BasicParameterizedType;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Objects;

/**
 * El tipo Typeable binding factory.
 */
public abstract class TypeableBindingFactory implements EvaluableBindingFactory {

    /**
     * El Parameter types.
     */
    private final Type[] parameterTypes;
    /**
     * El Return type.
     */
    private final Type returnType;

    /**
     * Instancia un nuevo Typeable binding factory.
     *
     * @param parameterTypes el parameter types
     * @param returnType     el return type
     */
    public TypeableBindingFactory(Type[] parameterTypes, Type returnType) {
        this.parameterTypes = Objects.requireNonNull(parameterTypes);
        this.returnType = Objects.requireNonNull(returnType);
    }

    /**
     * Get parameter types type [ ].
     *
     * @return el type [ ]
     */
    public final Type[] getParameterTypes() {
        return parameterTypes;
    }

    /**
     * Permite obtener return type.
     *
     * @return el return type
     */
    public final Type getReturnType() {
        return returnType;
    }

    /**
     * Check signature boolean.
     *
     * @param method     el method
     * @param ownerClass el owner class
     * @return el boolean
     */
    @Override
    public final boolean checkSignature(Method method, Class<?> ownerClass) {
        Type[] parameterTypes = method.getGenericParameterTypes();
        if (this.parameterTypes.length == parameterTypes.length) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if (!this.parameterTypes[i].equals(canonizeType(parameterTypes[i], method, ownerClass)))
                    return false;
            }
            return returnType.equals(method.getGenericReturnType());
        }
        return false;
    }

    /**
     * Canonize type type.
     *
     * @param type       el type
     * @param method     el method
     * @param ownerClass el owner class
     * @return el type
     */
    private Type canonizeType(Type type, Method method, Class<?> ownerClass) {
        if (type instanceof Class<?>) return type;
        else if (type instanceof ParameterizedType) {

            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            Type[] types = new Type[actualTypeArguments.length];

            for (int i = 0; i < types.length; i++)
                types[i] = canonizeType(actualTypeArguments[i], method, ownerClass);

            return new BasicParameterizedType(
                parameterizedType.getOwnerType(),
                (Class<?>) parameterizedType.getRawType(),
                types
            );
        } else if (type instanceof TypeVariable<?> && method.getDeclaringClass().isAssignableFrom(ownerClass)) {
            //Selección de tipo para caso específico
            Type superClassType = ownerClass.getGenericSuperclass();
            if (superClassType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) superClassType;
                return parameterizedType.getActualTypeArguments()[0];
            }
            return type;
        } else return type;
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return Arrays.toString(parameterTypes) + "->" + returnType;
    }

}
