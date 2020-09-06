package net.absolutioncraft.lobby.listener;

import com.google.inject.Inject;

import net.absolutioncraft.commons.bukkit.rank.event.UserRankChangeEvent;
import net.absolutioncraft.lobby.board.helper.LobbyBoardHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class UserRankChangeListener implements Listener {
    @Inject private LobbyBoardHelper lobbyBoardHelper;

    @EventHandler
    public void onRank(UserRankChangeEvent event) {
        Player player = Bukkit.getPlayerExact(event.getChanged().getDisplayName());
        if (player == null) return;
        lobbyBoardHelper.requestTag(player, event.getChanged());
    }
}
