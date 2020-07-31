package net.absolutioncraft.api.shared.http;

import net.absolutioncraft.api.shared.http.type.HttpRequestType;

import java.util.Map;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public interface IHttpRequest {
    HttpRequestType getType();

    String getURL();

    Map<String, String> getHeaders();

    Map<String, String> getQueryStrings();

    String getJSONParams();
}
