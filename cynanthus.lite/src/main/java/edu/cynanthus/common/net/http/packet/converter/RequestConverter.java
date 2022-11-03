package edu.cynanthus.common.net.http.packet.converter;

import edu.cynanthus.common.net.http.packet.Request;

import java.io.IOException;
import java.util.Objects;

/**
 * El tipo Request converter.
 *
 * @param <A> el parámetro de tipo
 * @param <B> el parámetro de tipo
 */
public class RequestConverter<A, B> implements Converter<Request<A>, Request<B>> {

    /**
     * El Data converter.
     */
    private final Converter<A, B> dataConverter;

    /**
     * Instancia un nuevo Request converter.
     *
     * @param dataConverter el data converter
     */
    public RequestConverter(Converter<A, B> dataConverter) {
        this.dataConverter = Objects.requireNonNull(dataConverter);
    }

    /**
     * Exchange request.
     *
     * @param request el request
     * @return el request
     * @throws IOException el io exception
     */
    @Override
    public Request<B> exchange(Request<A> request) throws IOException {
        B newData = dataConverter.exchange(request.getData());
        return new SelfContainedRequest<>(request) {
            @Override
            public B getData() {
                return newData;
            }
        };
    }

}
