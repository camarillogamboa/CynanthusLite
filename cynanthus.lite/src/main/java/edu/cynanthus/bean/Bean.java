package edu.cynanthus.bean;

import java.io.Serializable;

/**
 * Un Java Bean es un objeto definido a través de una clase que cumple con los
 * elementos siguientes.
 * <ul>
 *     <li>Un constructor sin argumentos</li>
 *     <li>Acceso a campos como propiedades</li>
 * </ul>
 * <p>
 * Estas caracteristicas tan elementales son aprovechadas en la gestión de instancias
 * de un bean a través de tecnologías como la reflexión. Sin embargo, es responsabilidad
 * de las subclases asegurarse que estas dos reglas se cumplan, por lo que esta interface solo sirve
 * como unificador estratégíco de una jerarquía de beans que establece que se debe realizar también la implementación
 * propia de métodos comunes de un objeto java.
 * Se sugiere asegurar la integridad de los datos de los campos de cada bean utilizando las anotaciones de la
 * especificación BeanValidation.
 *
 * @author L.G. Camarillo
 */
public interface Bean extends Serializable {

    /**
     * Permite realizar una copia del estado actual del objeto devolviendolo
     * en una nueva instancia del mismo. De las clases que implementen este método
     * dependerá el nivel de profundidad de la clonación, por lo que algunas veces se obtendrá un arbol de objetos
     * completamente nuevo, o simplemente se realizará una copia de las referencias contenidas por el mismo.
     *
     * @return un nuevo objeto con el estado actual del bean
     */
    Bean clone();

    /**
     * Se sugiere que todos los beans tengan su propia implementación del método equals para
     * garantizar la correcta comparación entre 2 objetos.
     *
     * @param o el objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    boolean equals(Object o);

    /**
     * Genera y retorna un código hash que representa la suma de las posiciones hash en memoria
     * de todos los objetos subyacentes a este y este.
     *
     * @return el código hash
     */
    @Override
    int hashCode();

    /**
     * Retorna una representación de cadena de caracteres del objeto
     *
     * @return la representación del objeto
     */
    @Override
    String toString();

}
