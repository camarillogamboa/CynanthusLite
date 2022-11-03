package edu.cynanthus.common.observable;

import edu.cynanthus.common.observable.event.UpdateEvent;

import java.util.function.Consumer;

/**
 * La interface Update observable.
 *
 * @param <T> el parámetro de tipo
 */
public interface UpdateObservable<T> extends Observable<UpdateEvent<T>> {

    /**
     * Add as observer.
     *
     * @param consumer el consumer
     */
    default void addAsObserver(Consumer<T> consumer) {
        addObserver(event -> consumer.accept(event.getNewValue()));
    }

}
