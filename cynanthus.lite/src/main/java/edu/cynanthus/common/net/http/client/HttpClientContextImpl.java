package edu.cynanthus.common.net.http.client;

import edu.cynanthus.common.SSV;
import edu.cynanthus.common.URIQuery;
import edu.cynanthus.common.json.JsonProvider;
import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.common.net.http.packet.Request;
import edu.cynanthus.common.net.http.packet.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * El tipo Http client context.
 */
final class HttpClientContextImpl extends HttpHostAddressImpl implements HttpClientContext {

    /**
     * El Http client.
     */
    private final HttpClient httpClient;
    /**
     * El Filed alias finder.
     */
    private final Function<Field, String> filedAliasFinder;

    /**
     * Instancia un nuevo Http client context.
     *
     * @param serverName       el server name
     * @param serverPort       el server port
     * @param serverContext    el server context
     * @param filedAliasFinder el filed alias finder
     */
    HttpClientContextImpl(
        String serverName,
        int serverPort,
        String serverContext,
        Function<Field, String> filedAliasFinder
    ) {
        super(serverName, serverPort, serverContext);
        this.httpClient = HttpClient.newHttpClient();
        this.filedAliasFinder = Objects.requireNonNull(filedAliasFinder);
    }

    /**
     * Permite obtener http client.
     *
     * @return el http client
     */
    @Override
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Do request response.
     *
     * @param <T>         el parámetro de tipo
     * @param request     el request
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    @Override
    public <T> Response<T> doRequest(
        Request<?> request,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        Objects.requireNonNull(bodyHandler);
        HttpRequest.Builder builder = HttpRequest.newBuilder();

        if (RequestMethod.withBody(request.getRequestMethod())) {
            builder = builder.uri(URI.create(buildPath()));
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(
                JsonProvider.toJson(request.getData())
            );
            switch (request.getRequestMethod()) {
                case POST:
                    builder = builder.POST(bodyPublisher);
                    break;
                case PUT:
                    builder = builder.PUT(bodyPublisher);
            }
        } else {
            builder.uri(URI.create(buildPath() + "?" + URIQuery.toQuery(request.getData(), filedAliasFinder)));
            switch (request.getRequestMethod()) {
                case GET:
                    builder = builder.GET();
                    break;
                case DELETE:
                    builder = builder.DELETE();
            }
        }

        Map<String, String[]> headers = request.getHeaders();

        for (Map.Entry<String, String[]> header : headers.entrySet()) {
            builder = builder.header(header.getKey(), SSV.toSSVFormat(header.getValue(), " "));
        }

        builder.timeout(Duration.ofSeconds(10));

        HttpResponse<T> httpResponse = httpClient.send(builder.build(), bodyHandler);
        return Response.create(httpResponse.statusCode(), httpResponse.body());
    }

}
