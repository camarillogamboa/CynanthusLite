package edu.cynanthus.microservice;

import com.google.gson.JsonSyntaxException;
import edu.cynanthus.bean.Config;
import edu.cynanthus.common.TimePatterns;
import edu.cynanthus.common.json.JsonProvider;
import edu.cynanthus.common.reflect.ConstantType;
import edu.cynanthus.common.reflect.Entries;
import edu.cynanthus.common.reflect.ReflectUtil;
import edu.cynanthus.common.resource.FileAccesObject;
import edu.cynanthus.common.resource.ResourceException;
import edu.cynanthus.common.security.AgentUser;
import edu.cynanthus.common.security.SystemUser;
import edu.cynanthus.microservice.property.MetaProperties;
import edu.cynanthus.microservice.property.MetaProperty;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.logging.*;

/**
 * El tipo Archiveable context.
 */
final class ArchiveableContextImpl implements ArchiveableContext, TimePatterns {

    /**
     * La constante STD_OUT_OPTION.
     */
    private static final String STD_OUT_OPTION = "std-out";

    /**
     * El Name.
     */
    private final String name;
    /**
     * El Context directory.
     */
    private final File contextDirectory;
    /**
     * El Config class.
     */
    private final Class<? extends Config> configClass;

    /**
     * El Logger.
     */
    private final Logger logger;
    /**
     * El Log directory.
     */
    private final File logDirectory;

    /**
     * El Properties.
     */
    private final Map<String, MetaProperty> metaProperties;
    /**
     * El Properties file.
     */
    private final File metaPropertiesFile;

    /**
     * El System user management.
     */
    private final SystemUserManagement systemUserManagement;

    /**
     * El Messages.
     */
    private final Map<String, Properties> messages;

    /**
     * Instancia un nuevo Archiveable context.
     *
     * @param name                   el name
     * @param contextDirectory       el context directory
     * @param configClass            el config class
     * @param mainUser               el main user
     * @param fieldInformationSeeker el field information seeker
     * @param args                   el args
     */
    ArchiveableContextImpl(
        String name,
        File contextDirectory,
        Class<? extends Config> configClass,
        AgentUser mainUser,
        Function<Field, String[]> fieldInformationSeeker,
        String[] args
    ) {
        this.name = Objects.requireNonNull(name);
        this.contextDirectory = Objects.requireNonNull(contextDirectory);
        this.configClass = Objects.requireNonNull(configClass);
        Objects.requireNonNull(fieldInformationSeeker);

        FileAccesObject.createDirectoryIfNoExists(contextDirectory);

        /*-----------------------------------Estableciendo nuevos controladores de log--------------------------------*/
        this.logger = Logger.getLogger(name);

        logger.setUseParentHandlers(false);
        if (args.length >= 1 && args[0].equals(STD_OUT_OPTION)) logger.addHandler(new ConsoleHandler());

        this.logDirectory = new File(contextDirectory + "/log");

        try {
            FileAccesObject.createDirectoryIfNoExists(logDirectory);
            String logFile = logDirectory + "/" + LocalDateTime.now().format(LOG_FILE_NAME_PATTERN) + ".log";
            Handler fileHandler = new FileHandler(logFile);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.info("Archivo de registro iniciado en " + (new File(logFile).getAbsolutePath()));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "No fue posible iniciar el archivo de registro", ex);
        }

        logger.info("Iniciando contexto " + name.replace('.', ' ').toUpperCase());

        /*---------------------------------------Preparando entorno de propiedades------------------------------------*/
        logger.info("Cargando propiedades del contexto...");

        this.metaProperties = new TreeMap<>(String::compareTo);
        this.metaPropertiesFile = new File(contextDirectory + "/properties.conf");

        for (Field field : configClass.getDeclaredFields()) {
            String[] propertyInfo = fieldInformationSeeker.apply(field);
            if (propertyInfo != null)
                metaProperties.put(propertyInfo[0], MetaProperty.asMetaProperty(propertyInfo[2], propertyInfo[1]));
        }

        /*--------------------------------Cargando propiedades establecidas por el usuario----------------------------*/
        if (!loadMetaProperties()) saveMetaProperties();

        this.systemUserManagement = new SystemUserManagementImpl(mainUser);
        systemUserManagement.loadSystemUsers();

        /*--------------------------------------------Cargando mapa de mensajes---------------------------------------*/
        this.messages = new TreeMap<>(String::compareTo);

        File[] contextFiles = contextDirectory.listFiles(file -> file.getName().endsWith(".properties"));

