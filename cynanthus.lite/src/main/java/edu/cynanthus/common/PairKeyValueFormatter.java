package edu.cynanthus.common;

import edu.cynanthus.common.reflect.Entries;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Function;

/**
 * El tipo Pair key value formatter.
 *
 * @param <T> el parámetro de tipo
 */
public class PairKeyValueFormatter<T> implements Formatter<T> {

    /**
     * El Field alias finder.
     */
    private final Function<Field, String> fieldAliasFinder;

    /**
     * El Key formatter.
     */
    private final Formatter<String> keyFormatter;
    /**
     * El Value formatter.
     */
    private final Formatter<Object> valueFormatter;

    /**
     * El Ssv builder.
     */
    private final SSV.Builder ssvBuilder;
    /**
     * El Correspondece symbol.
     */
    private final String correspondeceSymbol;

    /**
     * Instancia un nuevo Pair key value formatter.
     *
     * @param fieldAliasFinder     el field alias finder
     * @param keyFormatter         el key formatter
     * @param valueFormatter       el value formatter
     * @param correspondenceSymbol el correspondence symbol
     * @param separator            el separator
     */
    public PairKeyValueFormatter(
        Function<Field, String> fieldAliasFinder,
        Formatter<String> keyFormatter,
        Formatter<Object> valueFormatter,
        String correspondenceSymbol,
        String separator
    ) {
        this.fieldAliasFinder = Objects.requireNonNull(fieldAliasFinder);
        this.keyFormatter = Objects.requireNonNull(keyFormatter);
        this.valueFormatter = Objects.requireNonNull(valueFormatter);
        this.ssvBuilder = new SSV.Builder(separator);
        this.correspondeceSymbol = Objects.requireNonNull(correspondenceSymbol);
    }

    /**
     * Instancia un nuevo Pair key value formatter.
     *
     * @param fieldAliasFinder     el field alias finder
     * @param correspondenceSymbol el correspondence symbol
     * @param separator            el separator
     */
    public PairKeyValueFormatter(
        Function<Field, String> fieldAliasFinder,
        String correspondenceSymbol,
        String separator
    ) {
        this(fieldAliasFinder, Object::toString, Object::toString, correspondenceSymbol, separator);
    }

    /**
     * Format string.
     *
     * @param value el value
     * @return el string
     */
    @Override
    public String format(T value) {
        Entries.forEachEntry(value, fieldAliasFinder, (k, v) -> {
            if (v != null) {
                String pair = keyFormatter.format(k) + correspondeceSymbol + valueFormatter.format(v);
                ssvBuilder.append(pair);
            }
        });

        String string = ssvBuilder.toString();
        ssvBuilder.flush();
        return string;
    }

}
