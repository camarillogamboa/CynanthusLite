package edu.cynanthus.microservice.property;

import edu.cynanthus.common.observable.Observable;
import edu.cynanthus.common.observable.Observer;
import edu.cynanthus.common.observable.UpdateObservable;
import edu.cynanthus.common.observable.event.UpdateEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * El tipo Observable property.
 *
 * @param <T> el parámetro de tipo
 */
public class ObservableProperty<T> extends Property<T> implements UpdateObservable<T> {

    /**
     * El Observers.
     */
    private final List<Observer<? super UpdateEvent<T>>> observers;

    /**
     * Instancia un nuevo Observable property.
     *
     * @param value el value
     */
    ObservableProperty(T value) {
        super(value);
        this.observers = new LinkedList<>();
    }

    /**
     * Permite establecer value.
     *
     * @param value el value
     */
    @Override
    public void setValue(T value) {
        T oldValue = this.value;
        super.setValue(value);
        dispatchEvent(new UpdateEvent<>(this, oldValue, value));
    }

    /**
     * Add observer.
     *
     * @param observer el observer
     */
    @Override
    public final void addObserver(Observer<? super UpdateEvent<T>> observer) {
        observers.add(observer);
    }

    /**
     * Remove observer.
     *
     * @param observer el observer
     */
    @Override
    public final void removeObserver(Observer<? super UpdateEvent<T>> observer) {
        observers.remove(observer);
    }

    /**
     * Delete observers.
     */
    @Override
    public final void deleteObservers() {
        observers.clear();
    }

    /**
     * Count observers int.
     *
     * @return el int
     */
    @Override
    public final int countObservers() {
        return observers.size();
    }

    /**
     * Share observers.
     *
     * @param observable el observable
     */
    @Override
    public final void shareObservers(Observable<UpdateEvent<T>> observable) {
        observers.forEach(observable::addObserver);
    }

    /**
     * Dispatch event.
     *
     * @param event el event
     */
    @Override
    public final void dispatchEvent(UpdateEvent<T> event) {
        observers.forEach(observer -> observer.update(event));
    }

    /**
     * Trigger now.
     */
    @Override
    public final void triggerNow() {
        dispatchEvent(new UpdateEvent<>(this, value, value));
    }

    /**
     * Clone observable property.
     *
     * @return el observable property
     */
    @Override
    public ObservableProperty<T> clone() {
        return Observable.shareObservers(this, new ObservableProperty<>(value));
    }

    /**
     * As observable property observable property.
     *
     * @param <T>   el parámetro de tipo
     * @param value el value
     * @return el observable property
     */
    public static <T> ObservableProperty<T> asObservableProperty(T value) {
        return new ObservableProperty<>(value);
    }

}
