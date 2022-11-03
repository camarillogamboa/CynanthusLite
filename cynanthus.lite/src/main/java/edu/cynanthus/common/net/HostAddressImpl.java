package edu.cynanthus.common.net;

import java.util.Objects;

class HostAddressImpl implements HostAddress {

    private String hostName;
    private int hostPort;

    HostAddressImpl(String hostName, int hostPort) {
        this.hostName = hostName;
        this.hostPort = hostPort;
    }

    HostAddressImpl() {
    }

    @Override
    public String getHostName() {
        return hostName;
    }

    @Override
    public int getHostPort() {
        return hostPort;
    }

    @Override
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostAddressImpl that = (HostAddressImpl) o;
        return hostPort == that.hostPort && Objects.equals(hostName, that.hostName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostName, hostPort);
    }

    @Override
    public String toString() {
        return "{" +
            "hostName:'" + hostName + '\'' +
            ",hostPort:" + hostPort +
            '}';
    }

}
