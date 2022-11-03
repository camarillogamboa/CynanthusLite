package edu.cynanthus.microservice.property;

/**
 * El tipo Property.
 *
 * @param <T> el parámetro de tipo
 */
public class Property<T> extends ReadOnlyProperty<T> {

    /**
     * El Lock.
     */
    private final Object lock;

    /**
     * Instancia un nuevo Property.
     *
     * @param value el value
     */
    Property(T value) {
        super(value);
        this.lock = new Object();
    }

    /**
     * Permite establecer value.
     *
     * @param value el value
     */
    public void setValue(T value) {
        synchronized (lock) {
            this.value = value;
        }
    }

    /**
     * Clone property.
     *
     * @return el property
     */
    @Override
    public Property<T> clone() {
        return new Property<>(value);
    }

    /**
     * As property property.
     *
     * @param <T>   el parámetro de tipo
     * @param value el value
     * @return el property
     */
    public static <T> Property<T> asProperty(T value) {
        return new Property<>(value);
    }

}
