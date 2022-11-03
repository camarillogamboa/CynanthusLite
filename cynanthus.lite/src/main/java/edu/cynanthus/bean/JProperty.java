package edu.cynanthus.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JProperty pretente recopilar información adicional para un campo, como un alias, valor por defecto
 * información adicional entre otros.
 *
 * @author L.G. Camarillo
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JProperty {

    /**
     * Contiene un alias para el campo anotado, este dato no es obligatorio.
     *
     * @return el alias del campo
     */
    String alias() default "";

    /**
     * Contiene el valor por defecto para el campo anotado, este dato no es obligatorio.
     *
     * @return el valor por defecto del campo
     */
    String defaultValue() default "";

    /**
     * Contiene información adicional del campo, este dato no es obligatorio.
     *
     * @return información adicional del campo
     */
    String info() default "";

    /**
     * Contiene el nombre completo del método get del campo anotado
     *
     * @return el nombre del método get para el campo
     */
    String getMethod() default "";

    /**
     * Contiene el nombre completo del método set del campo anotado
     *
     * @return el nombre del método set para el campo
     */
    String setMethod() default "";

}
