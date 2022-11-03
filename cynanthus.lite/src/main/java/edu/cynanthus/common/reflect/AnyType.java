package edu.cynanthus.common.reflect;

import java.lang.reflect.Type;

/**
 * El tipo Any type.
 */
public final class AnyType implements Type {

    /**
     * El Last type compared.
     */
    private Type lastTypeCompared;

    /**
     * Permite obtener last type compared.
     *
     * @return el last type compared
     */
    public Type getLastTypeCompared() {
        return lastTypeCompared;
    }

    /**
     * Equals boolean.
     *
     * @param o el o
     * @return el boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Type) {
            lastTypeCompared = (Type) o;
            return true;
        }
        return false;
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return "?";
    }

}
