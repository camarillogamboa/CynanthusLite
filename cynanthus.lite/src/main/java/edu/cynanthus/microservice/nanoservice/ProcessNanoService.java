package edu.cynanthus.microservice.nanoservice;

import edu.cynanthus.microservice.Context;

import java.util.logging.Level;

/**
 * El tipo Process nano service.
 */
public abstract class ProcessNanoService extends ContextNanoService {

    /**
     * El Task.
     */
    private final Runnable task;

    /**
     * Instancia un nuevo Process nano service.
     *
     * @param id      el id
     * @param context el context
     */
    public ProcessNanoService(String id, Context context) {
        super(id, context);
        this.task = () -> {
            while (isWorking()) {
                try {
                    processLoop();
                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        };
    }

    /**
     * Start.
     */
    @Override
    public final void start() {
        if (!working) {
            logger.info("Starting" + getName() + "...");
            working = true;
            new Thread(task).start();
        }
    }

    /**
     * Stop.
     */
    @Override
    public final void stop() {
        if (working) {
            working = false;
            logger.info("Stoping " + getName() + "...");
        }
    }

    /**
     * Process loop.
     *
     * @throws InterruptedException el interrupted exception
     */
    public abstract void processLoop() throws InterruptedException;

}
