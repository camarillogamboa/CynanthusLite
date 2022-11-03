package edu.cynanthus.bean;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Pone en marcha una implementación de la especificación BeanValidation para módulos de aplicación
 * que no hagan uso de algún framework
 *
 * @author L.G. Camarillo
 * @see javax.validation.Validator
 */
public final class BeanValidation {

    /**
     * Instancia por defector de un validador de beans sin interpolador de mensajes basados en archivos.
     */
    private static final Validator DEFAULT_VALIDATOR;

    static {

        try (
            ValidatorFactory factory =
                Validation.byDefaultProvider().configure().messageInterpolator(
                    new ParameterMessageInterpolator()
                ).buildValidatorFactory()
        ) {
            DEFAULT_VALIDATOR = factory.getValidator();
        }
    }

    /**
     * No se puede crear instancias de esta clase.
     */
    private BeanValidation() {
    }

    /**
     * Valida un bean especificando el o los grupos que se validaran en el bean
     *
     * @param <T>    el parámetro de tipo de bean a validar
     * @param bean   el bean a validar
     * @param groups los grupos de validación a ejecutar
     * @return el conjunto de restricciones violadas por el bean
     */
    public static <T extends Bean> Set<ConstraintViolation<T>> validate(T bean, Class<?>... groups) {
        return DEFAULT_VALIDATOR.validate(bean, groups);
    }

    /**
     * Realiza la validación de un bean y la lanza las restricciones encontradas como una excepción.
     *
     * @param <T>    el parámetro de tipo del bean a validar
     * @param bean   el bean a validar
     * @param groups los grupos de validación a ejecutar
     * @throws ConstraintViolationException al encontrarse con violación de restricciones
     */
    public static <T extends Bean> void validateAndThrow(
        T bean,
        Class<?>... groups
    ) throws ConstraintViolationException {
        Set<ConstraintViolation<T>> constraintViolations = validate(bean, groups);
        if (!constraintViolations.isEmpty()) throw new ConstraintViolationException(constraintViolations);
    }

    /**
     * Realiza una interpolación básica de mensajes a partir de un conjunto
     * de violaciones de restricciones y un mapa de mensajes.
     * El proceso consiste en iterar a través de cada una de las
     * restricciones de violación de las cuales se obtiene la plantilla
     * del mensaje, esta plantilla es utilizada como llave de busqueda en el mapa
     * de mensajes. Si existe un mensaje en el mapa para la plantilla especificada,
     * este será agregado a la lista de resultados, si no lo encuentra será agregada la plantilla
     * como mensaje en la lista de resultados.
     *
     * @param constraintViolations el conjunto de violaciones de restricciones
     * @param messages             el mapa de mensajes
     * @param list                 la lista de mensajes interpolados
     */
    public static void basicInterpolation(
        Set<ConstraintViolation<?>> constraintViolations,
        Map<?, ?> messages,
        List<String> list
    ) {
        constraintViolations.forEach(constraintViolation -> {
            String message = constraintViolation.getMessageTemplate();
            if (messages != null) {
                Object obj = messages.get(message.substring(2, message.length() - 1));
                if (obj != null) message = obj.toString();
            }
            list.add(message);
        });
    }

    /**
     * Realiza exactamente la misma tarea que el método anterior, con la diferencia de que este
     * crea y utiliza una lista propia para almacenar los mensajes interpolados tras la busqueda
     * en el mapa de mensajes.
     *
     * @param constraintViolations el conjunto de violaciones de restricciones
     * @param messages             el mapa de mensajes
     * @return la lista de mensajes interpolados
     * @see BeanValidation#basicInterpolation(Set, Map, List)
     */
    public static List<String> basicInterpolation(Set<ConstraintViolation<?>> constraintViolations, Map<?, ?> messages) {
        List<String> list = new LinkedList<>();
        basicInterpolation(constraintViolations, messages, list);
        return list;
    }

}
