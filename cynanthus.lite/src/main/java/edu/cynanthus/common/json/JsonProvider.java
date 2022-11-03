package edu.cynanthus.common.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cynanthus.common.resource.StreamUtil;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;

/**
 * El tipo Json provider.
 */
public final class JsonProvider {

    /**
     * La constante gson.
     */
    private static Gson gson;

    /**
     * Instancia un nuevo Json provider.
     */
    private JsonProvider() {}

    /**
     * Permite obtener instance.
     *
     * @return el instance
     */
    public static Gson getInstance() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            //builder.registerTypeAdapter(SystemUser.class, SystemUser.createJsonDeserializer());
            gson = builder.create();
        }
        return gson;
    }

    /**
     * To json string.
     *
     * @param object el object
     * @return el string
     */
    public static String toJson(Object object) {
        return getInstance().toJson(object);
    }

    /**
     * To json imput stream input stream.
     *
     * @param object el object
     * @return el input stream
     */
    public static InputStream toJsonInputStream(Object object) {
        return StreamUtil.asInputStream(toJson(object));
    }

    /**
     * From json t.
     *
     * @param <T>        el parámetro de tipo
     * @param jsonString el json string
     * @param type       el type
     * @return el t
     */
    public static <T> T fromJson(String jsonString, Type type) {
        return getInstance().fromJson(jsonString, type);
    }

    /**
     * From json t.
     *
     * @param <T>    el parámetro de tipo
     * @param reader el reader
     * @param type   el type
     * @return el t
     */
    public static <T> T fromJson(Reader reader, Type type) {
        return getInstance().fromJson(reader, type);
    }

}
