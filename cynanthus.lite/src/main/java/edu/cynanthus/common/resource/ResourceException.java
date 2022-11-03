package edu.cynanthus.common.resource;

import java.util.Objects;

/**
 * El tipo Resource exception.
 */
public class ResourceException extends Exception {

    /**
     * El Resource action.
     */
    private final ResourceAction resourceAction;

    /**
     * Instancia un nuevo Resource exception.
     *
     * @param message            el message
     * @param cause              el cause
     * @param enableSuppression  el enable suppression
     * @param writableStackTrace el writable stack trace
     * @param resourceAction     el resource action
     */
    public ResourceException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace,
        ResourceAction resourceAction
    ) {
        super(Objects.requireNonNull(message), cause, enableSuppression, writableStackTrace);
        this.resourceAction = Objects.requireNonNull(resourceAction);
    }

    /**
     * Instancia un nuevo Resource exception.
     *
     * @param message        el message
     * @param cause          el cause
     * @param resourceAction el resource action
     */
    public ResourceException(String message, Throwable cause, ResourceAction resourceAction) {
        super(message, cause);
        this.resourceAction = Objects.requireNonNull(resourceAction);
    }

    /**
     * Instancia un nuevo Resource exception.
     *
     * @param message        el message
     * @param resourceAction el resource action
     */
    public ResourceException(String message, ResourceAction resourceAction) {
        super(message);
        this.resourceAction = Objects.requireNonNull(resourceAction);
    }

    /**
     * Instancia un nuevo Resource exception.
     *
     * @param cause          el cause
     * @param resourceAction el resource action
     */
    public ResourceException(Throwable cause, ResourceAction resourceAction) {
        super(cause);
        this.resourceAction = Objects.requireNonNull(resourceAction);
    }

    /**
     * Instancia un nuevo Resource exception.
     *
     * @param resourceAction el resource action
     */
    public ResourceException(ResourceAction resourceAction) {
        super();
        this.resourceAction = Objects.requireNonNull(resourceAction);
    }

    /**
     * Permite obtener resource action.
     *
     * @return el resource action
     */
    public ResourceAction getResourceAction() {
        return resourceAction;
    }

}
