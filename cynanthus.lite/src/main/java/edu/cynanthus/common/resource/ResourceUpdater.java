package edu.cynanthus.common.resource;

/**
 * La interface Resource updater.
 *
 * @param <K> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
@FunctionalInterface
public interface ResourceUpdater<K, R> {

    /**
     * Update.
     *
     * @param key      el key
     * @param resource el resource
     * @throws ResourceException el resource exception
     */
    void update(K key, R resource) throws ResourceException;

}
