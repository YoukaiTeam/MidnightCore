package net.absolutioncraft.api.bukkit.storage;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

import net.absolutioncraft.api.bukkit.storage.checker.DataChecker;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.serialization.JsonSerializer;
import net.absolutioncraft.api.shared.user.GetUserDataRequest;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class UserDataChecker implements DataChecker {
    @Inject private ListeningExecutorService executorService;
    @Inject private JsonSerializer jsonSerializer;

    @Inject private GetUserDataRequest getUserDataRequest;

    @Override
    public boolean exists(String username) throws InternalServerErrorException, NotFoundException, ServiceUnavailableException {
        String response = this.getUserDataRequest.executeRequest(username, false);
        JsonObject parsedResponse = jsonSerializer.parseObject(response);

        return parsedResponse.get("exists").getAsBoolean();
    }

    @Override
    public ListenableFuture<RestResponse<Boolean>> existsAsync(String username) {
        return executorService.submit(() -> {
            try {
                return new RestResponse<>(null, RestResponse.Status.SUCCESS, this.exists(username));
            } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException exception) {
                return new RestResponse<>(exception, RestResponse.Status.ERROR, null);
            }
        });
    }
}
