package net.absolutioncraft.api.bukkit.storage.checker;

import com.google.common.util.concurrent.ListenableFuture;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.response.RestResponse;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface DataChecker {
    boolean exists(String username) throws InternalServerErrorException, NotFoundException, ServiceUnavailableException;
    ListenableFuture<RestResponse<Boolean>> existsAsync(String username);
}
