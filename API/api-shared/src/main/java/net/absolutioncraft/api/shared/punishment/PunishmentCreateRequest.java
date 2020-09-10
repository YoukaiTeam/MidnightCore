package net.absolutioncraft.api.shared.punishment;

import net.absolutioncraft.api.shared.http.HttpRequest;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.type.HttpRequestType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class PunishmentCreateRequest extends HttpRequest {
    private HashMap<String, String> headers = new HashMap<>();
    private String body;

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public HttpRequestType getType() {
        return HttpRequestType.POST;
    }

    public String getURL() {
        return "punishment/create";
    }

    @Override
    public String getJSONParams() {
        return body;
    }

    public String executeRequest(String deserializedPunishment) throws InternalServerErrorException, NotFoundException, ServiceUnavailableException {
        this.body = deserializedPunishment;
        return getResponse();
    }
}
