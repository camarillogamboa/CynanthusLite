package edu.cynanthus.common.net.http.packet.converter;

import edu.cynanthus.common.json.JsonProvider;
import edu.cynanthus.common.resource.Exchanger;
import edu.cynanthus.common.resource.ReaderInputStream;
import edu.cynanthus.common.resource.StreamUtil;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * La interface Converter.
 *
 * @param <A> el parámetro de tipo
 * @param <B> el parámetro de tipo
 */
public interface Converter<A, B> extends Exchanger<A, B> {

    /**
     * El Input stream to reader.
     */
    Converter<InputStream, Reader> INPUT_STREAM_TO_READER = InputStreamReader::new;
    /**
     * El Input stream to string.
     */
    Converter<InputStream, String> INPUT_STREAM_TO_STRING = StreamUtil::toString;
    /**
     * El Reader to input stream.
     */
    Converter<Reader, InputStream> READER_TO_INPUT_STREAM = ReaderInputStream::new;
    /**
     * El Reader to string.
     */
    Converter<Reader, String> READER_TO_STRING = StreamUtil::toString;
    /**
     * El String to input stream.
     */
    Converter<String, InputStream> STRING_TO_INPUT_STREAM = StreamUtil::asInputStream;
    /**
     * El String to reader.
     */
    Converter<String, Reader> STRING_TO_READER = StreamUtil::asReader;
    /**
     * El Object to json string.
     */
    Converter<Object, String> OBJECT_TO_JSON_STRING = JsonProvider.getInstance()::toJson;
    /**
     * El Object to json reader.
     */
    Converter<Object, Reader> OBJECT_TO_JSON_READER =
        object -> StreamUtil.asReader(JsonProvider.toJson(object));
    /**
     * El Object to json input stream.
     */
    Converter<Object, InputStream> OBJECT_TO_JSON_INPUT_STREAM =
        object -> StreamUtil.asInputStream(JsonProvider.toJson(object));

    /**
     * Json string to object converter.
     *
     * @param <T>  el parámetro de tipo
     * @param type el type
     * @return el converter
     */
    static <T> Converter<String, T> jsonStringToObject(Type type) {
        return jsonString -> JsonProvider.fromJson(jsonString, type);
    }

    /**
     * Json reader to object converter.
     *
     * @param <T>  el parámetro de tipo
     * @param type el type
     * @return el converter
     */
    static <T> Converter<Reader, T> jsonReaderToObject(Type type) {
        return jsonReader -> JsonProvider.getInstance().fromJson(jsonReader, type);
    }

    /**
     * Json input stream to object converter.
     *
     * @param <T>  el parámetro de tipo
     * @param type el type
     * @return el converter
     */
    static <T> Converter<InputStream, T> jsonInputStreamToObject(Type type) {
        return jsonInputStream -> JsonProvider.fromJson(new InputStreamReader(jsonInputStream), type);
    }

    /**
     * Identity converter.
     *
     * @param <A> el parámetro de tipo
     * @return el converter
     */
    static <A> Converter<A, A> identity() {
        return data -> data;
    }

}
