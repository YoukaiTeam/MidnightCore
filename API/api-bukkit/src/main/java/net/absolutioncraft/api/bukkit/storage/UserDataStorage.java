package net.absolutioncraft.api.bukkit.storage;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.redis.RedisClient;
import net.absolutioncraft.api.shared.serialization.JsonSerializer;
import net.absolutioncraft.api.shared.serialization.model.user.UserDataDeserializer;
import net.absolutioncraft.api.shared.user.GetUserDataRequest;
import net.absolutioncraft.api.shared.user.UpdateUserDataRequest;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.api.shared.user.model.User;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class UserDataStorage implements UserDataProvider {
    // TODO 03/08/2020: Document this.
    @Inject private ListeningExecutorService executorService;
    @Inject private RedisClient redisClient;
    @Inject private Gson gson;
    @Inject private JsonSerializer jsonSerializer;

    @Inject private UserDataDeserializer userDataDeserializer;

    @Inject private GetUserDataRequest getUserDataRequest;
    @Inject private UpdateUserDataRequest updateUserDataRequest;

    @Override
    public void cacheUserInstance(IUser userModel) {
        final String username = userModel.getUsername();
        this.redisClient.setString("user:" + username, gson.toJson(userModel));
        this.redisClient.setExpiration("user:" + username, 500);
    }

    @Override
    public ListenableFuture<RestResponse<IUser>> getCachedUserByName(String username, boolean createData) {
        return this.executorService.submit(() -> {
            try {
                return new RestResponse<>(null, RestResponse.Status.SUCCESS, this.getCachedUserByNameSync(username, createData));
            } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException exception) {
                return new RestResponse<>(exception, RestResponse.Status.ERROR, null);
            }
        });
    }

    @Override
    public IUser getCachedUserByNameSync(String username, boolean createData) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException {
        if (this.redisClient.existsKey("user:" + username.toLowerCase())) {
            return this.gson.fromJson(this.redisClient.getString("user:" + username.toLowerCase()), User.class);
        } else {
            return getUserByNameSync(username, createData);
        }
    }

    @Override
    public ListenableFuture<RestResponse<IUser>> getUserByName(String username, boolean createData) {
        return this.executorService.submit(() -> {
            try {
                return new RestResponse<>(null, RestResponse.Status.SUCCESS, this.getUserByNameSync(username, createData));
            } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException exception) {
                return new RestResponse<>(exception, RestResponse.Status.ERROR, null);
            }
        });
    }

    @Override
    public IUser getUserByNameSync(String username, boolean createData) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException {
        long startTime = System.nanoTime(); // Debug
        String response = this.getUserDataRequest.executeRequest(username, createData);
        JsonObject parsedResponse = jsonSerializer.parseObject(response);
        boolean exists = parsedResponse.get("exists").getAsBoolean();

        if (!createData && !exists) {
            throw new NotFoundException("User not found on database.");
        }

        final IUser user = this.userDataDeserializer.deserialize(parsedResponse.get("message").toString());
        cacheUserInstance(user);
        long endTime = System.nanoTime(); // Debug
        long duration = (endTime - startTime); // Debug
        System.out.println("Time took for #getUserByNameSync: " + duration); // Debug
        return user;
    }

    @Override
    public IUser updateUserData(IUser user) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException {
        this.updateUserDataRequest.executeRequest(user.getUsername(), user);
        cacheUserInstance(user);
        return user;
    }
}
