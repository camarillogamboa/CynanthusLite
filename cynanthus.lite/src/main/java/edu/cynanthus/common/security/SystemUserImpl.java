package edu.cynanthus.common.security;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * El tipo System user.
 */
class SystemUserImpl implements SystemUser {

    /**
     * El Username.
     */
    private final String username;
    /**
     * El Password.
     */
    private final String password;
    /**
     * El Roles.
     */
    private final List<SystemRole> roles;

    /**
     * Instancia un nuevo System user.
     *
     * @param username el username
     * @param password el password
     * @param roles    el roles
     */
    SystemUserImpl(String username, String password, List<SystemRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = Collections.unmodifiableList(roles);
    }

    /**
     * Permite obtener username.
     *
     * @return el username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Permite obtener password.
     *
     * @return el password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Permite obtener roles.
     *
     * @return el roles
     */
    @Override
    public List<SystemRole> getRoles() {
        return roles;
    }

    /**
     * Equals boolean.
     *
     * @param o el o
     * @return el boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SystemUser)) return false;
        SystemUser that = (SystemUser) o;
        return Objects.equals(username, that.getUsername()) &&
            Objects.equals(password, that.getPassword()) &&
            Objects.equals(roles, that.getRoles());
    }

    /**
     * Hash code int.
     *
     * @return el int
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, password, roles);
    }

    /**
     * To string string.
     *
     * @return el string
     */
    @Override
    public String toString() {
        return "[user:" + username + "]";
    }

    /**
     * El tipo System user deserializer.
     */
    static final class SystemUserDeserializer implements JsonDeserializer<SystemUser> {

        /**
         * Deserialize system user.
         *
         * @param json    el json
         * @param typeOfT el type of t
         * @param context el context
         * @return el system user
         * @throws JsonParseException el json parse exception
         */
        @Override
        public SystemUser deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context
        ) throws JsonParseException {
            try {
                JsonObject jsonObject = json.getAsJsonObject();
                JsonPrimitive jsonUsername = jsonObject.getAsJsonPrimitive("username");
                JsonPrimitive jsonPassword = jsonObject.getAsJsonPrimitive("password");
                JsonArray jsonRoles = jsonObject.getAsJsonArray("roles");

                List<SystemRole> roles = new LinkedList<>();

                for (JsonElement jsonElement : jsonRoles) {
                    String roleName = jsonElement.getAsJsonPrimitive().getAsString();
                    roles.add(SystemRole.valueOf(roleName));
                }

                return new SystemUserImpl(jsonUsername.getAsString(), jsonPassword.getAsString(), roles);
            } catch (Exception ex) {
                throw new JsonParseException(ex);
            }
        }

    }

}
