package edu.cynanthus.microservice.property;

import java.util.Objects;

/**
 * El tipo Read only property.
 *
 * @param <T> el parámetro de tipo
 */
public class ReadOnlyProperty<T> {

    /**
     * El Value.
     */
    T value;

    /**
     * Instancia un nuevo Read only property.
     *
     * @param value el value
     */
    ReadOnlyProperty(T value) {
        this.value = value;
    }

    /**
     * Permite obtener value.
     *
     * @return el value
     */
    public final T getValue() {
        return value;
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
        if (o == null || getClass() != o.getClass()) return false;
        ReadOnlyProperty<?> that = (ReadOnlyProperty<?>) o;
        return Objects.equals(value, that.value);
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * As read only property read only property.
     *
     * @param <T>   el parámetro de tipo
     * @param value el value
     * @return el read only property
     */
    public static <T> ReadOnlyProperty<T> asReadOnlyProperty(T value) {
        return new ReadOnlyProperty<>(value);
    }

}
