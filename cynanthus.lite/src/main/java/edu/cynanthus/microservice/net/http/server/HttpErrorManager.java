package edu.cynanthus.microservice.net.http.server;

import com.sun.net.httpserver.HttpHandler;
import edu.cynanthus.bean.BeanValidation;
import edu.cynanthus.bean.ErrorMessage;
import edu.cynanthus.common.json.JsonProvider;
import edu.cynanthus.common.net.http.HttpException;
import edu.cynanthus.common.net.http.HttpStatusCode;

import javax.validation.ConstraintViolationException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * El tipo Http error manager.
 */
public class HttpErrorManager {

    private static final String DEFAULT_MESSAGE = "Error interno del servidor";

    /**
     * El Context logger.
     */
    private final Logger contextLogger;

    private final Map<String, ? extends Map<?, ?>> messages;

    /**
     * Instancia un nuevo Http error manager.
     *
     * @param contextLogger el context logger
     */
    public HttpErrorManager(Logger contextLogger, Map<String, ? extends Map<?, ?>> messages) {
        this.contextLogger = contextLogger;
        this.messages = messages;
    }

    /**
     * Wrap handler http handler.
     *
     * @param httpHandler el http handler
     * @return el http handler
     */
    public HttpHandler wrapHandler(HttpHandler httpHandler) {
        Objects.requireNonNull(httpHandler);
        return exchange -> {
            try {
                httpHandler.handle(exchange);
            } catch (Exception ex) {
                ex.printStackTrace();

                String language = HttpExchangeUtil.getHeader(
                    exchange,
                    "Accept-Language",
                    "default"
                );

                Map<?, ?> langMessages = messages.get(language.toLowerCase());
                if (langMessages == null) messages.get("default");

                int code;
                List<String> causes = new LinkedList<>();

                if (ex instanceof HttpException)
                    code = ((HttpException) ex).getCode();
                else if (ex instanceof ConstraintViolationException) {
                    code = HttpStatusCode.UNPROCESSABLE_ENTITY;

                    BeanValidation.basicInterpolation(
                        ((ConstraintViolationException) ex).getConstraintViolations(),
                        langMessages,
                        causes
                    );

                } else {
                    code = HttpStatusCode.INTERNAL_SERVER_ERROR;
                    contextLogger.log(Level.SEVERE, DEFAULT_MESSAGE + ":" + ex.getMessage(), ex);
                }

                String message = DEFAULT_MESSAGE;

                if (langMessages != null) {
                    Object obj = langMessages.get("http.status." + code);
                    if (obj != null) message = obj.toString();
                }

                try (InputStream bodyStream = JsonProvider.toJsonInputStream(
                    new ErrorMessage<>(code, message, causes)
                )) {
                    HttpExchangeUtil.sendResponse(exchange, code, bodyStream);
                }
            }
        };
    }

}
