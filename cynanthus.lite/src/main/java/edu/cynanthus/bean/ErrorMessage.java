package edu.cynanthus.bean;

import java.util.List;
import java.util.Objects;

/**
 * Representa un mensaje de error acompañado con un código de error y las causas del error.
 * Esta clase puede tener diversos usos, pero el uso mas común es el que se le da al lanzar excepciones
 * personalizadas en APIs que manejen un sistema de códigos de error y se requiera detallar las causas sin
 * necesidad de recurrir a la construcción de pila habitual de una impresión de excepción.
 *
 * @param <T> el parámetro de tipo para las causas del error
 */
public class ErrorMessage<T> {

    /**
     * El código de error
     */
    private final int code;
    /**
     * El mensaje del error
     */
    private final String message;
    /**
     * Las causas del error.
     * Este campo se maneja como una lista genérica de causas, donde se deja
     * el tipo de causa a la implementación final con el objetivo de que
     * pueda ser aprovechado en la construcción de complejos árboles de mensajes de error.
     */
    private final List<T> causes;

    /**
     * Crea una nueva instancia de ErrorMenssage a partir de un código de error
     * un mensaje y una lista de causas.
     *
     * @param code    el código de error
     * @param message el mensaje del error
     * @param causes  las causas del error
     */
    public ErrorMessage(int code, String message, List<T> causes) {
        this.code = code;
        this.message = message;
        this.causes = causes;
    }

    /**
     * Crea una nueva instancia de ErrorMessage a partir de un código de error y un mensaje.
     * La lista de causas es establecida como null.
     *
     * @param code    el código de error
     * @param message el mensaje del error
     */
    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
        this.causes = null;
    }

    /**
     * Crea una nueva instancia de ErrorMessage pero estableciendo todos los campos a su valor cero.
     * Este constructor no puede ser utilizado fuera del paquete, su uso esta orientado a llamadas por reflexión.
     */
    ErrorMessage() {
        this.code = 0;
        this.message = "";
        this.causes = null;
    }

    /**
     * Permite obtener el código de error.
     *
     * @return el código de error
     */
    public int getCode() {
        return code;
    }

    /**
     * Permite obtener el mensaje.
     *
     * @return el mensaje del error
     */
    public String getMessage() {
        return message;
    }

    /**
     * Permite obtener una lista de causas del error
     *
     * @return la lista de causas
     */
    public List<T> getCauses() {
        return causes;
    }

    /**
     * Permite determinar ei este objeto es igual a otro.
     *
     * @param o el objeto a comparar con este
     * @return true si los objetos comparados son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage<?> that = (ErrorMessage<?>) o;
        return code == that.code && Objects.equals(message, that.message) && Objects.equals(causes, that.causes);
    }

    /**
     * Genera y retorna un código hash que representa la suma de las posiciones hash en memoria
     * de todos los objetos subyacentes a este y este.
     *
     * @return el código hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, message, causes);
    }

    /**
     * Retorna una representación de cadena de caracteres del objeto
     *
     * @return la representación del objeto
     */
    @Override
    public String toString() {
        return "{" +
            "code:" + code +
            ",message:'" + message + '\'' +
            ",causes:" + causes +
            '}';
    }

}
