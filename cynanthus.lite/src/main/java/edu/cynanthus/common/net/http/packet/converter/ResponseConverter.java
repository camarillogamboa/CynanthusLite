package edu.cynanthus.common.net.http.packet.converter;

import edu.cynanthus.common.net.http.packet.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * El tipo Response converter.
 *
 * @param <A> el parámetro de tipo
 * @param <B> el parámetro de tipo
 */
public class ResponseConverter<A, B> implements Converter<Response<A>, Response<B>> {

    /**
     * El Data converter.
     */
    private final Converter<A, B> dataConverter;

    /**
     * Instancia un nuevo Response converter.
     *
     * @param dataConverter el data converter
     */
    public ResponseConverter(Converter<A, B> dataConverter) {
        this.dataConverter = Objects.requireNonNull(dataConverter);
    }

    /**
     * Exchange response.
     *
     * @param response el response
     * @return el response
     * @throws IOException el io exception
     */
    @Override
    public Response<B> exchange(Response<A> response) throws IOException {
        B newData = dataConverter.exchange(response.getData());
        return new SelfContainedResponse<>(response) {
            @Override
            public B getData() {
                return newData;
            }
        };
    }

}
