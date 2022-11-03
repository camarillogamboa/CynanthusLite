package edu.cynanthus.common.net.http.packet;

/**
 * La interface Http packet.
 *
 * @param <T> el parámetro de tipo
 */
@FunctionalInterface
public interface HttpPacket<T> {

    /**
     * Permite obtener data.
     *
     * @return el data
     */
    T getData();

}
