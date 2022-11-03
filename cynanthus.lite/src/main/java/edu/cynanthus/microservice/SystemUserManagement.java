package edu.cynanthus.microservice;

import edu.cynanthus.common.security.AgentUser;
import edu.cynanthus.common.security.SystemUser;

/**
 * La interface System user management.
 */
public interface SystemUserManagement extends Iterable<SystemUser> {

    /**
     * Permite obtener main user.
     *
     * @return el main user
     */
    AgentUser getMainUser();

    /**
     * Put system user.
     *
     * @param systemUser el system user
     */
    void putSystemUser(SystemUser systemUser);

    /**
     * Permite obtener system user.
     *
     * @param username el username
     * @return el system user
     */
    SystemUser getSystemUser(String username);

    /**
     * Remove system user system user.
     *
     * @param username el username
     * @return el system user
     */
    SystemUser removeSystemUser(String username);

    /**
     * Load system users.
     */
    void loadSystemUsers();

    /**
     * Save system users.
     */
    void saveSystemUsers();

}
