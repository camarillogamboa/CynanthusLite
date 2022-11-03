package edu.cynanthus.bean;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * Permite la identificación de campos con metainformación contenida a través
 * de la anotación JProperty.
 *
 * @author L.G. Camarillo
 * @see JProperty
 */
public enum FieldInformationSeeker implements Function<Field, String[]> {

    INSTANCE();

    /**
     * Evalúa un campo de clase en busca de la anotación JProperty, si el campo se encuentra anotado
     * se obtiene la instancia de la anotación y se procede a obtener la información contenida en ella.
     * Los datos obtenidos son almacenados en un arreglo, en total son 5 datos dispuestos de la siguiente manera
     *
     * <ul>
     *     <li>0 - Alias del campo</li>
     *     <li>1 - Valor por defecto para el campo</li>
     *     <li>2 - Descripción del campo</li>
     *     <li>3 - Nombre del método get para el campo</li>
     *     <li>4 - Nombre del método set para el campo</li>
     * </ul>
     *
     * @param field el campo a evaluar
     * @return el arreglo con la metainformación encontrada
     */
    @Override
    public String[] apply(Field field) {
        JProperty jProperty = field.getAnnotation(JProperty.class);
        if (jProperty != null) {
            return new String[]{
                jProperty.alias().isEmpty() || jProperty.alias().isBlank() ? field.getName() : jProperty.alias(),
                jProperty.defaultValue(),
                jProperty.info(),
                jProperty.getMethod(),
                jProperty.setMethod()
            };
        }
        return null;
    }

}
