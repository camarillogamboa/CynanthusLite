package edu.cynanthus.common.resource;

/**
 * La interface Resource reader.
 *
 * @param <K> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
@FunctionalInterface
public interface ResourceReader<K, R> {

    /**
     * Read r.
     *
     * @param key el key
     * @return el r
     * @throws ResourceException el resource exception
     */
    R read(K key) throws ResourceException;

}
