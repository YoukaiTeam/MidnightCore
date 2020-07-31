package net.absolutioncraft.api.shared.http.builder;

import com.google.inject.Inject;

import net.absolutioncraft.api.shared.http.config.WebServerConfig;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class RequestBuilder {
    private WebServerConfig config;

    @Inject protected RequestBuilder(final WebServerConfig config) {
        this.config = config;
    }

    public URI getURI(final String url, final Map<String, String> params) {
        try {
            final URIBuilder uri = new URIBuilder()
                    .setScheme("http")
                    .setHost(this.config.getHost())
                    .setPort(this.config.getPort())
                    .setPath(this.config.getSuffix() + "/" + url);
            for (Map.Entry<String, String> entry: params.entrySet()) {
                uri.setParameter(entry.getKey(), entry.getValue());
            }
            return uri.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
