package edu.cynanthus.microservice.net.http.server.engine;

import com.sun.net.httpserver.HttpExchange;
import edu.cynanthus.common.net.http.packet.converter.Converter;
import edu.cynanthus.common.reflect.AnyType;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * El tipo Reflexive link utility.
 */
final class ReflexiveLinkUtilityImpl extends ReflexiveLinkUtility {

    /**
     * El Factories.
     */
    private EvaluableBindingFactory[] factories;

    /**
     * Test boolean.
     *
     * @param method el method
     * @return el boolean
     */
    @Override
    public boolean test(Method method) {
        return Modifier.isPublic(method.getModifiers()) &&
            !Modifier.isStatic(method.getModifiers()) &&
            !method.isBridge() &&
            !method.isSynthetic();
    }

    /**
     * Get factories evaluable binding factory [ ].
     *
     * @return el evaluable binding factory [ ]
     */
    @Override
    public EvaluableBindingFactory[] getFactories() {
        return (factories != null ? factories : (factories = loadFactories()));
    }

    /**
     * Close.
     */
    @Override
    public void close() {
        if (factories != null) {
            Arrays.fill(factories, null);
            factories = null;
        }
    }

    /**
     * Load factories evaluable binding factory [ ].
     *
     * @return el evaluable binding factory [ ]
     */
    private EvaluableBindingFactory[] loadFactories() {
        LinkedList<EvaluableBindingFactory> list = new LinkedList<>();

        //HttpExchange->void
        list.add(new FunctionalBindingFactory<>(
            HttpExchange.class,
            void.class,
            exchanger -> exchanger::exchange
        ));

        loadRequestToResponse(list);
        loadObjectToObject(list);

        return list.toArray(EvaluableBindingFactory[]::new);
    }

    /**
     * Load request to response.
     *
     * @param list el list
     */
    //Request<InputStream|Reader|String|?>->Response<InputStream|Reader|String|?>
    private void loadRequestToResponse(List<EvaluableBindingFactory> list) {
        //Request<InputStream>->Response<InputStream>
        list.add(new PacketExchangeBindingFactory<>(InputStream.class, InputStream.class, Function.identity()));

        //Request<InputStream>->Response<Reader>
        list.add(PacketExchangeBindingFactory.ofInputStreamRequest(
            Reader.class, Converter.READER_TO_INPUT_STREAM
        ));

        //Request<InputStream>->Response<String>
        list.add(PacketExchangeBindingFactory.ofInputStreamRequest(
            String.class, Converter.STRING_TO_INPUT_STREAM
        ));

        //Request<InputStream>->Response<?>
        list.add(PacketExchangeBindingFactory.ofInputStreamRequest(
            new AnyType(), Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));

        //Request<Reader>->Response<InputStream>
        list.add(PacketExchangeBindingFactory.ofReaderRequest(
            InputStream.class, Converter.identity()
        ));

        //Request<Reader>->Response<Reader>
        list.add(PacketExchangeBindingFactory.ofReaderRequest(
            Reader.class, Converter.READER_TO_INPUT_STREAM
        ));

        //Request<Reader>->Response<String>
        list.add(PacketExchangeBindingFactory.ofReaderRequest(
            String.class, Converter.STRING_TO_INPUT_STREAM
        ));

        //Request<Reader>->Response<?>
        list.add(PacketExchangeBindingFactory.ofReaderRequest(
            new AnyType(), Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));

        //Request<String>->Response<InputStream>
        list.add(PacketExchangeBindingFactory.ofStringRequest(
            InputStream.class, Converter.identity()
        ));

        //Request<String>->Response<Reader>
        list.add(PacketExchangeBindingFactory.ofStringRequest(
            Reader.class, Converter.READER_TO_INPUT_STREAM
        ));

        //Request<String>->Response<String>
        list.add(PacketExchangeBindingFactory.ofStringRequest(
            String.class, Converter.STRING_TO_INPUT_STREAM
        ));

        //Request<String>->Response<?>
        list.add(PacketExchangeBindingFactory.ofStringRequest(
            new AnyType(), Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));

        //Request<?>->Response<InputStream>
        list.add(PacketExchangeBindingFactory.ofObjectRequest(
            InputStream.class, Converter.identity()
        ));

        //Request<?>->Response<Reader>
        list.add(PacketExchangeBindingFactory.ofObjectRequest(
            Reader.class, Converter.READER_TO_INPUT_STREAM
        ));

        //Request<?>->Response<String>
        list.add(PacketExchangeBindingFactory.ofObjectRequest(
            Reader.class, Converter.STRING_TO_INPUT_STREAM
        ));

        //Request<?>->Response<?>
        list.add(PacketExchangeBindingFactory.ofObjectRequest(
            new AnyType(), Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));
    }

    /**
     * Load object to object.
     *
     * @param list el list
     */
    //InputStream|Reader|String|?->InputStream|Reader|String|?
    private void loadObjectToObject(List<EvaluableBindingFactory> list) {
        //InputStream->InputStream
        list.add(ObjectExchangeBindingFactory.ofInputStream(
            InputStream.class,
            Converter.identity()
        ));

        //InputStream->Reader
        list.add(ObjectExchangeBindingFactory.ofInputStream(
            Reader.class,
            Converter.READER_TO_INPUT_STREAM
        ));

        //InputStream->String
        list.add(ObjectExchangeBindingFactory.ofInputStream(
            String.class,
            Converter.STRING_TO_INPUT_STREAM
        ));

        //InputStream->?
        list.add(ObjectExchangeBindingFactory.ofInputStream(
            new AnyType(),
            Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));

        //Reader->InputStream
        list.add(ObjectExchangeBindingFactory.ofReader(
            InputStream.class,
            Converter.identity()
        ));

        //Reader->Reader
        list.add(ObjectExchangeBindingFactory.ofReader(
            Reader.class,
            Converter.READER_TO_INPUT_STREAM
        ));

        //Reader->String
        list.add(ObjectExchangeBindingFactory.ofReader(
            String.class,
            Converter.STRING_TO_INPUT_STREAM
        ));

        //Reader->?
        list.add(ObjectExchangeBindingFactory.ofReader(
            new AnyType(),
            Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));

        //String->InputStream
        list.add(ObjectExchangeBindingFactory.ofString(
            InputStream.class,
            Converter.identity()
        ));

        //String->Reader
        list.add(ObjectExchangeBindingFactory.ofString(
            Reader.class,
            Converter.READER_TO_INPUT_STREAM
        ));

        //String->String
        list.add(ObjectExchangeBindingFactory.ofString(
            String.class,
            Converter.STRING_TO_INPUT_STREAM
        ));

        //String->?
        list.add(ObjectExchangeBindingFactory.ofString(
            new AnyType(),
            Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));

        //?->InputStream
        list.add(ObjectExchangeBindingFactory.ofObject(
            InputStream.class,
            Converter.identity()
        ));

        //?->Reader
        list.add(ObjectExchangeBindingFactory.ofObject(
            Reader.class,
            Converter.READER_TO_INPUT_STREAM
        ));

        //?->String
        list.add(ObjectExchangeBindingFactory.ofObject(
            String.class,
            Converter.STRING_TO_INPUT_STREAM
        ));

        //?->?
        list.add(ObjectExchangeBindingFactory.ofObject(
            new AnyType(),
            Converter.OBJECT_TO_JSON_INPUT_STREAM
        ));

    }

}
