package edu.cynanthus.common.resource;

/**
 * La interface Resource creator.
 *
 * @param <K> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
@FunctionalInterface
public interface ResourceCreator<K, R> {

    /**
     * Create.
     *
     * @param key      el key
     * @param resource el resource
     * @throws ResourceException el resource exception
     */
    void create(K key, R resource) throws ResourceException;

}
