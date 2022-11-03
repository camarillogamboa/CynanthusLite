package edu.cynanthus.microservice.net.http.server.engine;

import java.lang.annotation.*;

/**
 * La interface Server path.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ServerPath {

    /**
     * Path string.
     *
     * @return el string
     */
    String path() default "";

}
