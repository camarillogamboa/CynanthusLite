package edu.cynanthus.bean;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * Permite evaluar un campo de clase con el fin de determinar si este cuenta con
 * la metainformación necesaria para reconocer un sobrenombre del campo.
 * La metainformación es reconocida al encontrarse con la anotación JProperty,
 * si el campo cuenta con la anotación, se obtiene un sombrenombre, si la anotación no define
 * un sobrenombre entonces se retornará el nombre del campo.
 * <p>
 * Esta utilidad ha sido definida como una enumeración de un único elemento ya que no
 * es necesario generar mas de una instancia de la clase.
 *
 * @author L.G. Camarillo
 * @see JProperty
 */
public enum FieldAliasFinder implements Function<Field, String> {

    /**
     * La única instancia que existe de esta clase
     */
    INSTANCE();

    /**
     * Evalúa el campo proprocionado por parámetro y lo analiza en busca de la anotación JProperty,
     * si dicha anotación es encontrada, entonces se procederá a leer el sobrenombre asignado al campo,
     * si en caso contrario la anotación no esta presente sobre el campo, entonces el campo es ignorado retornando
     * null al invocador del método
     *
     * @param field el campo a evaluar
     * @return el string del sobrenombre del campo
     */
    @Override
    public String apply(Field field) {
        JProperty jProperty = field.getAnnotation(JProperty.class);
        if (jProperty != null) {
            String alias = jProperty.alias();
            return alias.isEmpty() || alias.isBlank() ? field.getName() : alias;
        }
        return null;
    }

}
