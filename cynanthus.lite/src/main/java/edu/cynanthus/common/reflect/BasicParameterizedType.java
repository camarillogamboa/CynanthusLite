package edu.cynanthus.common.reflect;

import edu.cynanthus.common.SSV;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Objects;

/**
 * El tipo Basic parameterized type.
 */
public class BasicParameterizedType implements ParameterizedType {

    /**
     * El Owner type.
     */
    private final Type ownerType;
    /**
     * El Raw type.
     */
    private final Class<?> rawType;
    /**
     * El Actual type arguments.
     */
    private final Type[] actualTypeArguments;

    /**
     * Instancia un nuevo Basic parameterized type.
     *
     * @param ownerType           el owner type
     * @param rawType             el raw type
     * @param actualTypeArguments el actual type arguments
     */
    public BasicParameterizedType(Type ownerType, Class<?> rawType, Type... actualTypeArguments) {
        this.ownerType = ownerType;
        this.rawType = Objects.requireNonNull(rawType);
        this.actualTypeArguments = Objects.requireNonNull(actualTypeArguments);
        validateConstructorArguments();
    }

    /**
     * Validate constructor arguments.
     */
    private void validateConstructorArguments() {
        TypeVariable<?>[] formals = rawType.getTypeParameters();
        // check correct arity of actual type args
        if (formals.length != actualTypeArguments.length) {
            throw new MalformedParameterizedTypeException(String.format("Mismatch of count of " +
                    "formal and actual type " +
                    "arguments in constructor " +
                    "of %s: %d formal argument(s) " +
                    "%d actual argument(s)",
                rawType.getName(),
                formals.length,
                actualTypeArguments.length));
        }
    }

    /**
     * Permite obtener owner type.
     *
     * @return el owner type
     */
    @Override
    public Type getOwnerType() {
        return ownerType;
    }

    /**
     * Permite obtener raw type.
     *
     * @return el raw type
     */
    @Override
    public Class<?> getRawType() {
        return rawType;
    }

    /**
     * Get actual type arguments type [ ].
     *
     * @return el type [ ]
     */
    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments.clone();
    }

    /**
     * Equals boolean.
     *
     * @param o el o
     * @return el boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ParameterizedType) {
            ParameterizedType that = (ParameterizedType) o;

            if (this == that) return true;

            Type thatRawType = that.getRawType();

            return Objects.equals(rawType, thatRawType)
                && Arrays.equals(actualTypeArguments, that.getActualTypeArguments());
        } else return false;
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return rawType + "<" + SSV.toSSVFormat(actualTypeArguments, ",") + ">";
    }

}
