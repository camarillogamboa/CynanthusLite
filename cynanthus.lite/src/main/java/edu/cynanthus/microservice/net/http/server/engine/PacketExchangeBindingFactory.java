package edu.cynanthus.microservice.net.http.server.engine;

import com.google.gson.JsonSyntaxException;
import edu.cynanthus.common.net.http.HttpException;
import edu.cynanthus.common.net.http.HttpStatusCode;
import edu.cynanthus.common.net.http.packet.Request;
import edu.cynanthus.common.net.http.packet.Response;
import edu.cynanthus.common.net.http.packet.converter.Converter;
import edu.cynanthus.common.net.http.packet.converter.RequestConverter;
import edu.cynanthus.common.net.http.packet.converter.ResponseConverter;
import edu.cynanthus.common.reflect.AnyType;
import edu.cynanthus.common.reflect.BasicParameterizedType;
import edu.cynanthus.common.resource.Exchanger;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * El tipo Packet exchange binding factory.
 *
 * @param <T> el parámetro de tipo
 * @param <R> el parámetro de tipo
 */
public class PacketExchangeBindingFactory<T, R> extends ObjectExchangeBindingFactory<Request<T>, Response<R>> {

    /**
     * Instancia un nuevo Packet exchange binding factory.
     *
     * @param requestDataType  el request data type
     * @param responseDataType el response data type
     * @param factory          el factory
     */
    public PacketExchangeBindingFactory(
        Type requestDataType,
        Type responseDataType,
        Function<Exchanger<Request<T>, Response<R>>, Exchanger<Request<InputStream>, Response<InputStream>>> factory
    ) {
        super(
            new BasicParameterizedType(
                PacketExchangeBindingFactory.class,
                Request.class,
                requestDataType
            ),
            new BasicParameterizedType(
                PacketExchangeBindingFactory.class,
                Response.class,
                responseDataType
            ),
            factory
        );
    }

    /**
     * Instancia un nuevo Packet exchange binding factory.
     *
     * @param requestDataType   el request data type
     * @param responseDataType  el response data type
     * @param requestConverter  el request converter
     * @param responseConverter el response converter
     */
    public PacketExchangeBindingFactory(
        Type requestDataType,
        Type responseDataType,
        Converter<Request<InputStream>, Request<T>> requestConverter,
        Converter<Response<R>, Response<InputStream>> responseConverter
    ) {
        this(
            requestDataType,
            responseDataType,
            invoker -> request -> responseConverter.exchange(
                invoker.exchange(requestConverter.exchange(request))
            )
        );
    }

    /**
     * Build packet exchange binding factory.
     *
     * @param <T>                   el parámetro de tipo
     * @param <R>                   el parámetro de tipo
     * @param requestDataType       el request data type
     * @param responseDataType      el response data type
     * @param requestDataConverter  el request data converter
     * @param responseDataConverter el response data converter
     * @return el packet exchange binding factory
     */
    public static <T, R> PacketExchangeBindingFactory<T, R> build(
        Type requestDataType,
        Type responseDataType,
        Converter<InputStream, T> requestDataConverter,
        Converter<R, InputStream> responseDataConverter
    ) {
        return new PacketExchangeBindingFactory<>(
            requestDataType,
            responseDataType,
            new RequestConverter<>(requestDataConverter),
            new ResponseConverter<>(responseDataConverter)
        );
    }

    /**
     * Of input stream request packet exchange binding factory.
     *
     * @param <R>                   el parámetro de tipo
     * @param returnType            el return type
     * @param responseDataConverter el response data converter
     * @return el packet exchange binding factory
     */
    public static <R> PacketExchangeBindingFactory<InputStream, R> ofInputStreamRequest(
        Type returnType,
        Converter<R, InputStream> responseDataConverter
    ) {
        return build(
            InputStream.class,
            returnType,
            Converter.identity(),
            responseDataConverter
        );
    }

    /**
     * Of reader request packet exchange binding factory.
     *
     * @param <R>                   el parámetro de tipo
     * @param returnType            el return type
     * @param responseDataConverter el response data converter
     * @return el packet exchange binding factory
     */
    public static <R> PacketExchangeBindingFactory<Reader, R> ofReaderRequest(
        Type returnType,
        Converter<R, InputStream> responseDataConverter
    ) {
        return build(
            Reader.class,
            returnType,
            Converter.INPUT_STREAM_TO_READER,
            responseDataConverter
        );
    }

    /**
     * Of string request packet exchange binding factory.
     *
     * @param <R>                   el parámetro de tipo
     * @param returnType            el return type
     * @param responseDataConverter el response data converter
     * @return el packet exchange binding factory
     */
    public static <R> PacketExchangeBindingFactory<String, R> ofStringRequest(
        Type returnType,
        Converter<R, InputStream> responseDataConverter
    ) {
        return build(
            String.class,
            returnType,
            Converter.INPUT_STREAM_TO_STRING,
            responseDataConverter
        );
    }

    /**
     * Of object request packet exchange binding factory.
     *
     * @param <R>                   el parámetro de tipo
     * @param returnType            el return type
     * @param responseDataConverter el response data converter
     * @return el packet exchange binding factory
     */
    public static <R> PacketExchangeBindingFactory<Object, R> ofObjectRequest(
        Type returnType,
        Converter<R, InputStream> responseDataConverter
    ) {
        AnyType anyType = new AnyType();
        return new PacketExchangeBindingFactory<>(
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

                RequestConverter<InputStream, Object> requestConverter = new RequestConverter<>(safeConverter);
                ResponseConverter<R, InputStream> responseConverter = new ResponseConverter<>(
                    responseDataConverter
                );
                return request -> responseConverter.exchange(invoker.exchange(requestConverter.exchange(request)));
            }
        );
    }

}
