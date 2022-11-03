package edu.cynanthus.microservice.nanoservice;

import java.util.Objects;

/**
 * El tipo Basic nano service.
 */
public abstract class BasicNanoService implements NanoService {

    /**
     * El Id.
     */
    private final String id;

    /**
     * El Working.
     */
    protected volatile boolean working;

    /**
     * Instancia un nuevo Basic nano service.
     *
     * @param id el id
     */
    public BasicNanoService(String id) {
        this.id = Objects.requireNonNull(id);
        this.working = false;
    }

    /**
     * Permite obtener id.
     *
     * @return el id
     */
    @Override
    public final String getId() {
        return id;
    }

    /**
     * Is working boolean.
     *
     * @return el boolean
     */
    @Override
    public final boolean isWorking() {
        return working;
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return getName() + " : " + (working ? "Working" : "Not Working");
    }

}
