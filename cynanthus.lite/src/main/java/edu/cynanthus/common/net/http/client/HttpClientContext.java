package edu.cynanthus.common.net.http.client;

import edu.cynanthus.common.net.http.RequestMethod;
import edu.cynanthus.common.net.http.packet.Request;
import edu.cynanthus.common.net.http.packet.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.function.Function;

/**
 * La interface Http client context.
 */
public interface HttpClientContext extends HttpHostAddress {

    /**
     * Permite obtener http client.
     *
     * @return el http client
     */
    HttpClient getHttpClient();

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
    <T> Response<T> doRequest(
        Request<?> request,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException;

    /**
     * Get response.
     *
     * @param <T>         el parámetro de tipo
     * @param headers     el headers
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> GET(
        Map<String, String[]> headers,
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.GET, headers, data), bodyHandler);
    }

    /**
     * Get response.
     *
     * @param <T>         el parámetro de tipo
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> GET(
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.GET, data), bodyHandler);
    }

    /**
     * Post response.
     *
     * @param <T>         el parámetro de tipo
     * @param headers     el headers
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> POST(
        Map<String, String[]> headers,
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.POST, headers, data), bodyHandler);
    }

    /**
     * Post response.
     *
     * @param <T>         el parámetro de tipo
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> POST(
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.POST, data), bodyHandler);
    }

    /**
     * Put response.
     *
     * @param <T>         el parámetro de tipo
     * @param headers     el headers
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> PUT(
        Map<String, String[]> headers,
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.PUT, headers, data), bodyHandler);
    }

    /**
     * Put response.
     *
     * @param <T>         el parámetro de tipo
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> PUT(
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.PUT, data), bodyHandler);
    }

    /**
     * Delete response.
     *
     * @param <T>         el parámetro de tipo
     * @param headers     el headers
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> DELETE(
        Map<String, String[]> headers,
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.DELETE, headers, data), bodyHandler);
    }

    /**
     * Delete response.
     *
     * @param <T>         el parámetro de tipo
     * @param data        el data
     * @param bodyHandler el body handler
     * @return el response
     * @throws IOException          el io exception
     * @throws InterruptedException el interrupted exception
     */
    default <T> Response<T> DELETE(
        Object data,
        HttpResponse.BodyHandler<T> bodyHandler
    ) throws IOException, InterruptedException {
        return doRequest(Request.create(RequestMethod.DELETE, data), bodyHandler);
    }

    /**
     * Create http client context.
     *
     * @param serverName       el server name
     * @param serverPort       el server port
     * @param serverContext    el server context
     * @param fieldAliasFinder el field alias finder
     * @return el http client context
     */
    static HttpClientContext create(
        String serverName,
        int serverPort,
        String serverContext,
        Function<Field, String> fieldAliasFinder
    ) {
        return new HttpClientContextImpl(serverName, serverPort, serverContext, fieldAliasFinder);
    }

}
