package edu.cynanthus.common.net.http.client;

import edu.cynanthus.common.net.HostAddress;

/**
 * La interface Http client info.
 */
public interface HttpHostAddress extends HostAddress {

    /**
     * Permite obtener server context.
     *
     * @return el server context
     */
    String getServerContext();

    /**
     * Permite establecer server context.
     *
     * @param context el context
     */
    void setServerContext(String context);

    /**
     * Build path string.
     *
     * @return el string
     */
    default String buildPath() {
        return "http://" + getHostName() + ":" + getHostPort() + getServerContext();
    }

    /**
     * Create http client info.
     *
     * @param serverName    el server name
     * @param serverPort    el server port
     * @param serverContext el server context
     * @return el http client info
     */
    static HttpHostAddress create(String serverName, int serverPort, String serverContext) {
        return new HttpHostAddressImpl(serverName, serverPort, serverContext);
    }

}
