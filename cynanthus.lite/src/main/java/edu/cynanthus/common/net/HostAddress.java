package edu.cynanthus.common.net;

/**
 * La interface Client info.
 */
public interface HostAddress {

    /**
     * Permite obtener server name.
     *
     * @return el server name
     */
    String getHostName();

    /**
     * Permite obtener server port.
     *
     * @return el server port
     */
    int getHostPort();

    /**
     * Permite establecer server name.
     *
     * @param hostName el server name
     */
    void setHostName(String hostName);

    /**
     * Permite establecer server port.
     *
     * @param hostPort el server port
     */
    void setHostPort(int hostPort);

    static HostAddress create(String hostName, int port) {
        return new HostAddressImpl(hostName, port);
    }

}
