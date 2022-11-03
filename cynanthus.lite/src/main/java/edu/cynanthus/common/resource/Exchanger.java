package edu.cynanthus.common.resource;

import java.io.IOException;

/**
 * La interface Exchanger.
 *
 * @param <T> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
@FunctionalInterface
public interface Exchanger<T, R> {

    /**
     * Exchange r.
     *
     * @param value el value
     * @return el r
     * @throws IOException el io exception
     */
    R exchange(T value) throws IOException;

    /**
     * Identity exchanger.
     *
     * @param <T> el parámetro de tipo
     * @return el exchanger
     */
    static <T> Exchanger<T, T> identity() {
        return value -> value;
    }

}
