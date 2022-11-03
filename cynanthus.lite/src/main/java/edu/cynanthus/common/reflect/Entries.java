package edu.cynanthus.common.reflect;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * El tipo Entries.
 */
public final class Entries {

    /**
     * Instancia un nuevo Entries.
     */
    private Entries() {
    }

    /**
     * Permite obtener entries.
     *
     * @param instance            el instance
     * @param identifierGenerator el identifier generator
     * @return el entries
     */
    public static List<Map.Entry<String, Object>> getEntries(
        Object instance,
        Function<Field, String> identifierGenerator
    ) {
        List<Map.Entry<String, Object>> entries = new LinkedList<>();
        collectEntries(instance, entries, identifierGenerator);
        return entries;
    }

    /**
     * Collect entries.
     *
     * @param instance            el instance
     * @param entries             el entries
     * @param identifierGenerator el identifier generator
     */
    public static void collectEntries(
        Object instance,
        Collection<Map.Entry<String, Object>> entries,
        Function<Field, String> identifierGenerator
    ) {
        Objects.requireNonNull(entries);
        forEachEntry(instance, identifierGenerator, entries::add);
    }

    /**
     * For each entry.
     *
     * @param instance         el instance
     * @param clazz            el clazz
     * @param fieldAliasFinder el field alias finder
     * @param action           el action
     */
    public static void forEachEntry(
        Object instance,
        Class<?> clazz,
        Function<Field, String> fieldAliasFinder,
        Consumer<Map.Entry<String, Object>> action
    ) {
        Objects.requireNonNull(fieldAliasFinder);

        Class<?> parentClass = clazz.getSuperclass();

        if (parentClass != null && !parentClass.equals(Object.class) && !parentClass.isInterface())
            forEachEntry(instance, parentClass, fieldAliasFinder, action);

        for (Field field : clazz.getDeclaredFields()) {
            String name = fieldAliasFinder.apply(field);
            if (name != null) {
                Object value = ReflectUtil.safeGet(field, instance);

                if (value != null) {

                    Class<?> valueClass = value.getClass();
                    if (!ConstantType.isConstantType(valueClass)) {

                        if (valueClass.isArray())
                            for (Object o : (Object[]) value)
                                forEachEntry(o, fieldAliasFinder, action);
                        else forEachEntry(value, fieldAliasFinder, action);

                    } else action.accept(new EntryImpl<>(name, value));

                } else action.accept(new EntryImpl<>(name, null));
            }
        }
    }

    /**
     * For each entry.
     *
     * @param instance         el instance
     * @param fieldAliasFinder el field alias finder
     * @param action           el action
     */
    public static void forEachEntry(
        Object instance,
        Function<Field, String> fieldAliasFinder,
        Consumer<Map.Entry<String, Object>> action
    ) {
        forEachEntry(instance, instance.getClass(), fieldAliasFinder, action);
    }

    /**
     * For each entry.
     *
     * @param instance         el instance
     * @param fieldAliasFinder el field alias finder
     * @param consumer         el consumer
     */
    public static void forEachEntry(
        Object instance,
        Function<Field, String> fieldAliasFinder,
        BiConsumer<String, Object> consumer
    ) {
        forEachEntry(instance, fieldAliasFinder, entry -> consumer.accept(entry.getKey(), entry.getValue()));
    }

}
