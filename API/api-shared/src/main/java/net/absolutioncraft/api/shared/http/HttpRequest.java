package net.absolutioncraft.api.shared.http;

import com.google.inject.Inject;

import net.absolutioncraft.api.shared.http.builder.RequestBuilder;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.serialization.JsonSerializer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public abstract class HttpRequest implements IHttpRequest {
    @Inject private RequestBuilder requestBuilder;
    @Inject private JsonSerializer jsonSerializer;

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public Map<String, String> getHeaders() {
        return new HashMap<>();
    }

    public Map<String, String> getQueryStrings() {
        return new HashMap<>();
    }

    public String getJSONParams() {
        return "";
    }

    protected String getResponse() throws ServiceUnavailableException, InternalServerErrorException, NotFoundException {
        String response = "";
        URI url = this.requestBuilder.getURI(getURL(), getQueryStrings());
        ResponseHandler<String> handler = handleResponse();
        HttpResponse http_response = null;
        try {
            StringEntity entity = new StringEntity(getJSONParams(), ContentType.APPLICATION_JSON);
            switch (getType()) {
                case POST:
                    HttpPost post = new HttpPost(url);
                    getHeaders().forEach(post::setHeader);
                    post.setEntity(entity);
                    http_response = httpClient.execute(post);
                    response = handler.handleResponse(http_response);
                    break;
                case DELETE:
                    HttpDelete delete = new HttpDelete(url);
                    getHeaders().forEach(delete::setHeader);
                    http_response = httpClient.execute(delete);
                    response = handler.handleResponse(http_response);
                    break;
                case PUT:
                    HttpPut put = new HttpPut(url);
                    getHeaders().forEach(put::setHeader);
                    put.setEntity(entity);
                    http_response = httpClient.execute(put);
                    response = handler.handleResponse(http_response);
                    break;
                case GET:
                    HttpGet get = new HttpGet(url);
                    getHeaders().forEach(get::setHeader);
                    http_response = httpClient.execute(get);
                    response = handler.handleResponse(http_response);
                    break;
            }

            http_response.getStatusLine().getReasonPhrase();
        } catch (final IOException exception) {
            exception.printStackTrace();
        }

        if (http_response != null) {
            switch (http_response.getStatusLine().getStatusCode()) {
                default: break;
                case 500: throw new InternalServerErrorException(jsonSerializer.errorContext(response));
                case 404: throw new NotFoundException(jsonSerializer.errorContext(response));
                case 503: throw new ServiceUnavailableException();
            }
        } else {
            throw new ServiceUnavailableException();
        }

        return response;
    }

    private ResponseHandler<String> handleResponse() {
        return response -> {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        };
    }
}
