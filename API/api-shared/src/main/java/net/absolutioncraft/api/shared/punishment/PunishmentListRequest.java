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
public final class PunishmentListRequest extends HttpRequest {
    private HashMap<String, String> params = new HashMap<>();

    @Override
    public Map<String, String> getQueryStrings() {
        return this.params;
    }

    public HttpRequestType getType() {
        return HttpRequestType.GET;
    }

    public String getURL() {
        return "punishment/list-model";
    }

    public String executeRequest(String type, String playerUsername, boolean active) throws InternalServerErrorException, NotFoundException, ServiceUnavailableException {
        this.params.put("type", type);
        this.params.put("playerName", playerUsername);
        this.params.put("active", Boolean.toString(active));
        return getResponse();
    }

}
