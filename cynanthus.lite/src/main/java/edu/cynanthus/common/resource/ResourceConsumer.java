package edu.cynanthus.common.resource;

/**
 * La interface Resource consumer.
 *
 * @param <R> el parámetro de tipo
 */
@FunctionalInterface
public interface ResourceConsumer<R> {

    /**
     * Accept.
     *
     * @param resource el resource
     * @throws ResourceException el resource exception
     */
    void accept(R resource) throws ResourceException;

}
