package edu.cynanthus.common.net.http.packet.converter;

import edu.cynanthus.common.net.http.packet.Response;

/**
 * El tipo Self contained response.
 *
 * @param <T> el parámetro de tipo
 * @param <O> el parámetro de tipo
 */
abstract class SelfContainedResponse<T, O> implements Response<T> {

    /**
     * El Response.
     */
    private final Response<O> response;

    /**
     * Instancia un nuevo Self contained response.
     *
     * @param response el response
     */
    SelfContainedResponse(Response<O> response) {
        this.response = response;
    }

    /**
     * Permite obtener response code.
     *
     * @return el response code
     */
    @Override
    public int getResponseCode() {
        return response.getResponseCode();
    }

}
