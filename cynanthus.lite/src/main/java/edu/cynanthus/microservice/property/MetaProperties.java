package edu.cynanthus.microservice.property;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * El tipo Meta properties.
 */
public final class MetaProperties {

    /**
     * La constante INITIAL_STATE.
     */
    private static final int INITIAL_STATE = 0;
    /**
     * La constante COMMENT_STATE.
     */
    private static final int COMMENT_STATE = 1;
    /**
     * La constante PAIR_STATE.
     */
    private static final int PAIR_STATE = 2;

    /**
     * Instancia un nuevo Meta properties.
     */
    private MetaProperties() {
        throw new UnsupportedOperationException("No está permitido generar instancias de esta clase");
    }

    /**
     * Load meta properties map.
     *
     * @param reader el reader
     * @return el map
     * @throws IOException el io exception
     */
    public static Map<String, MetaProperty> loadMetaProperties(Reader reader) throws IOException {
        Map<String, MetaProperty> propertyMap = new TreeMap<>(String::compareTo);
        loadMetaProperties(reader, propertyMap);
        return propertyMap;
    }

    /**
     * Load meta properties.
     *
     * @param reader     el reader
     * @param properties el properties
     * @throws IOException el io exception
     */
    public static void loadMetaProperties(Reader reader, Map<String, MetaProperty> properties) throws IOException {
        Objects.requireNonNull(reader);
        Objects.requireNonNull(properties);

        StringBuilder commentBuilder = new StringBuilder();
        StringBuilder pairBuilder = new StringBuilder();

        for (int code = reader.read(), state = INITIAL_STATE; code != -1; code = reader.read())
            switch (state) {
                case INITIAL_STATE:
                    if (code != ' ' && code != '\n' && code != '\t') {
                        if (code == '#') {
                            if (commentBuilder.length() > 0) commentBuilder.append(System.lineSeparator());
                            state = COMMENT_STATE;
                            break;
                        } else state = PAIR_STATE;
                    } else break;
                case PAIR_STATE:
                    if (code != '\n') pairBuilder.append((char) code);
                    else {
                        putInToMap(commentBuilder, pairBuilder, properties);
                        state = INITIAL_STATE;
                    }
                    break;
                case COMMENT_STATE:
                    if (code != '\n') commentBuilder.append((char) code);
                    else state = INITIAL_STATE;
            }

        putInToMap(commentBuilder, pairBuilder, properties);
    }

    /**
     * Put in to map.
     *
     * @param commentBuilder el comment builder
     * @param pairBuilder    el pair builder
     * @param properties     el properties
     */
    private static void putInToMap(
        StringBuilder commentBuilder,
        StringBuilder pairBuilder,
        Map<String, MetaProperty> properties
    ) {
        String comment = commentBuilder.toString();
        String pair = pairBuilder.toString().trim();

        String[] pairArray = pair.split("=");

        if (pairArray.length == 2) {
            MetaProperty metaProperty = properties.get(pairArray[0]);
            if (metaProperty != null) metaProperty.setValue(pairArray[1]);
            else properties.put(pairArray[0], new MetaProperty(comment, pairArray[1]));
        }

        commentBuilder.setLength(0);
        pairBuilder.setLength(0);
    }

    /**
     * Save meta properties.
     *
     * @param properties el properties
     * @param writer     el writer
     * @throws IOException el io exception
     */
    public static void saveMetaProperties(Map<String, MetaProperty> properties, Writer writer) throws IOException {
        Objects.requireNonNull(properties);
        Objects.requireNonNull(writer);

        for (Map.Entry<String, MetaProperty> propertyEntry : properties.entrySet()) {
            String comment = propertyEntry.getValue().getMetaData();

            if (comment.length() > 0) {
                writer.write(stringToComment(comment));
                writer.write(System.lineSeparator());
            }

            writer.write(propertyEntry.getKey() + "=" + propertyEntry.getValue());
            writer.write(System.lineSeparator().repeat(2));
        }
    }

    /**
     * String to comment string.
     *
     * @param string el string
     * @return el string
     */
    private static String stringToComment(String string) {
        StringBuilder commentBuilder = new StringBuilder();

        boolean lineSeparatorDetected = true;

        for (int i = 0; i < string.length(); i++) {
            char code = string.charAt(i);

            if (lineSeparatorDetected && code != '#') commentBuilder.append("#");
            commentBuilder.append(code);
            lineSeparatorDetected = code == '\n';
        }
        return commentBuilder.toString();
    }

}
