package edu.cynanthus.common.net.http;

/**
 * La enumeración Request method.
 */
public enum RequestMethod {

    /**
     * Get request method.
     */
    GET,
    /**
     * Post request method.
     */
    POST,
    /**
     * Put request method.
     */
    PUT,
    /**
     * Delete request method.
     */
    DELETE;

    /**
     * With body boolean.
     *
     * @param requestMethod el request method
     * @return el boolean
     */
    public static boolean withBody(RequestMethod requestMethod) {
        switch (requestMethod) {
            case GET:
            case DELETE:
                return false;
            case POST:
            case PUT:
                return true;
        }
        return false;
    }

}
