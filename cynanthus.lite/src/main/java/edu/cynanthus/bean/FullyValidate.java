package edu.cynanthus.bean;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * Esta interface de marcado denota un grupo de validación para la especificación
 * BeanVaidation. El uso de este grupo esta sugerido para la validación de campos en
 * clases que representen entidades de datos, marcandolos así para su completa validación.
 */
@GroupSequence(value = {Default.class, Required.class, ValidInfo.class})
public interface FullyValidate {
}
