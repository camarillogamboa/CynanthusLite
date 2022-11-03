package edu.cynanthus.common.observable;

import edu.cynanthus.common.observable.event.Event;

/**
 * La interface Observer.
 *
 * @param <E> el parámetro de tipo
 */
@FunctionalInterface
public interface Observer<E extends Event> {

    /**
     * Update.
     *
     * @param event el event
     */
    void update(E event);

}
