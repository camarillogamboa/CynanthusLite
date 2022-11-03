package edu.cynanthus.microservice.nanoservice;

/**
 * El tipo Single nano service.
 */
public abstract class SingleNanoService extends BasicNanoService {

    /**
     * La constante singleNanoService.
     */
    private static SingleNanoService singleNanoService;

    /**
     * Instancia un nuevo Single nano service.
     *
     * @param id el id
     */
    public SingleNanoService(String id) {
        super(id);
        if (singleNanoService != null)
            throw new UnsupportedOperationException("Solo puede haber una instancia de esta clase");

        singleNanoService = this;
    }

}
