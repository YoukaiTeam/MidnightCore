package net.absolutioncraft.commons.bukkit.rank;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.gson.Gson;
import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.storage.checker.DataChecker;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.online.provider.OnlineProvider;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.redis.RedisMessager;
import net.absolutioncraft.api.shared.redis.channel.Channel;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.commons.bukkit.CommonsBukkit;
import net.absolutioncraft.commons.bukkit.event.SynchronousEventCallTask;
import net.absolutioncraft.commons.bukkit.rank.changeable.ChangedRank;
import net.absolutioncraft.commons.bukkit.rank.event.UserRankChangeEvent;
import net.absolutioncraft.commons.bukkit.rank.response.RankChangeResponse;
import net.absolutioncraft.commons.bukkit.rank.sender.RankChangeSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class RankManager implements RankChangeSender {
    @Inject private CommonsBukkit commonsBukkit;
    @Inject private Gson gson;
    @Inject private OnlineProvider onlineProvider;
    @Inject private UserDataProvider userDataProvider;
    @Inject private DataChecker userDataChecker;

    private ListeningExecutorService executorService;
    private Channel<ChangedRank> changedRankChannel;

    @Inject
    RankManager(ListeningExecutorService executorService, RedisMessager redisMessager) {
        this.executorService = executorService;
        this.changedRankChannel = redisMessager.getChannel("rank_change");
        //changedRankChannel.registerListener(rankListener);
    }

    @Override
    public ListenableFuture<RankChangeResponse> sendRankChange(IUser changer, IUser changed, UserRank rank, int expiration) {
        // If the user isn't connected to the network, just update the data
        // and success. This is necessary because if destination user isn't
        // connected, this method will send the request through redis, and
        // RedisListener will cancel the request if the player is not connected
        // on the destination server. So, if the player is not connected, just
        // change the rank and save it.
        if (!onlineProvider.isUserOnline(changed.getUsername())) {
            // Set rank
            changed.setUserRank(rank.toString());
            changed.setSetRankTime((int)Instant.now().getEpochSecond());
            changed.setRankExpiration(expiration);
            // Data cloud saving
            try {
                userDataProvider.updateUserData(changed);
                new SynchronousEventCallTask(new UserRankChangeEvent(changer, changed, rank)).runTask(commonsBukkit);
            } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException e) {
                e.printStackTrace();
                return Futures.immediateFuture(new RankChangeResponse(RankChangeResponse.Response.INTERNAL_ERROR, null, e));
            }
            return Futures.immediateFuture(new RankChangeResponse(RankChangeResponse.Response.SUCCESS, new RankChange(changer, changed, rank, expiration), null));
        }

        Player changedPlayer = Bukkit.getPlayerExact(changed.getDisplayName());

        // If the destination player is in the same server of the
        // sender, execute immediately.
        if (changedPlayer != null) {
            // Set rank
            changed.setUserRank(rank.toString());
            changed.setSetRankTime((int)Instant.now().getEpochSecond());
            changed.setRankExpiration(expiration);
            // Data cloud saving
            try {
                userDataProvider.updateUserData(changed);
            } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException e) {
                e.printStackTrace();
                return Futures.immediateFuture(new RankChangeResponse(RankChangeResponse.Response.INTERNAL_ERROR, null, e));
            }
            // Finish and call the bukkit event.
            new SynchronousEventCallTask(new UserRankChangeEvent(changer, changed, rank)).runTask(commonsBukkit);
            return Futures.immediateFuture(new RankChangeResponse(RankChangeResponse.Response.SUCCESS, new RankChange(changer, changed, rank, expiration), null));
        }

        // Send rank change through redis messaging.
        return executorService.submit(() -> {
            RankChange rankChange = new RankChange(changer, changed, rank, expiration);
            changedRankChannel.sendMessage(gson.toJson(rankChange, RankChange.class));
            return new RankChangeResponse(RankChangeResponse.Response.SENT_THROUGH_REDIS, rankChange, null);
        });
    }
}
