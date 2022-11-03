package edu.cynanthus.common.resource;

/**
 * La interface Resource deleter.
 *
 * @param <K> el parámetro de tipo
 */
@FunctionalInterface
public interface ResourceDeleter<K> {

    /**
     * Delete.
     *
     * @param key el key
     * @throws ResourceException el resource exception
     */
    void delete(K key) throws ResourceException;

}
