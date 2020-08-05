package net.absolutioncraft.api.shared.user;

import com.google.gson.Gson;
import com.google.inject.Inject;
import net.absolutioncraft.api.shared.http.HttpRequest;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.type.HttpRequestType;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.api.shared.user.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class UpdateUserDataRequest extends HttpRequest {
    @Inject private Gson gson;

    private HashMap<String, String> headers = new HashMap<>();
    private String username;
    private String data;

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HttpRequestType getType() {
        return HttpRequestType.PUT;
    }

    public String getURL() {
        return "user/update-server/" + this.username;
    }

    public String executeRequest(String username, IUser dataSupplier) throws ServiceUnavailableException, InternalServerErrorException, NotFoundException {
        this.username = username;
        this.data = this.gson.toJson(dataSupplier, User.class);
        return getResponse();
    }
}
