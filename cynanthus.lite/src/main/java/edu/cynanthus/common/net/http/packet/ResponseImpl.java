package edu.cynanthus.common.net.http.packet;

import java.util.Objects;

/**
 * El tipo Response.
 *
 * @param <T> el parámetro de tipo
 */
class ResponseImpl<T> extends HttpPacketImpl<T> implements Response<T> {

    /**
     * El Response code.
     */
    private final int responseCode;

    /**
     * Instancia un nuevo Response.
     *
     * @param responseCode el response code
     * @param data         el data
     */
    ResponseImpl(int responseCode, T data) {
        super(data);
        this.responseCode = responseCode;
    }

    /**
     * Permite obtener response code.
     *
     * @return el response code
     */
    @Override
    public int getResponseCode() {
        return responseCode;
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
        ResponseImpl<?> response = (ResponseImpl<?>) o;
        return responseCode == response.responseCode;
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), responseCode);
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return "{" +
            super.toString() + "," +
            "responseCode:" + responseCode +
            '}';
    }

}
