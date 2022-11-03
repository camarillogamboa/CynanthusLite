package edu.cynanthus.bean;

/**
 * Un objeto Config es un bean que se encarga de representar y transportar las propiedades de una aplicación.
 * Las propiedades de la aplicación pueden ser representadas por un objeto donde cada campo es equivalente a
 * una propiedad del sistema cada una con su respectivo método set y get.
 *
 * @author L.G. Camarillo
 * @see Bean
 */
public interface Config extends Bean {

    /**
     * Realiza la clonación de este objeto restringiendo el tipo de retorno a
     * un objeto de tipo Config.
     *
     * @return el objeto Config resultado de la clonación
     */
    @Override
    Config clone();

}
