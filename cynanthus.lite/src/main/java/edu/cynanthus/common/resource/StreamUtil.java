package edu.cynanthus.common.resource;

import java.io.*;
import java.util.function.Supplier;

/**
 * El tipo Stream util.
 */
public final class StreamUtil {

    /**
     * Instancia un nuevo Stream util.
     */
    private StreamUtil() {
    }

    /**
     * To string string.
     *
     * @param reader el reader
     * @return el string
     * @throws IOException el io exception
     */
    public static String toString(Reader reader) throws IOException {
        try (Writer writer = new StringWriter()) {
            reader.transferTo(writer);
            return writer.toString();
        }
    }

    /**
     * To string string.
     *
     * @param inputStream el input stream
     * @return el string
     * @throws IOException el io exception
     */
    public static String toString(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream)) {
            return toString(reader);
        }
    }

    /**
     * As input stream input stream.
     *
     * @param string el string
     * @return el input stream
     */
    public static InputStream asInputStream(String string) {
        return new ByteArrayInputStream(string.getBytes());
    }

    /**
     * As reader reader.
     *
     * @param string el string
     * @return el reader
     */
    public static Reader asReader(String string) {
        return new InputStreamReader(asInputStream(string));
    }

    /**
     * To imput stream supplier supplier.
     *
     * @param string el string
     * @return el supplier
     */
    public static Supplier<InputStream> toImputStreamSupplier(String string) {
        return () -> asInputStream(string);
    }

    /**
     * To imput stream supplier supplier.
     *
     * @param file el file
     * @return el supplier
     */
    public static Supplier<InputStream> toImputStreamSupplier(File file) {
        return () -> {
            try {
                return FileAccesObject.INPUT_STREAM_FAO.read(file);
            } catch (ResourceException e) {
                return null;
            }
        };
    }

}
