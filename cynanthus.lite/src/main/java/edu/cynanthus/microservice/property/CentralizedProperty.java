package edu.cynanthus.microservice.property;

import edu.cynanthus.common.observable.Observable;
import edu.cynanthus.common.observable.Observer;
import edu.cynanthus.common.observable.event.UpdateEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * El tipo Centralized property.
 *
 * @param <T> el parámetro de tipo
 */
public class CentralizedProperty<T> extends ObservableProperty<T> {

    /**
     * El Notifiers.
     */
    private final List<Observer<UpdateEvent<T>>> notifiers;

    /**
     * Instancia un nuevo Centralized property.
     *
     * @param value el value
     */
    CentralizedProperty(T value) {
        super(value);
        this.notifiers = new LinkedList<>();
    }

    /**
     * Permite establecer value.
     *
     * @param value el value
     */
    @Override
    public final void setValue(T value) {
        centralizedSetValue(value, null);
    }

    /**
     * Centralized set value.
     *
     * @param value     el value
     * @param skippable el skippable
     */
    private synchronized void centralizedSetValue(T value, Observer<UpdateEvent<T>> skippable) {
        T oldValue = this.value;
        this.value = value;
        UpdateEvent<T> event = new UpdateEvent<>(this, oldValue, value);
        dispatchEvent(event);
        notifiers.forEach(skippable != null ? notifier -> {
            if (!notifier.equals(skippable)) notifier.update(event);
        } : notifier -> notifier.update(event));
    }

    /**
     * As other read only property read only property.
     *
     * @param <O>      el parámetro de tipo
     * @param function el function
     * @return el read only property
     */
    public final <O> ReadOnlyProperty<O> asOtherReadOnlyProperty(Function<T, O> function) {
        return new ReadOnlyProperty<>(function.apply(value)) {{
            notifiers.add(event -> value = function.apply(event.getNewValue()));
        }};
    }

    /**
     * As other property property.
     *
     * @param <O>                 el parámetro de tipo
     * @param toOtherTypeFunction el to other type function
     * @param otherTypeFunction   el other type function
     * @return el property
     */
    public final <O> Property<O> asOtherProperty(Function<T, O> toOtherTypeFunction, Function<O, T> otherTypeFunction) {
        Objects.requireNonNull(otherTypeFunction);
        return new Property<>(toOtherTypeFunction.apply(value)) {

            final Observer<UpdateEvent<T>> notifier;

            {
                notifiers.add(notifier = event -> super.setValue(toOtherTypeFunction.apply(event.getNewValue())));
            }

            @Override
            public void setValue(O value) {
                super.setValue(value);
                centralizedSetValue(otherTypeFunction.apply(value), notifier);
            }

        };
    }

    /**
     * As other observable property observable property.
     *
     * @param <O>                 el parámetro de tipo
     * @param toOtherTypeFunction el to other type function
     * @param otherTypeFunction   el other type function
     * @return el observable property
     */
    public final <O> ObservableProperty<O> asOtherObservableProperty(
        Function<T, O> toOtherTypeFunction,
        Function<O, T> otherTypeFunction
    ) {
        Objects.requireNonNull(otherTypeFunction);
        return new ObservableProperty<>(toOtherTypeFunction.apply(value)) {

            final Observer<UpdateEvent<T>> notifier;

            {
                notifiers.add(notifier = event -> super.setValue(toOtherTypeFunction.apply(event.getNewValue())));
            }

            @Override
            public void setValue(O value) {
                super.setValue(value);
                centralizedSetValue(otherTypeFunction.apply(value), notifier);
            }

        };
    }

    /**
     * Clone centralized property.
     *
     * @return el centralized property
     */
    @Override
    public CentralizedProperty<T> clone() {
        return Observable.shareObservers(this, new CentralizedProperty<>(value));
    }

    /**
     * As centralized property centralized property.
     *
     * @param <T>   el parámetro de tipo
     * @param value el value
     * @return el centralized property
     */
    public static <T> CentralizedProperty<T> asCentralizedProperty(T value) {
        return new CentralizedProperty<>(value);
    }

}
