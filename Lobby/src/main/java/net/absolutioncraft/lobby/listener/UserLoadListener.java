package net.absolutioncraft.lobby.listener;

import com.google.inject.Inject;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.commons.bukkit.user.event.UserLoadEvent;
import net.absolutioncraft.lobby.board.helper.LobbyBoardHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class UserLoadListener implements Listener {
    @Inject private LobbyBoardHelper lobbyBoardHelper;

    @EventHandler(priority = EventPriority.LOWEST)
    public void response(UserLoadEvent event) {
        final Player player = event.getPlayer();
        final IUser user = event.getUser();

        // Scoreboard & tag related.
        lobbyBoardHelper.requestBoard(player, user);
        lobbyBoardHelper.requestTag(player, user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        lobbyBoardHelper.clearPlayer(event.getPlayer());
    }
}
