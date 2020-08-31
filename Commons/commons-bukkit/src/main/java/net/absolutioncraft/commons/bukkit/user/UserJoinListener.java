package net.absolutioncraft.commons.bukkit.user;

import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.rank.expirer.RankExpirer;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.concurrent.CallbackHelper;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.commons.bukkit.CommonsBukkit;
import net.absolutioncraft.commons.bukkit.user.event.UserLoadEvent;
import net.absolutioncraft.commons.bukkit.event.SynchronousEventCallTask;
import net.absolutioncraft.commons.bukkit.user.task.SynchronousKickTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 0.1.0
 */
public final class UserJoinListener implements Listener {
    @Inject private CommonsBukkit commonsBukkit;

    @Inject private UserDataProvider userDataProvider;
    @Inject private RankExpirer rankExpirer;

    @EventHandler(priority = EventPriority.LOWEST)
    public void userJoinResponse(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        CallbackHelper.add(userDataProvider.getCachedUserByName(player.getName(), true), response -> {
            if (response.getStatus() == RestResponse.Status.SUCCESS) {
                // Rank expiration checker
                rankExpirer.checkExpiration(response.getResponse());
                callSyncEvent(new UserLoadEvent(response.getResponse(), player));
            } else {
                noDataKick(player, "Error de datos\n#" + response.getThrowedException().statusCode());
                Bukkit.getLogger().log(Level.INFO, "[Commons] Exception occurred while loading data for {0}: {1} (Code: {2})",
                        new Object[]{player.getName(), response.getThrowedException().toString(), response.getThrowedException().statusCode(),
                        });
            }
        });
    }

    private void noDataKick(Player player, String reason) {
        new SynchronousKickTask(player, reason).runTask(commonsBukkit);
    }

    private void callSyncEvent(Event event) {
        new SynchronousEventCallTask(event).runTask(commonsBukkit);
    }
}
