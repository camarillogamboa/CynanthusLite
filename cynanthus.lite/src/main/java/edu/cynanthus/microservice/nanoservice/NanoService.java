package edu.cynanthus.microservice.nanoservice;

/**
 * La interface Nano service.
 */
public interface NanoService {

    /**
     * Permite obtener id.
     *
     * @return el id
     */
    String getId();

    /**
     * Permite obtener name.
     *
     * @return el name
     */
    default String getName() {
        return getClass().getSimpleName() + "(" + getId() + ")";
    }

    /**
     * Is working boolean.
     *
     * @return el boolean
     */
    boolean isWorking();

    /**
     * Start.
     */
    void start();

    /**
     * Stop.
     */
    void stop();

}
