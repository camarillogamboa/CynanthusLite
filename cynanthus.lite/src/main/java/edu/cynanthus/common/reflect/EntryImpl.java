package edu.cynanthus.common.reflect;

import java.util.Map;
import java.util.Objects;

/**
 * El tipo Entry.
 *
 * @param <K> el parámetro de tipo
 * @param <V> el parámetro de tipo
 */
final class EntryImpl<K, V> implements Map.Entry<K, V> {

    /**
     * El Key.
     */
    private final K key;
    /**
     * El Value.
     */
    private V value;

    /**
     * Instancia un nuevo Entry.
     *
     * @param key   el key
     * @param value el value
     */
    EntryImpl(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Permite obtener key.
     *
     * @return el key
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Permite obtener value.
     *
     * @return el value
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * Permite establecer value.
     *
     * @param value el value
     * @return el value
     */
    @Override
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
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
        EntryImpl<?, ?> that = (EntryImpl<?, ?>) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value);
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return key + "=" + value;
    }

}
