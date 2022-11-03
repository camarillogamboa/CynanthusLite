package edu.cynanthus.microservice;

import java.io.File;

/**
 * La interface Archiveable context.
 */
public interface ArchiveableContext extends Context {

    /**
     * Permite obtener context directory.
     *
     * @return el context directory
     */
    File getContextDirectory();

    /**
     * Permite obtener log directory.
     *
     * @return el log directory
     */
    File getLogDirectory();

    /**
     * Permite obtener properties file.
     *
     * @return el properties file
     */
    File getMetaPropertiesFile();

}
