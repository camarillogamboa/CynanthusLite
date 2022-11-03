package edu.cynanthus.common.net.http.packet.converter;

import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.common.net.http.packet.Request;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * El tipo Self contained request.
 *
 * @param <T> el parámetro de tipo
 * @param <O> el parámetro de tipo
 */
abstract class SelfContainedRequest<T, O> implements Request<T> {

    /**
     * El Request.
     */
    private final Request<O> request;

    /**
     * Instancia un nuevo Self contained request.
     *
     * @param request el request
     */
    SelfContainedRequest(Request<O> request) {
        this.request = request;
    }

    /**
     * Permite obtener headers.
     *
     * @return el headers
     */
    @Override
    public Map<String, String[]> getHeaders() {
        return request.getHeaders();
    }

    /**
     * Permite obtener request method.
     *
     * @return el request method
     */
    @Override
    public RequestMethod getRequestMethod() {
        return request.getRequestMethod();
    }

    /**
     * Permite obtener remote address.
     *
     * @return el remote address
     */
    @Override
    public InetSocketAddress getRemoteAddress() {
        return request.getRemoteAddress();
    }

}
