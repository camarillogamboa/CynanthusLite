package edu.cynanthus.microservice.nanoservice;

import edu.cynanthus.bean.Config;
import edu.cynanthus.bean.FieldAliasFinder;
import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.microservice.Context;
import edu.cynanthus.microservice.net.http.server.engine.RequestHandler;

import java.lang.reflect.Field;
import java.util.function.Function;

/**
 * El tipo Cynanthus server.
 *
 * @param <T> el parámetro de tipo
 */
public class CynanthusServer<T extends Config> extends ConfigurationServer<T> {

    /**
     * Instancia un nuevo Cynanthus server.
     *
     * @param id               el id
     * @param context          el context
     * @param fieldAliasFinder el field alias finder
     */
    public CynanthusServer(String id, Context context, Function<Field, String> fieldAliasFinder) {
        super(id, context, fieldAliasFinder);
    }

    /**
     * Instancia un nuevo Cynanthus server.
     *
     * @param id      el id
     * @param context el context
     */
    public CynanthusServer(String id, Context context) {
        this(id, context, FieldAliasFinder.INSTANCE);
    }

    /**
     * Is available string.
     *
     * @param string el string
     * @return el string
     */
    @RequestHandler(context = "/available", method = RequestMethod.GET)
    public String isAvailable(String string) {
        return getClass().getSimpleName() + " disponible.";
    }

}
