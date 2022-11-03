package edu.cynanthus.common.observable.event;

import java.util.Objects;

/**
 * El tipo Event.
 */
public class Event {

    /**
     * El Source.
     */
    private final Object source;

    /**
     * Instancia un nuevo Event.
     *
     * @param source el source
     */
    public Event(Object source) {
        this.source = Objects.requireNonNull(source);
    }

    /**
     * Permite obtener source.
     *
     * @return el source
     */
    public final Object getSource() {
        return source;
    }

}
