package edu.cynanthus.common.security;

import java.util.Arrays;

/**
 * El tipo Agent user.
 */
public final class AgentUser extends SystemUserImpl {

    /**
     * La constante DEFAUL_AGENT_USER.
     */
    public static final AgentUser DEFAUL_AGENT_USER = new AgentUser(
        "agent",
        "gs8JKXbzy9Ru21YB3+n78g==:Wd1SslFnDiU1GTJ3tRVGoCgb9buyGaH/Y4+sltmPhQw="
    );

    /**
     * Instancia un nuevo Agent user.
     *
     * @param username el username
     * @param password el password
     */
    public AgentUser(String username, String password) {
        super(username, password, Arrays.asList(SystemRole.values()));
    }

}
