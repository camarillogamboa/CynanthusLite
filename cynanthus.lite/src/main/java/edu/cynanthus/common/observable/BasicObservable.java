package edu.cynanthus.common.observable;

import edu.cynanthus.common.observable.event.Event;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * El tipo Basic observable.
 *
 * @param <E> el parámetro de tipo
 */
public abstract class BasicObservable<E extends Event> implements Observable<E> {

    /**
     * El Observers.
     */
    private final List<Observer<? super E>> observers;

    {
        this.observers = new LinkedList<>();
    }

    /**
     * Add observer.
     *
     * @param observer el observer
     */
    @Override
    public void addObserver(Observer<? super E> observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    /**
     * Remove observer.
     *
     * @param observer el observer
     */
    @Override
    public void removeObserver(Observer<? super E> observer) {
        observers.remove(Objects.requireNonNull(observer));
    }

    /**
     * Delete observers.
     */
    @Override
    public void deleteObservers() {
        observers.clear();
    }

    /**
     * Count observers int.
     *
     * @return el int
     */
    @Override
    public int countObservers() {
        return observers.size();
    }

    /**
     * Share observers.
     *
     * @param observable el observable
     */
    @Override
    public void shareObservers(Observable<E> observable) {
        observers.forEach(observable::addObserver);
    }

    /**
     * Dispatch event.
     *
     * @param event el event
     */
    @Override
    public void dispatchEvent(E event) {
        observers.forEach(observer -> observer.update(event));
    }

}
