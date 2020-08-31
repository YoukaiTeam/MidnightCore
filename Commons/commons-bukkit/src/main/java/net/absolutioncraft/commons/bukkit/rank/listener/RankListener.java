package net.absolutioncraft.commons.bukkit.rank.listener;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;

import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.concurrent.CallbackHelper;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.redis.event.RedisMessageEvent;
import net.absolutioncraft.api.shared.serialization.JsonSerializer;
import net.absolutioncraft.api.shared.serialization.model.user.UserDataDeserializer;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.commons.bukkit.CommonsBukkit;
import net.absolutioncraft.commons.bukkit.event.SynchronousEventCallTask;
import net.absolutioncraft.commons.bukkit.rank.RankChange;
import net.absolutioncraft.commons.bukkit.rank.event.UserRankChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.time.Instant;
import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class RankListener implements Listener {
    @Inject private CommonsBukkit commonsBukkit;
    @Inject private UserDataProvider userDataProvider;
    @Inject private UserDataDeserializer userDataDeserializer;
    @Inject private JsonSerializer jsonSerializer;
    @Inject private Gson gson;

    //TODO: Make the redis-tolerant player messaging for data updating response info.
    @EventHandler
    public void receiveMessage(RedisMessageEvent event) {
        String changedRankJson = event.getMessage();
        JsonObject parsedResponse = jsonSerializer.parseObject(changedRankJson);

        IUser changer = userDataDeserializer.deserialize(parsedResponse.get("changerUser").toString());
        IUser changed = userDataDeserializer.deserialize(parsedResponse.get("changedUser").toString());
        UserRank rank = UserRank.valueOf(parsedResponse.get("rank").getAsString());
        int expiration = parsedResponse.get("expiration").getAsInt();

        RankChange changedRank = new RankChange(changer, changed, rank, expiration);

        CallbackHelper.add(userDataProvider.getCachedUserByName(changed.getUsername(), false), response -> {
            if (response.getStatus() == RestResponse.Status.SUCCESS) {
                response.getResponse().setUserRank(changedRank.rank().toString());
                response.getResponse().setSetRankTime((int) Instant.now().getEpochSecond());
                response.getResponse().setRankExpiration(changedRank.expiration());
                try {
                    userDataProvider.updateUserData(response.getResponse());
                } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException e) {
                    e.printStackTrace();
                }
                new SynchronousEventCallTask(new UserRankChangeEvent(changer, response.getResponse(), changedRank.rank())).runTask(commonsBukkit);
            } else {
                Bukkit.getLogger().log(Level.SEVERE, "[Commons] Something went wrong updating User rank via redis channel. #" + response.getThrowedException().statusCode());
            }
        });
    }
}
