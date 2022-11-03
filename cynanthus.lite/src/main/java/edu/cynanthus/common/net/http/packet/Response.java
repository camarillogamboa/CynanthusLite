package edu.cynanthus.common.net.http.packet;

import edu.cynanthus.common.net.http.HttpStatusCode;

/**
 * La interface Response.
 *
 * @param <T> el parámetro de tipo
 */
public interface Response<T> extends HttpPacket<T> {

    /**
     * Permite obtener response code.
     *
     * @return el response code
     */
    int getResponseCode();

    /**
     * Create response.
     *
     * @param <T>          el parámetro de tipo
     * @param responseCode el response code
     * @param data         el data
     * @return el response
     */
    static <T> Response<T> create(int responseCode, T data) {
        return new ResponseImpl<>(responseCode, data);
    }

    /**
     * Create ok response response.
     *
     * @param <T>  el parámetro de tipo
     * @param data el data
     * @return el response
     */
    static <T> Response<T> createOkResponse(T data) {
        return new ResponseImpl<>(HttpStatusCode.OK, data);
    }

}
