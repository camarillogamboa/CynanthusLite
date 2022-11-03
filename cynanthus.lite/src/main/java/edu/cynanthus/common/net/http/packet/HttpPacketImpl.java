package edu.cynanthus.common.net.http.packet;

import java.util.Objects;

/**
 * El tipo Http packet.
 *
 * @param <T> el parámetro de tipo
 */
class HttpPacketImpl<T> implements HttpPacket<T> {

    /**
     * El Data.
     */
    private final T data;

    /**
     * Instancia un nuevo Http packet.
     *
     * @param data el data
     */
    HttpPacketImpl(T data) {
        this.data = Objects.requireNonNull(data);
    }

    /**
     * Permite obtener data.
     *
     * @return el data
     */
    @Override
    public final T getData() {
        return data;
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
        HttpPacketImpl<?> that = (HttpPacketImpl<?>) o;
        return Objects.equals(data, that.data);
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return "{" +
            "data:" + data +
            '}';
    }

}
