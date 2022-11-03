package edu.cynanthus.microservice.nanoservice;

import edu.cynanthus.common.CollectionUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * El tipo Nano services.
 */
public final class NanoServices extends BasicNanoService {

    /**
     * El Nano services.
     */
    private final List<NanoService> nanoServices;

    /**
     * Instancia un nuevo Nano services.
     *
     * @param id           el id
     * @param nanoServices el nano services
     */
    public NanoServices(String id, List<NanoService> nanoServices) {
        super(id);
        this.nanoServices = Objects.requireNonNull(nanoServices);
    }

    /**
     * Instancia un nuevo Nano services.
     *
     * @param name el name
     */
    public NanoServices(String name) {
        this(name, new LinkedList<>());
    }

    /**
     * Instancia un nuevo Nano services.
     *
     * @param name         el name
     * @param nanoServices el nano services
     */
    public NanoServices(String name, NanoService... nanoServices) {
        this(name, new LinkedList<>(Arrays.asList(nanoServices)));
    }

    /**
     * Add nano service.
     *
     * @param nanoService el nano service
     */
    public void addNanoService(NanoService nanoService) {
        Objects.requireNonNull(nanoService);
        nanoServices.add(nanoService);
        if (working) if (!nanoService.isWorking()) nanoService.start();
        else if (nanoService.isWorking()) nanoService.stop();
    }

    /**
     * Remove nano service.
     *
     * @param nanoService el nano service
     */
    public void removeNanoService(NanoService nanoService) {
        nanoServices.remove(nanoService);
    }

    /**
     * Start.
     */
    @Override
    public void start() {
        if (!working) {
            nanoServices.forEach(NanoService::start);
            working = true;
        }
    }

    /**
     * Stop.
     */
    @Override
    public void stop() {
        if (working) {
            nanoServices.forEach(NanoService::stop);
            working = false;
        }
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return "{" +
            super.toString() + "," +
            "nanoServices:" + CollectionUtil.toString(nanoServices) +
            '}';
    }

}
