package edu.cynanthus.microservice.nanoservice;

import com.sun.net.httpserver.HttpServer;
import edu.cynanthus.microservice.Context;
import edu.cynanthus.microservice.net.http.server.HttpErrorManager;
import edu.cynanthus.microservice.net.http.server.HttpSecurityManager;
import edu.cynanthus.microservice.net.http.server.engine.HttpContextController;
import edu.cynanthus.microservice.property.ReadOnlyProperty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

/**
 * El tipo Web server.
 */
public class WebServer extends ContextNanoService implements HttpContextController {

    /**
     * El Port.
     */
    private final ReadOnlyProperty<Integer> port;

    /**
     * El Http error manager.
     */
    protected final HttpErrorManager httpErrorManager;
    /**
     * El Http security manager.
     */
    protected final HttpSecurityManager httpSecurityManager;

    /**
     * El Http server.
     */
    private HttpServer httpServer;

    /**
     * Instancia un nuevo Web server.
     *
     * @param id      el id
     * @param context el context
     */
    public WebServer(String id, Context context) {
        super(id, context);
        this.port = getProperty("port").asReadOnlyIntegerProperty();

        this.httpErrorManager = new HttpErrorManager(logger, context.getMessages());
        this.httpSecurityManager = new HttpSecurityManager(context.getUserManagement(), logger);
    }

    /**
     * Permite obtener http error manager.
     *
     * @return el http error manager
     */
    @Override
    public final HttpErrorManager getHttpErrorManager() {
        return httpErrorManager;
    }

    /**
     * Permite obtener http security manager.
     *
     * @return el http security manager
     */
    public final HttpSecurityManager getHttpSecurityManager() {
        return httpSecurityManager;
    }

    /**
     * Start.
     */
    @Override
    public final void start() {
        if (!working) {
            logger.info("Iniciando " + getName() + "...");
            logger.info("Iniciando servidor web http...");

            try {
                httpServer = HttpServer.create(new InetSocketAddress(port.getValue()), 0);

                HttpContextController.bindContextHandlers(httpServer, this);

                httpServer.start();
                logger.info("Servidor http iniciado en el puerto " + port);

                working = true;
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Falló la inicialización del servidor web http.", ex);
            }
        }
    }

    /**
     * Stop.
     */
    @Override
    public final void stop() {
        if (working) {
            logger.info("Deteniendo servidor web http...");
            httpServer.stop(0);
            httpServer = null;
            logger.info("Deteniendo " + getName() + "...");
            working = false;
        }
    }

}
