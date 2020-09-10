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
public final class PunishmentUpdateRequest extends HttpRequest {
    private HashMap<String, String> headers = new HashMap<>();
    private String punishmentId;
    private String body;

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HttpRequestType getType() {
        return HttpRequestType.PUT;
    }

    public String getURL() {
        return "punishment/update/" +  this.punishmentId;
    }

    @Override
    public String getJSONParams() {
        return body;
    }

    public String executeRequest(String punishment, String punishmentId, String token) throws InternalServerErrorException, NotFoundException, ServiceUnavailableException {
        this.body = punishment;
        this.punishmentId = punishmentId;
        return getResponse();
    }
}
