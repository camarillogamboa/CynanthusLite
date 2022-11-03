package edu.cynanthus.common.observable.event;

/**
 * El tipo Update event.
 *
 * @param <T> el parámetro de tipo
 */
public class UpdateEvent<T> extends Event {

    /**
     * El Old value.
     */
    private final T oldValue;
    /**
     * El New value.
     */
    private final T newValue;

    /**
     * Instancia un nuevo Update event.
     *
     * @param source   el source
     * @param oldValue el old value
     * @param newValue el new value
     */
    public UpdateEvent(Object source, T oldValue, T newValue) {
        super(source);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Permite obtener old value.
     *
     * @return el old value
     */
    public T getOldValue() {
        return oldValue;
    }

    /**
     * Permite obtener new value.
     *
     * @return el new value
     */
    public T getNewValue() {
        return newValue;
    }

}
