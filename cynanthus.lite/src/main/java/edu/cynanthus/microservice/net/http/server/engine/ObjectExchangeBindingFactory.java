package edu.cynanthus.microservice.net.http.server.engine;

import com.google.gson.JsonSyntaxException;
import edu.cynanthus.common.net.http.HttpException;
import edu.cynanthus.common.net.http.HttpStatusCode;
import edu.cynanthus.common.net.http.packet.Request;
import edu.cynanthus.common.net.http.packet.Response;
import edu.cynanthus.common.net.http.packet.converter.Converter;
import edu.cynanthus.common.reflect.AnyType;
import edu.cynanthus.common.resource.Exchanger;
import edu.cynanthus.microservice.net.http.server.HttpPacketExchangeHandler;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * El tipo Object exchange binding factory.
 *
 * @param <T> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
public class ObjectExchangeBindingFactory<T, R> extends FunctionalBindingFactory<T, R> {

    /**
     * Instancia un nuevo Object exchange binding factory.
     *
     * @param parameterType el parameter type
     * @param returnType    el return type
     * @param factory       el factory
     */
    public ObjectExchangeBindingFactory(
        Type parameterType,
        Type returnType,
        Function<Exchanger<T, R>, Exchanger<Request<InputStream>, Response<InputStream>>> factory
    ) {
        super(
            parameterType,
            returnType,
            invoker -> new HttpPacketExchangeHandler(factory.apply(invoker))
        );
    }

    /**
     * Instancia un nuevo Object exchange binding factory.
     *
     * @param parameterType             el parameter type
     * @param returnType                el return type
     * @param requestToObjectConverter  el request to object converter
     * @param objectToResponseConverter el object to response converter
     */
    public ObjectExchangeBindingFactory(
        Type parameterType,
        Type returnType,
        Converter<Request<InputStream>, T> requestToObjectConverter,
        Converter<R, Response<InputStream>> objectToResponseConverter
    ) {
        this(
            parameterType,
            returnType,
            invoker -> request -> objectToResponseConverter.exchange(
                invoker.exchange(requestToObjectConverter.exchange(request))
            )
        );
    }

    /**
     * Create object exchange binding factory.
     *
     * @param <T>                   el parámetro de tipo
     * @param <R>                   el parámetro de tipo
     * @param parameterType         el parameter type
     * @param returnType            el return type
     * @param requestDataConverter  el request data converter
     * @param responseDataConverter el response data converter
     * @return el object exchange binding factory
     */
    public static <T, R> ObjectExchangeBindingFactory<T, R> create(
        Type parameterType,
        Type returnType,
        Converter<InputStream, T> requestDataConverter,
        Converter<R, InputStream> responseDataConverter
    ) {
        return new ObjectExchangeBindingFactory<>(
            parameterType,
            returnType,
            request -> requestDataConverter.exchange(request.getData()),
            data -> Response.createOkResponse(responseDataConverter.exchange(data))
        );
    }

    /**
     * Of input stream object exchange binding factory.
     *
     * @param <R>                   el parámetro de tipo
     * @param returnType            el return type
     * @param responseDataConverter el response data converter
     * @return el object exchange binding factory
     */
    public static <R> ObjectExchangeBindingFactory<InputStream, R> ofInputStream(
        Type returnType,
        Converter<R, InputStream> responseDataConverter
    ) {
        return create(
            InputStream.class,
            returnType,
            Converter.identity(),
            responseDataConverter
        );
    }

    /**
     * Of reader object exchange binding factory.
     *
     * @param <R>                   el parámetro de tipo
     * @param returnType            el return type
     * @param responseDataConverter el response data converter
     * @return el object exchange binding factory
     */
    public static <R> ObjectExchangeBindingFactory<Reader, R> ofReader(
        Type returnType,
        Converter<R, InputStream> responseDataConverter
    ) {
        return create(
            Reader.class,
            returnType,
            Converter.INPUT_STREAM_TO_READER,
            responseDataConverter
        );
    }

    /**
     * Of string object exchange binding factory.
     *
     * @param <R>           el parámetro de tipo
     * @param returnType    el return type
     * @param dataConverter el data converter
     * @return el object exchange binding factory
     */
    public static <R> ObjectExchangeBindingFactory<String, R> ofString(
        Type returnType,
        Converter<R, InputStream> dataConverter
    ) {
        return create(
            String.class,
            returnType,
            Converter.INPUT_STREAM_TO_STRING,
            dataConverter
        );
    }

    /**
     * Of object object exchange binding factory.
     *
     * @param <R>           el parámetro de tipo
     * @param returnType    el return type
     * @param dataConverter el data converter
     * @return el object exchange binding factory
     */
    public static <R> ObjectExchangeBindingFactory<Object, R> ofObject(
        Type returnType,
        Converter<R, InputStream> dataConverter
    ) {
        AnyType anyType = new AnyType();
        return new ObjectExchangeBindingFactory<>(
            anyType,
            returnType,
            invoker -> {
                Converter<InputStream, Object> converter =
                    Converter.jsonInputStreamToObject(anyType.getLastTypeCompared());

                Converter<InputStream, Object> safeConverter = inputStream -> {
                    try {
                        return converter.exchange(inputStream);
                    } catch (JsonSyntaxException ex) {
                        throw new HttpException(HttpStatusCode.BAD_REQUEST, ex);
                    }
                };

                return request -> Response.createOkResponse(
                    dataConverter.exchange(invoker.exchange(safeConverter.exchange(request.getData())))
                );
            }
        );
    }

}
