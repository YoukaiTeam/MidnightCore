package net.absolutioncraft.api.shared.user;

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
public final class GetUserDataRequest extends HttpRequest {
    private HashMap<String, String> headers = new HashMap<>();
    private String username;
    private boolean createData;

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HttpRequestType getType() {
        return HttpRequestType.GET;
    }

    public String getURL() {
        return "user/get/" + this.username;
    }

    @Override
    public String getJSONParams() {
        return String.valueOf(createData);
    }

    public String executeRequest(String username, boolean createData) throws ServiceUnavailableException, InternalServerErrorException, NotFoundException {
        this.username = username;
        this.createData = createData;
        return getResponse();
    }
}
