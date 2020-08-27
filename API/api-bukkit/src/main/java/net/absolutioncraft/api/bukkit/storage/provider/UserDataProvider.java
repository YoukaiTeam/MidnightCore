package net.absolutioncraft.api.bukkit.storage.provider;

import com.google.common.util.concurrent.ListenableFuture;

import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.user.model.IUser;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public interface UserDataProvider {
    void cacheUserInstance(IUser userModel);

    ListenableFuture<RestResponse<IUser>> getCachedUserByName(String username, boolean createData);
    IUser getCachedUserByNameSync(String username, boolean createData) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException;

    ListenableFuture<RestResponse<IUser>> getUserByName(String username, boolean createData);
    IUser getUserByNameSync(String username, boolean createData) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException;

    IUser updateUserData(IUser user) throws NotFoundException, InternalServerErrorException, ServiceUnavailableException;
}