        if (contextFiles != null)
            for (File file : contextFiles) {
                String fileName = file.getName().split("\\.")[0];
                if (fileName.startsWith("messages")) {
                    Properties properties = loadProperties(file.getName());
                    if (properties != null) {
                        String subName = fileName.substring(8);
                        if (subName.isEmpty()) messages.put("default", properties);
                        else messages.put(subName.substring(1), properties);
                    }
                }
            }
    }

    /**
     * Instancia un nuevo Archiveable context.
     *
     * @param name                  el name
     * @param configClass           el config class
     * @param mainUser              el main user
     * @param propertyInfoGenerator el property info generator
     * @param args                  el args
     */
    ArchiveableContextImpl(
        String name,
        Class<? extends Config> configClass,
        AgentUser mainUser,
        Function<Field, String[]> propertyInfoGenerator,
        String[] args
    ) {
        this(name, new File(name), configClass, mainUser, propertyInfoGenerator, args);
    }

    /**
     * Permite obtener name.
     *
     * @return el name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Permite obtener context directory.
     *
     * @return el context directory
     */
    @Override
    public File getContextDirectory() {
        return contextDirectory;
    }

    /**
     * Permite obtener config class.
     *
     * @return el config class
     */
    @Override
    public Class<? extends Config> getConfigClass() {
        return configClass;
    }

    /**
     * Permite obtener log directory.
     *
     * @return el log directory
     */
    @Override
    public File getLogDirectory() {
        return logDirectory;
    }

    /**
     * Permite obtener properties file.
     *
     * @return el properties file
     */
    @Override
    public File getMetaPropertiesFile() {
        return metaPropertiesFile;
    }

    /**
     * Load properties boolean.
     *
     * @return el boolean
     */
    @Override
    public boolean loadMetaProperties() {
        if (metaPropertiesFile.exists()) {
            try (Reader reader = new FileReader(metaPropertiesFile)) {
                MetaProperties.loadMetaProperties(reader, metaProperties);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "No fue posible cargar las propiedades desde " + metaPropertiesFile, ex);
                return false;
            }
            return true;
        } else logger.info("No existe un archivo de propiedades definido, se usará la configuración por defecto");
        return false;
    }

    /**
     * Save properties boolean.
     *
     * @return el boolean
     */
    @Override
    public boolean saveMetaProperties() {
        try {
            if (metaPropertiesFile.exists() || metaPropertiesFile.createNewFile()) {
                try (Writer writer = new FileWriter(metaPropertiesFile)) {
                    MetaProperties.saveMetaProperties(metaProperties, writer);
                }
                return true;
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "No fue posible actualizar el archivo de propiedades", ex);
        }
        return false;
    }

    /**
     * Load properties properties.
     *
     * @param name el name
     * @return el properties
     */
    @Override
    public Properties loadProperties(String name) {
        try (InputStream in = loadResource(name)) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        } catch (ResourceException | IOException ex) {
            return null;
        }
    }

    /**
     * Save properties boolean.
     *
     * @param properties el properties
     * @param name       el name
     * @return el boolean
     */
    @Override
    public boolean saveProperties(Properties properties, String name) {
        try (OutputStream out = new FileOutputStream(toContextFile(name))) {
            properties.store(out, "");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Permite obtener property.
     *
     * @param key el key
     * @return el property
     */
    @Override
    public MetaProperty getMetaProperty(String key) {
        return metaProperties.get(name + "." + key);
    }

    /**
     * Put property.
     *
     * @param key          el key
     * @param metaProperty el meta property
     */
    @Override
    public void putMetaProperty(String key, MetaProperty metaProperty) {
        metaProperties.put(name + "." + key, Objects.requireNonNull(metaProperty));
    }

    /**
     * Update properties from.
     *
     * @param configObject el config object
     * @param aliasFinder  el alias finder
     * @return el boolean
     */
    @Override
    public boolean updateMetaPropertiesFrom(Config configObject, Function<Field, String> aliasFinder) {
        if (configObject.getClass().equals(configClass)) {
            AtomicBoolean updateFlag = new AtomicBoolean(false);
            Entries.forEachEntry(configObject, aliasFinder, (k, v) -> {
                if (v != null) {
                    MetaProperty property = metaProperties.get(k);
                    if (property != null) {
                        String newValue = v.toString();
                        if (property.getValue() == null || !property.getValue().equals(newValue)) {
                            logger.info(
                                "Estableciendo \""
                                    + k
                                    + "\" de \""
                                    + property.getValue()
                                    + "\" a \""
                                    + newValue
                                    + "\""
                            );
                            property.setValue(newValue);
                            updateFlag.set(true);
                        }
                    }
                }
            });
            if (updateFlag.get()) {
                logger.info("Actualizando archivo de propiedades");
                saveMetaProperties();
                return true;
            } else logger.info("Ninguna propiedad fue actualizada");
        } else logger.warning("Objeto de configuración no válido para este contexto");
        return false;
    }

    /**
     * Copy properties to.
     *
     * @param configObject el config object
     * @param aliasFinder  el alias finder
     */
    @Override
    public void copyMetaPropertiesTo(Config configObject, Function<Field, String> aliasFinder) {
        if (configObject.getClass().equals(configClass))
            for (Field field : configObject.getClass().getDeclaredFields()) {
                String name = aliasFinder.apply(field);
                if (name != null) {
                    ConstantType constantType = ConstantType.constantTypeOf(field.getType());
                    if (constantType != null) {
                        MetaProperty property = metaProperties.get(name);
                        if (property != null) {
                            Object value = constantType.parse(property.getValue());
                            ReflectUtil.safeSet(field, configObject, value);
                        }
                    } else logger.warning("El contenedor de valores de propiedad debe declarar tipos básicos");
                }
            }
        else logger.warning("Objeto de configuración de tipo no válido para este contexto");
    }

    /**
     * Permite obtener properties as config object.
     *
     * @param <T> el parámetro de tipo
     * @return el properties as config object
     * @throws Exception el exception
     */
    @Override
    public <T extends Config> T getMetaPropertiesAsConfigObject() throws Exception {
        T configObject = (T) configClass.getConstructor().newInstance();
        copyMetaPropertiesTo(configObject);
        return configObject;
    }

    /**
     * Load resource input stream.
     *
     * @param name el name
     * @return el input stream
     * @throws ResourceException el resource exception
     */
    @Override
    public InputStream loadResource(String name) throws ResourceException {
        return FileAccesObject.INPUT_STREAM_FAO.read(toContextFile(name));
    }

    /**
     * Save resource.
     *
     * @param name        el name
     * @param inputStream el input stream
     * @throws ResourceException el resource exception
     */
    @Override
    public void saveResource(String name, InputStream inputStream) throws ResourceException {
        File file = toContextFile(name);
        if (file.exists())
            FileAccesObject.INPUT_STREAM_FAO.update(file, inputStream);
        else FileAccesObject.INPUT_STREAM_FAO.create(file, inputStream);
    }

    /**
     * Load object t.
     *
     * @param <T>  el parámetro de tipo
     * @param name el name
     * @param type el type
     * @return el t
     */
    @Override
    public <T> T loadObject(String name, Type type) {
        try (Reader reader = new InputStreamReader(loadResource(name))) {
            return JsonProvider.getInstance().fromJson(reader, type);
        } catch (ResourceException | IOException | JsonSyntaxException ex) {
            return null;
        }
    }

    /**
     * Save object boolean.
     *
     * @param name   el name
     * @param object el object
     * @return el boolean
     */
    @Override
    public boolean saveObject(String name, Object object) {
        String jsonString = JsonProvider.toJson(object);
        try (InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes())) {
            saveResource(name, inputStream);
        } catch (ResourceException | IOException ex) {
            return false;
        }
        return true;
    }

    /**
     * Permite obtener user management.
     *
     * @return el user management
     */
    @Override
    public SystemUserManagement getUserManagement() {
        return systemUserManagement;
    }

    /**
     * Permite obtener messages.
     *
     * @return el messages
     */
    @Override
    public Map<String, ? extends Map<?, ?>> getMessages() {
        return messages;
    }

    /**
     * Permite obtener logger.
     *
     * @param name el name
     * @return el logger
     */
    @Override
    public Logger getLogger(String name) {
        return Logger.getLogger(this.name + "." + name);
    }

    private File toContextFile(String path) {
        File file = new File(path);
        if (file.isAbsolute()) return file;
        else return new File(contextDirectory + "/" + path);
    }


    /**
     * El tipo System user management.
     */
    private final class SystemUserManagementImpl implements SystemUserManagement {

        /**
         * El Main user.
         */
        private final AgentUser mainUser;
        /**
         * El System users.
         */
        private final Map<String, SystemUser> systemUsers;

        /**
         * Instancia un nuevo System user management.
         *
         * @param mainUser el main user
         */
        SystemUserManagementImpl(AgentUser mainUser) {
            this.mainUser = mainUser;
            this.systemUsers = new TreeMap<>(String::compareTo);
        }

        /**
         * Permite obtener main user.
         *
         * @return el main user
         */
        @Override
        public AgentUser getMainUser() {
            return mainUser;
        }

        /**
         * Put system user.
         *
         * @param systemUser el system user
         */
        @Override
        public void putSystemUser(SystemUser systemUser) {
            systemUsers.put(systemUser.getUsername(), systemUser);
        }

        /**
         * Permite obtener system user.
         *
         * @param username el username
         * @return el system user
         */
        @Override
        public SystemUser getSystemUser(String username) {
            return systemUsers.get(username);
        }

        /**
         * Remove system user system user.
         *
         * @param username el username
         * @return el system user
         */
        @Override
        public SystemUser removeSystemUser(String username) {
            return systemUsers.remove(username);
        }

        /**
         * Load system users.
         */
        @Override
        public void loadSystemUsers() {
            SystemUser[] loadeds = loadObject("users.data", SystemUser[].class);

            if (loadeds != null)
                for (SystemUser systemUser : loadeds)
                    systemUsers.put(systemUser.getUsername(), systemUser);
        }

        /**
         * Save system users.
         */
        @Override
        public void saveSystemUsers() {
            saveObject("users.data", systemUsers.values());
        }

        /**
         * Iterator iterator.
         *
         * @return el iterator
         */
        @Override
        public Iterator<SystemUser> iterator() {
            return systemUsers.values().iterator();
        }

    }

}
