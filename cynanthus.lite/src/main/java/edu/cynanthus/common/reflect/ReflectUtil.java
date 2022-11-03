package edu.cynanthus.common.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * El tipo Reflect util.
 */
public final class ReflectUtil {

    /**
     * Instancia un nuevo Reflect util.
     */
    private ReflectUtil() {
    }

    /**
     * Safe invocation object.
     *
     * @param method   el method
     * @param instance el instance
     * @param args     el args
     * @return el object
     */
    public static Object safeInvocation(Method method, Object instance, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException ex) {
        }
        return null;
    }

    /**
     * Adaptative invocation t.
     *
     * @param <T>      el parámetro de tipo
     * @param method   el method
     * @param instance el instance
     * @param args     el args
     * @return el t
     */
    public static <T> T adaptativeInvocation(Method method, Object instance, Object... args) {
        return (T) safeInvocation(method, instance, args);
    }

    /**
     * Safe set.
     *
     * @param field    el field
     * @param instance el instance
     * @param value    el value
     */
    public static void safeSet(Field field, Object instance, Object value) {
        boolean accessible = field.canAccess(instance);
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
        }
        field.setAccessible(accessible);
    }

    /**
     * Safe get object.
     *
     * @param field    el field
     * @param instance el instance
     * @return el object
     */
    public static Object safeGet(Field field, Object instance) {
        Object value = null;
        boolean accessible = field.canAccess(instance);
        field.setAccessible(true);
        try {
            value = field.get(instance);
        } catch (IllegalAccessException e) {
        }
        field.setAccessible(accessible);
        return value;
    }

    public static Class<?> arrayTypeOf(Class<?> arrayClass) {
        return Array.newInstance(arrayClass, 0).getClass();
    }

}
