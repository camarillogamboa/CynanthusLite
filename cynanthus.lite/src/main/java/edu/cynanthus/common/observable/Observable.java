package edu.cynanthus.common.observable;

import edu.cynanthus.common.observable.event.Event;

/**
 * La interface Observable.
 *
 * @param <E> el parámetro de tipo
 */
public interface Observable<E extends Event> extends Trigger {

    /**
     * Add observer.
     *
     * @param observer el observer
     */
    void addObserver(Observer<? super E> observer);

    /**
     * Remove observer.
     *
     * @param observer el observer
     */
    void removeObserver(Observer<? super E> observer);

    /**
     * Delete observers.
     */
    void deleteObservers();

    /**
     * Count observers int.
     *
     * @return el int
     */
    int countObservers();

    /**
     * Share observers.
     *
     * @param observable el observable
     */
    void shareObservers(Observable<E> observable);

    /**
     * Dispatch event.
     *
     * @param event el event
     */
    void dispatchEvent(E event);

    /**
     * Share observers t.
     *
     * @param <E>      el parámetro de tipo
     * @param <T>      el parámetro de tipo
     * @param source   el source
     * @param receiver el receiver
     * @return el t
     */
    static <E extends Event, T extends Observable<E>> T shareObservers(T source, T receiver) {
        source.shareObservers(receiver);
        return receiver;
    }

}
