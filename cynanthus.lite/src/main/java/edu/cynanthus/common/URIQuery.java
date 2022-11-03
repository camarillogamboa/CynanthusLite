package edu.cynanthus.common;

import edu.cynanthus.common.reflect.ConstantType;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.Function;

/**
 * La clase URIQuery provee de métodos estáticos que permitan la codificación
 * y decodificación de objetos en parametros pertenecientes a una URI.
 */
public final class URIQuery {

    /**
     * Esta clase no permite creación de instancias.
     */
    private URIQuery() {}

    /**
     * Parse map.
     *
     * @param query el query
     * @return el map
     * @throws UnsupportedEncodingException el unsupported encoding exception
     */
    public static Map<String, Object> parse(String query) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        parse(query, map);
        return map;
    }

    /**
     * Analiza un cadena de parámetros de URI como param1=val1&param2=val2&param3=val3...
     * y construye un mapa tomando el nombre de parámetro como clave e introduciendo el valor como un objeto.
     * Algunos nombres de parametros suelen repetirse, en estos casos, los valores son asignados a una misma clave,
     * pero son insertados como una lista.
     *
     * @param query      el query
     * @param parameters el parameters
     * @throws UnsupportedEncodingException el unsupported encoding exception
     */
    public static void parse(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {
        if (query != null) {
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                String[] param = pair.split("=");
                String key = null;
                String value = null;
                if (param.length > 0) key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));

                if (param.length > 1) value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));

                Object obj = parameters.get(key);
                if (obj != null) {
                    if (obj instanceof List) {
                        List<Object> values = (List<Object>) obj;
                        values.add(value);
                    } else if (obj instanceof String) {
                        List<Object> values = new LinkedList<>();
                        values.add(obj);
                        values.add(value);
                        parameters.put(key, values);
                    }
                } else parameters.put(key, value);
            }
        }
    }

    /**
     * Convierte una cadena de parametros de URI en un objeto JSON simple,
     * tomando los nombres de parametros como nombres de campo.
     * <p>
     * Si la cadena contiene un solo valor, este es convertido en una cadena simple.
     *
     * @param query el query
     * @return el string
     * @throws UnsupportedEncodingException el unsupported encoding exception
     */
    public static String queryToJson(String query) throws UnsupportedEncodingException {
        Map<String, Object> map = parse(query);

        int size = map.size();

        switch (size) {
            case 0:
                return "";
            case 1:
                return valueToJson(map.values().toArray()[0]);
            default:
                SSV.Builder jsonBuilder = new SSV.Builder(",");

                map.forEach((key, value) -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("\"").append(key).append("\"");
                    stringBuilder.append(":");
                    valueToJson(value, stringBuilder, true);
                    jsonBuilder.append(stringBuilder);
                });

                return "{" + jsonBuilder + "}";
        }
    }

    /**
     * Value to json.
     *
     * @param value           el value
     * @param stringBuilder   el string builder
     * @param stringCandidate el string candidate
     */
    private static void valueToJson(Object value, StringBuilder stringBuilder, boolean stringCandidate) {
        if (value instanceof List) {
            stringBuilder.append("[");
            stringBuilder.append(SSV.toSSVFormat((Collection<?>) value, ","));
            stringBuilder.append("]");
        } else if (stringCandidate)
            stringBuilder.append("\"").append(value).append("\"");
        else
            stringBuilder.append(value);
    }

    /**
     * Value to json string.
     *
     * @param value el value
     * @return el string
     */
    private static String valueToJson(Object value) {
        StringBuilder stringBuilder = new StringBuilder();
        valueToJson(value, stringBuilder, false);
        return stringBuilder.toString();
    }

    /**
     * To query string.
     *
     * @param object              el object
     * @param identifierGenerator el identifier generator
     * @return el string
     */
    public static String toQuery(Object object, Function<Field, String> identifierGenerator) {
        if (object.getClass().isArray()) {
            SSV.Builder builder = new SSV.Builder("&");
            for (Object o : (Object[]) object) builder.append(toQuery(o, identifierGenerator));
        } else if (ConstantType.isConstantType(object.getClass())) {
            return "value=" + object;
        }
        Formatter<Object> formatter = new PairKeyValueFormatter<>(
            identifierGenerator,
            "=",
            "&"
        );
        return formatter.format(object);
    }

}
