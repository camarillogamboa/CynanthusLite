package edu.cynanthus.microservice.nanoservice;

import edu.cynanthus.microservice.Context;
import edu.cynanthus.microservice.property.MetaProperty;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * El tipo Context nano service.
 */
public abstract class ContextNanoService extends BasicNanoService {

    /**
     * El Context.
     */
    protected final Context context;
    /**
     * El Logger.
     */
    protected final Logger logger;

    /**
     * Instancia un nuevo Context nano service.
     *
     * @param id      el id
     * @param context el context
     */
    public ContextNanoService(String id, Context context) {
        super(id);
        this.context = Objects.requireNonNull(context);
        this.logger = context.getLogger(getClass().getName());
    }

    /**
     * Permite obtener property.
     *
     * @param name el name
     * @return el property
     */
    protected final MetaProperty getProperty(String name) {
        return context.getMetaProperty(getId() + "." + name);
    }

    /**
     * Put property.
     *
     * @param name         el name
     * @param metaProperty el meta property
     */
    protected final void putProperty(String name, MetaProperty metaProperty) {
        context.putMetaProperty(getId() + "." + name, metaProperty);
    }

}
