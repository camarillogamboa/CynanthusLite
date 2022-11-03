package edu.cynanthus.microservice.net.http.server.engine;

import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.common.security.SystemRole;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La interface Request handler.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestHandler {

    /**
     * Context string.
     *
     * @return el string
     */
    String context() default "";

    /**
     * Method request method.
     *
     * @return el request method
     */
    RequestMethod method();

    /**
     * Roles system role [ ].
     *
     * @return el system role [ ]
     */
    SystemRole[] roles() default {};

}
