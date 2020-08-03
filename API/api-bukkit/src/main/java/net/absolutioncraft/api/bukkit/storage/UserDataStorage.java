package net.absolutioncraft.api.bukkit.storage;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.gson.Gson;
import com.google.inject.Inject;

import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.redis.RedisClient;
import net.absolutioncraft.api.shared.serialization.model.user.UserDataDeserializer;
import net.absolutioncraft.api.shared.user.GetUserDataRequest;
import net.absolutioncraft.api.shared.user.UpdateUserDataRequest;
import net.absolutioncraft.api.shared.user.model.IUser;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class UserDataStorage implements UserDataProvider {
    // TODO 03/08/2020: Document this.
    @Inject private ListeningExecutorService executorService;
    @Inject private RedisClient redisClient;
    @Inject private Gson gson;

    @Inject private UserDataDeserializer userDataDeserializer;

    @Inject private GetUserDataRequest getUserDataRequest;
    @Inject private UpdateUserDataRequest updateUserDataRequest;

    @Override
    public void cacheUserInstance(IUser userModel) {
        final String username = userModel.getUsername();
        this.redisClient.setString("user:" + username, gson.toJson(userModel));
        this.redisClient.setExpiration("user:" + username, 300);
    }

    @Override
    public ListenableFuture<RestResponse<IUser>> getCachedUserByName(String username) {
        return this.executorService.submit(() -> {
            try {
                return new RestResponse<>(null, RestResponse.Status.SUCCESS, this.getCachedUserByNameSync(username));
            } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException exception) {
                return new RestResponse<>(exception, RestResponse.Status.ERROR, null);
            }
        });
    }

    @Override
    public IUser getCachedUserByNameSync(String username) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException {
        if (this.redisClient.existsKey("user:" + username.toLowerCase())) {
            return this.gson.fromJson(this.redisClient.getString("user:" + username.toLowerCase()), IUser.class);
        } else {
            return getUserByNameSync(username);
        }
    }

    @Override
    public ListenableFuture<RestResponse<IUser>> getUserByName(String username) {
        return this.executorService.submit(() -> {
            try {
                return new RestResponse<>(null, RestResponse.Status.SUCCESS, this.getUserByNameSync(username));
            } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException exception) {
                return new RestResponse<>(exception, RestResponse.Status.ERROR, null);
            }
        });
    }

    @Override
    public IUser getUserByNameSync(String username) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException {
        final IUser user = this.userDataDeserializer.deserialize(this.getUserDataRequest.executeRequest(username.toLowerCase()));
        cacheUserInstance(user);
        return user;
    }

    @Override
    public IUser updateUserData(IUser user) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException {
        this.updateUserDataRequest.executeRequest(user.getUsername().toLowerCase(), user);
        cacheUserInstance(user);
        return user;
    }
}
