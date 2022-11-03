package edu.cynanthus.common;

/**
 * La interface Formatter.
 *
 * @param <T> el parámetro de tipo
 */
@FunctionalInterface
public interface Formatter<T> {

    /**
     * Format string.
     *
     * @param value el value
     * @return el string
     */
    String format(T value);

}
