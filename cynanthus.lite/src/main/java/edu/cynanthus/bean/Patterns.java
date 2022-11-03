package edu.cynanthus.bean;

/**
 * Define algunos patrones ReGex de uso común
 *
 * @author L.G. Camarillo
 */
public interface Patterns {

    /**
     * Patrón utilizado para analizar la sintaxis de una dirección física (mac).
     * EL patrón solo evalúa como válidas las direcciones mac que se encuentran en la conocida notación de
     * 6 pares separados por 2 puntos, admite mayúsculas y minúsculas.
     */
    String MAC = "[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}";

}
