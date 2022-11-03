package edu.cynanthus.common;

import java.time.format.DateTimeFormatter;

/**
 * Provee de constantes de tipo DateTimeFormatter utilizadas en el formateo de fechas
 * para distintos usos.
 */
public interface TimePatterns {

    /**
     * La constante FILE_NAME_PATTERN define un patrón de tiempo considerando horas
     * minutos, segundos y milisegundos seperados por guion bajo.
     */
    DateTimeFormatter FILE_NAME_PATTERN = DateTimeFormatter.ofPattern("HH_mm_ss.SS");

    /**
     * La constante DIRECTORY_NAME_PATTERN define un patrón de fecha considerando dias, mes y año.
     * Este patrón orienta su uso para el nombrado de archivos.
     */
    DateTimeFormatter DIRECTORY_NAME_PATTERN = DateTimeFormatter.ofPattern("dd_MM_yyyy");

    /**
     * La constante LOG_FILE_NAME_PATTERN extiende los patrones anteriores creando un formateador
     * de fecha y hora orientado al nombrado de archivos
     */
    DateTimeFormatter LOG_FILE_NAME_PATTERN = DateTimeFormatter.ofPattern("dd_MM_yyyy-HH_mm_ss.SS");

}
