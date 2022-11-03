package edu.cynanthus.common.resource;

import java.util.*;

/**
 * La interface Resource acces object.
 *
 * @param <K> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
public interface ResourceAccesObject<K, R>
    extends ResourceCreator<K, R>, ResourceReader<K, R>, ResourceUpdater<K, R>, ResourceDeleter<K> {

    /**
     * Create functional instance resource acces object.
     *
     * @param <K>             el parámetro de tipo
     * @param <R>             el parámetro de tipo
     * @param resourceCreator el resource creator
     * @param resourceReader  el resource reader
     * @param resourceUpdater el resource updater
     * @param resourceDeleter el resource deleter
     * @return el resource acces object
     */
    static <K, R> ResourceAccesObject<K, R> createFunctionalInstance(
        ResourceCreator<K, R> resourceCreator,
        ResourceReader<K, R> resourceReader,
        ResourceUpdater<K, R> resourceUpdater,
        ResourceDeleter<K> resourceDeleter
    ) {
        Objects.requireNonNull(resourceCreator);
        Objects.requireNonNull(resourceReader);
        Objects.requireNonNull(resourceUpdater);
        Objects.requireNonNull(resourceDeleter);
        return new ResourceAccesObject<>() {

            @Override
            public void create(K key, R resource) throws ResourceException {
                resourceCreator.create(key, resource);
            }

            @Override
            public R read(K key) throws ResourceException {
                return resourceReader.read(key);
            }

            @Override
            public void update(K key, R resource) throws ResourceException {
                resourceUpdater.update(key, resource);
            }

            @Override
            public void delete(K key) throws ResourceException {
                resourceDeleter.delete(key);
            }

        };
    }

    /**
     * Map to resource acces object resource acces object.
     *
     * @param <K> el parámetro de tipo
     * @param <R> el parámetro de tipo
     * @param map el map
     * @return el resource acces object
     */
    static <K, R> ResourceAccesObject<K, R> mapToResourceAccesObject(Map<K, R> map) {
        Objects.requireNonNull(map);
        return createFunctionalInstance(map::put, map::get, map::replace, map::remove);
    }

    /**
     * Create map instance resource acces object.
     *
     * @param <K> el parámetro de tipo
     * @param <R> el parámetro de tipo
     * @return el resource acces object
     */
    static <K, R> ResourceAccesObject<K, R> createMapInstance() {
        return mapToResourceAccesObject(new HashMap<>());
    }

    /**
     * Create map instance resource acces object.
     *
     * @param <K>        el parámetro de tipo
     * @param <R>        el parámetro de tipo
     * @param comparator el comparator
     * @return el resource acces object
     */
    static <K, R> ResourceAccesObject<K, R> createMapInstance(Comparator<K> comparator) {
        Objects.requireNonNull(comparator);
        return mapToResourceAccesObject(new TreeMap<>(comparator));
    }

}
