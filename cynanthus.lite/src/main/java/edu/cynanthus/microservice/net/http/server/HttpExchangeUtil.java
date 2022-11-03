package edu.cynanthus.microservice.net.http.server;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class HttpExchangeUtil {

    private HttpExchangeUtil() {}

    public static String getHeader(HttpExchange exchange, String headerName, String defaultValue) {
        List<String> header = exchange.getRequestHeaders().get(headerName);
        return header == null || header.isEmpty() ? defaultValue : header.get(0);
    }

    public static String getHeader(HttpExchange exchange, String headerName) {
        return getHeader(exchange, headerName, null);
    }

    public static void putHeadersIn(HttpExchange exchange, Map<String, String[]> map) {
        exchange.getRequestHeaders().forEach((k, v) -> map.put(k, v.toArray(String[]::new)));
    }

    public static Map<String, String[]> getHeaders(HttpExchange exchange) {
        Map<String, String[]> headersMap = new TreeMap<>(String::compareTo);
        putHeadersIn(exchange, headersMap);
        return headersMap;
    }

    public static void sendResponse(HttpExchange exchange, int status, InputStream bodyStream) throws IOException {
        exchange.sendResponseHeaders(status, 0);
        try (OutputStream out = exchange.getResponseBody()) {
            bodyStream.transferTo(out);
        }
    }

}
