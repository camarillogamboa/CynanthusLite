package edu.cynanthus.common.net.http.packet;

import edu.cynanthus.common.net.http.RequestMethod;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.TreeMap;

/**
 * La interface Request.
 *
 * @param <T> el parámetro de tipo
 */
public interface Request<T> extends HttpPacket<T> {

    /**
     * Permite obtener request method.
     *
     * @return el request method
     */
    RequestMethod getRequestMethod();

    /**
     * Permite obtener headers.
     *
     * @return el headers
     */
    Map<String, String[]> getHeaders();

    /**
     * Permite obtener remote address.
     *
     * @return el remote address
     */
    InetSocketAddress getRemoteAddress();

    /**
     * Create request.
     *
     * @param <T>           el parámetro de tipo
     * @param requestMethod el request method
     * @param headers       el headers
     * @param remoteAddress el remote address
     * @param data          el data
     * @return el request
     */
    static <T> Request<T> create(
        RequestMethod requestMethod,
        Map<String, String[]> headers,
        InetSocketAddress remoteAddress,
        T data
    ) {
        return new RequestImpl<>(
            requestMethod,
            headers,
            remoteAddress,
            data
        );
    }

    /**
     * Create request.
     *
     * @param <T>           el parámetro de tipo
     * @param requestMethod el request method
     * @param headers       el headers
     * @param data          el data
     * @return el request
     */
    static <T> Request<T> create(RequestMethod requestMethod, Map<String, String[]> headers, T data) {
        return create(requestMethod, headers, null, data);
    }

    /**
     * Create request.
     *
     * @param <T>           el parámetro de tipo
     * @param requestMethod el request method
     * @param data          el data
     * @return el request
     */
    static <T> Request<T> create(RequestMethod requestMethod, T data) {
        return create(requestMethod, new TreeMap<>(String::compareTo), data);
    }

}
