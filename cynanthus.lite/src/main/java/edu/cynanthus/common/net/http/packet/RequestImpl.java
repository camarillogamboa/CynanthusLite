package edu.cynanthus.common.net.http.packet;

import edu.cynanthus.common.net.http.RequestMethod;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Objects;

/**
 * El tipo Request.
 *
 * @param <T> el parámetro de tipo
 */
class RequestImpl<T> extends HttpPacketImpl<T> implements Request<T> {

    /**
     * El Request method.
     */
    private final RequestMethod requestMethod;
    /**
     * El Headers.
     */
    private final Map<String, String[]> headers;
    /**
     * El Remote address.
     */
    private final InetSocketAddress remoteAddress;

    /**
     * Instancia un nuevo Request.
     *
     * @param requestMethod el request method
     * @param headers       el headers
     * @param remoteAddress el remote address
     * @param data          el data
     */
    public RequestImpl(
        RequestMethod requestMethod,
        Map<String, String[]> headers,
        InetSocketAddress remoteAddress,
        T data
    ) {
        super(data);
        this.requestMethod = Objects.requireNonNull(requestMethod);
        this.headers = headers;
        this.remoteAddress = remoteAddress;
    }

    /**
     * Permite obtener request method.
     *
     * @return el request method
     */
    @Override
    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    /**
     * Permite obtener headers.
     *
     * @return el headers
     */
    @Override
    public Map<String, String[]> getHeaders() {
        return headers;
    }

    /**
     * Permite obtener remote address.
     *
     * @return el remote address
     */
    @Override
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * Equals boolean.
     *
     * @param o el o
     * @return el boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RequestImpl<?> request = (RequestImpl<?>) o;
        return Objects.equals(headers, request.headers) &&
            Objects.equals(remoteAddress, request.remoteAddress) &&
            requestMethod == request.requestMethod;
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), headers, remoteAddress, requestMethod);
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return "RequestImpl{" +
            "headers=" + headers +
            ", remoteAddress=" + remoteAddress +
            ", requestMethod=" + requestMethod +
            '}';
    }

}
