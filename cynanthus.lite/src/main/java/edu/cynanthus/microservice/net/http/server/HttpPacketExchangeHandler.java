package edu.cynanthus.microservice.net.http.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.cynanthus.common.URIQuery;
import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.common.net.http.packet.Request;
import edu.cynanthus.common.net.http.packet.Response;
import edu.cynanthus.common.resource.Exchanger;
import edu.cynanthus.common.resource.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

/**
 * El tipo Http packet exchange handler.
 */
public final class HttpPacketExchangeHandler implements HttpHandler {

    /**
     * El Exchanger.
     */
    private final Exchanger<Request<InputStream>, Response<InputStream>> exchanger;

    /**
     * Instancia un nuevo Http packet exchange handler.
     *
     * @param exchanger el exchanger
     */
    public HttpPacketExchangeHandler(Exchanger<Request<InputStream>, Response<InputStream>> exchanger) {
        this.exchanger = Objects.requireNonNull(exchanger);
    }

    /**
     * Handle.
     *
     * @param exchange el exchange
     * @throws IOException el io exception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Map<String, String[]> headers = HttpExchangeUtil.getHeaders(exchange);

        RequestMethod requestMethod = RequestMethod.valueOf(exchange.getRequestMethod());

        InputStream data;
        if (RequestMethod.withBody(requestMethod)) data = exchange.getRequestBody();
        else data = StreamUtil.asInputStream(URIQuery.queryToJson(exchange.getRequestURI().getRawQuery()));

        Response<InputStream> response = exchanger.exchange(
            Request.create(requestMethod, headers, exchange.getRemoteAddress(), data)
        );

        try (InputStream bodyStream = response.getData()) {
            HttpExchangeUtil.sendResponse(exchange, response.getResponseCode(), bodyStream);
        }
    }

}
