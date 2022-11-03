package edu.cynanthus.microservice;

import edu.cynanthus.bean.Config;
import edu.cynanthus.bean.FieldAliasFinder;
import edu.cynanthus.common.resource.ResourceException;
import edu.cynanthus.microservice.property.MetaProperty;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * La interface {@code Context} crea un contexto de aplicación con la información
 * implicada en la ejecución de la misma.
 */
public interface Context {

    /**
     * Permite obtener el nombre del contexto que en aplicaciones de un solo contexto es
     * equivalente al nombre de la aplicación.
     *
     * @return el nombre del contexto
     */
    String getName();

    /**
     * Permite obtener el tipo de clase que transporta el estado de las propieades.
     *
     * @return la clase de configuración del contexto
     */
    Class<? extends Config> getConfigClass();

    /**
     * Realiza la carga de las propieadades desde el recurso subyacente al contexto.
     *
     * @return true si se cargaron correctamente las propiedades, false en caso contrario
     */
    boolean loadMetaProperties();

    /**
     * Guarda el conjunto de propieades administrados por el contexto sobre un recurso subyacente.
     *
     * @return true si se guardaron correctamente las propiedades, false en caso contrario
     */
    boolean saveMetaProperties();

    /**
     * Carga propiedades no administradas por el contexto, obteniendolas desde un recurso dentro
     * del alcance del contexto en forma de un objeto de la clase {@code java.util.Properties}
     *
     * @param name el nombre del recurso que contiene las propieades a cargar
     * @return el objeto java.util.Properties con las propiedades encontradas en el recurso especificado
     */
    Properties loadProperties(String name);

    /**
     * Guarda las propiedades suministradas en el objeto {@code java.util.Properties} para escribirlas como un recurso
     * del contexto con el nombre proporcionado
     *
     * @param properties el properties
     * @param name       el name
     * @return el boolean
     */
    boolean saveProperties(Properties properties, String name);

    /**
     * Permite obtener la propiedad especificada por el parámetro {@code key}, si la propieadad
     * no se encuentra simplemente retorna null.
     *
     * @param key la llave de la propiedad a obtener
     * @return la propiedad especificada
     */
    MetaProperty getMetaProperty(String key);

    /**
     * Coloca una propiedad sobre .
     *
     * @param key          el key
     * @param metaProperty el meta property
     */
    void putMetaProperty(String key, MetaProperty metaProperty);

    /**
     * Update properties from.
     *
     * @param configObject el config object
     * @param aliasFinder  el alias finder
     * @return el boolean
     */
    boolean updateMetaPropertiesFrom(Config configObject, Function<Field, String> aliasFinder);

    /**
     * Update properties from.
     *
     * @param configObject el config object
     */
    default void updateMetaPropertiesFrom(Config configObject) {
        updateMetaPropertiesFrom(configObject, FieldAliasFinder.INSTANCE);
    }

    /**
     * Copy properties to.
     *
     * @param configObject el config object
     * @param aliasFinder  el alias finder
     */
    void copyMetaPropertiesTo(Config configObject, Function<Field, String> aliasFinder);

    /**
     * Copy properties to.
     *
     * @param configObject el config object
     */
    default void copyMetaPropertiesTo(Config configObject) {
        copyMetaPropertiesTo(configObject, FieldAliasFinder.INSTANCE);
    }

    /**
     * Permite obtener properties as config object.
     *
     * @param <T> el parámetro de tipo
     * @return el properties as config object
     * @throws Exception el exception
     */
    <T extends Config> T getMetaPropertiesAsConfigObject() throws Exception;

    /**
     * Load resource input stream.
     *
     * @param name el name
     * @return el input stream
     * @throws ResourceException el resource exception
     */
    InputStream loadResource(String name) throws ResourceException;

    /**
     * Save resource.
     *
     * @param name        el name
     * @param inputStream el input stream
     * @throws ResourceException el resource exception
     */
    void saveResource(String name, InputStream inputStream) throws ResourceException;

    /**
     * Load object t.
     *
     * @param <T>  el parámetro de tipo
     * @param name el name
     * @param type el type
     * @return el t
     */
    <T> T loadObject(String name, Type type);

    /**
     * Guarda el estado de un objeto en el recurso con nombre especificado.
     * Es responsabilidad de la implementación el definir el formato de representación del objeto.
     *
     * @param name   el name
     * @param object el object
     * @return el boolean
     */
    boolean saveObject(String name, Object object);

    /**
     * Permite obtener el administrador de usuarios del contexto
     *
     * @return el objeto SystemUserManagement del contexto
     */
    SystemUserManagement getUserManagement();

    /**
     * Permite obtener el mapa de mensajes del contexto, los mensajes estan organizados por idiomas
     * según el estandar de internacionalización y son cargados a partir de un recurso con nombre messages.properties
     *
     * @return el mapa de mensajes del contexto
     */
    Map<String, ? extends Map<?, ?>> getMessages();

    /**
     * Permite obtener un registrador de eventos a partir del nombre especificado.
     * La estrategia para obtener el registrador se basa en la especificada en el API java.logging,
     * con la diferencia que todos los registradores obtenidos en este método son desendientes directos del registrador
     * de la clase que implementa esta interface, por lo que se hereda la mayor parte de la configuración desde
     * ese registrador.
     *
     * @param name el name
     * @return el logger
     */
    Logger getLogger(String name);

}
