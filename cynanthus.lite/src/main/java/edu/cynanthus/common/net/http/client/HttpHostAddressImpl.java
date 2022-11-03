package edu.cynanthus.common.net.http.client;

import java.util.Objects;

/**
 * El tipo Http client info.
 */
class HttpHostAddressImpl implements HttpHostAddress {

    /**
     * El Server name.
     */
    private String serverName;
    /**
     * El Server port.
     */
    private int serverPort;
    /**
     * El Server context.
     */
    private String serverContext;

    /**
     * Instancia un nuevo Http client info.
     *
     * @param serverName    el server name
     * @param serverPort    el server port
     * @param serverContext el server context
     */
    HttpHostAddressImpl(String serverName, int serverPort, String serverContext) {
        setHostName(serverName);
        setHostPort(serverPort);
        setServerContext(serverContext);
    }

    /**
     * Permite obtener server name.
     *
     * @return el server name
     */
    @Override
    public final String getHostName() {
        return serverName;
    }

    /**
     * Permite obtener server port.
     *
     * @return el server port
     */
    @Override
    public final int getHostPort() {
        return serverPort;
    }

    /**
     * Permite obtener server context.
     *
     * @return el server context
     */
    @Override
    public final String getServerContext() {
        return serverContext;
    }

    /**
     * Permite establecer server name.
     *
     * @param hostName el servername
     */
    @Override
    public final void setHostName(String hostName) {
        this.serverName = Objects.requireNonNull(hostName);
    }

    /**
     * Permite establecer server port.
     *
     * @param hostPort el server port
     */
    @Override
    public final void setHostPort(int hostPort) {
        if (hostPort >= 0) this.serverPort = hostPort;
        else throw new IllegalArgumentException("Negative port number");
    }

    /**
     * Permite establecer server context.
     *
     * @param context el context
     */
    @Override
    public final void setServerContext(String context) {
        this.serverContext = Objects.requireNonNull(context);
    }

    /**
     * Equals boolean.
     *
     * @param o el o
     * @return el boolean
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HttpHostAddressImpl that = (HttpHostAddressImpl) o;
        return serverPort == that.serverPort &&
            Objects.equals(serverName, that.serverName) &&
            Objects.equals(serverContext, that.serverContext);
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public final int hashCode() {
        return Objects.hash(serverName, serverPort, serverContext);
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public final String toString() {
        return "AbstractHttpClientContext{"
            + "serverName='" + serverName + '\''
            + ", serverPort=" + serverPort
            + ", serverContext='" + serverContext +
            '\'' + '}';
    }

}
