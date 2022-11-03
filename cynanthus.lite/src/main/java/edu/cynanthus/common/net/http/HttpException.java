package edu.cynanthus.common.net.http;

import java.io.IOException;

/**
 * El tipo Http exception.
 */
public final class HttpException extends IOException {

    /**
     * El Code.
     */
    private final int code;

    /**
     * Instancia un nuevo Http exception.
     *
     * @param code el code
     */
    public HttpException(int code) {
        this.code = code;
    }

    /**
     * Instancia un nuevo Http exception.
     *
     * @param code    el code
     * @param message el message
     */
    public HttpException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Instancia un nuevo Http exception.
     *
     * @param code    el code
     * @param message el message
     * @param cause   el cause
     */
    public HttpException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Instancia un nuevo Http exception.
     *
     * @param code  el code
     * @param cause el cause
     */
    public HttpException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    /**
     * Permite obtener code.
     *
     * @return el code
     */
    public int getCode() {
        return code;
    }

}
