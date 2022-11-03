package edu.cynanthus.common.security;

import com.google.gson.JsonDeserializer;

import java.util.Arrays;
import java.util.List;

/**
 * La interface System user.
 */
public interface SystemUser {

    /**
     * Permite obtener username.
     *
     * @return el username
     */
    String getUsername();

    /**
     * Permite obtener password.
     *
     * @return el password
     */
    String getPassword();

    /**
     * Permite obtener roles.
     *
     * @return el roles
     */
    List<SystemRole> getRoles();

    /**
     * Get credentials string [ ].
     *
     * @return el string [ ]
     */
    default String[] getCredentials() {
        return new String[]{getUsername(), getPassword()};
    }

    /**
     * Has any roles boolean.
     *
     * @param systemRoles el system roles
     * @return el boolean
     */
    default boolean hasAnyRoles(SystemRole... systemRoles) {
        for (SystemRole rolName : systemRoles)
            for (SystemRole rol : getRoles())
                if (rolName.equals(rol)) return true;
        return false;
    }

    /**
     * Create system user system user.
     *
     * @param username el username
     * @param password el password
     * @param roles    el roles
     * @return el system user
     */
    static SystemUser createSystemUser(String username, String password, SystemRole... roles) {
        return new SystemUserImpl(username, password, Arrays.asList(roles));
    }

    /**
     * Create json deserializer json deserializer.
     *
     * @return el json deserializer
     */
    static JsonDeserializer<SystemUser> createJsonDeserializer() {
        return new SystemUserImpl.SystemUserDeserializer();
    }

}
