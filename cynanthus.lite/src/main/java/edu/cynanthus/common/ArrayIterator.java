package edu.cynanthus.common;

import java.util.Iterator;
import java.util.Objects;

/**
 * El tipo Array iterator.
 *
 * @param <T> el parámetro de tipo
 */
public final class ArrayIterator<T> implements Iterator<T> {

    /**
     * El Array.
     */
    private final T[] array;

    /**
     * El Index.
     */
    private int index;

    /**
     * Instancia un nuevo Array iterator.
     *
     * @param array el array
     */
    public ArrayIterator(T[] array) {
        this.array = Objects.requireNonNull(array);
        this.index = 0;
    }

    /**
     * Has next boolean.
     *
     * @return el boolean
     */
    @Override
    public boolean hasNext() {
        return index < array.length;
    }

    /**
     * Next t.
     *
     * @return el t
     */
    @Override
    public T next() {
        return array[index++];
    }

}
