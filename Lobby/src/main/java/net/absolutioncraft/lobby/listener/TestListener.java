package net.absolutioncraft.lobby.listener;

import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.board.PlayerBoard;
import net.absolutioncraft.api.bukkit.board.exception.PlayerBoardException;
import net.absolutioncraft.api.bukkit.board.scoreboard.ScoreboardHandler;
import net.absolutioncraft.api.bukkit.board.storage.ScoreboardViewerStorage;
import net.absolutioncraft.api.bukkit.board.tag.TagHandler;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.concurrent.CallbackHelper;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.online.provider.OnlineProvider;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.serialization.StringSerializer;
import net.absolutioncraft.commons.bukkit.rank.event.UserRankChangeEvent;
import net.absolutioncraft.commons.bukkit.user.event.UserLoadEvent;
import net.absolutioncraft.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author MelonDev
 * @since 0.1.0
 */
public final class TestListener implements Listener {
    @Inject private Lobby lobby;
    @Inject private OnlineProvider onlineProvider;
    @Inject private UserDataProvider userDataProvider;

    @Inject private ScoreboardViewerStorage scoreboardViewerStorage;
    @Inject private ScoreboardHandler scoreboardHandler;
    @Inject private TagHandler tagHandler;

    @EventHandler(priority = EventPriority.LOWEST)
    public void response(UserLoadEvent event) {
        scoreboardViewerStorage.addViewer(event.getPlayer());
        try {
            PlayerBoard playerBoard = scoreboardViewerStorage.getPlayerBoard(event.getPlayer());
            // Connected players updater
            TestUpdater testUpdater = new TestUpdater(playerBoard, 2, "Jug. conectados: &b" + onlineProvider.getOnlinePlayers());
            Bukkit.getScheduler().runTaskTimerAsynchronously(lobby, () -> testUpdater.update(2, "Jug. conectados: &b" + onlineProvider.getOnlinePlayers()), 0L, 20L);

            playerBoard.setSlot(1, "test");
            playerBoard.setTitle("ABSOLUTION");
            playerBoard.enableGlow(lobby);

            if (event.getUser().getUserRank() == UserRank.VIP) {
                tagHandler.setPrefix(event.getPlayer(), StringSerializer.getSupporterHearts(event.getUser()));
            } else {
                tagHandler.setPrefix(event.getPlayer(), event.getUser().getUserRank().getPrefix());
            }
        } catch (PlayerBoardException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        tagHandler.clearTag(event.getPlayer());
        scoreboardViewerStorage.removeViewer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        CallbackHelper.add(userDataProvider.getCachedUserByName(player.getName(), false), response -> {
            if (response.getStatus() == RestResponse.Status.SUCCESS) {
                if (response.getResponse().getUserRank() == UserRank.VIP) {
                    Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', StringSerializer.getSupporterHearts(response.getResponse()) + response.getResponse().getDisplayName() + "&f: " + event.getMessage())));
                } else {
                    Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', response.getResponse().getUserRank().getPrefix() + response.getResponse().getDisplayName() + "&f: " + event.getMessage())));
                }
            } else {
                player.sendMessage("Algo salió mal xd");
            }
        });
    }

    @EventHandler
    public void onRank(UserRankChangeEvent event) {
        Player player = Bukkit.getPlayerExact(event.getChanged().getDisplayName());
        if (player == null) return;
        try {
            if (event.getDestinationRank() == UserRank.VIP) {
                tagHandler.setPrefix(player, StringSerializer.getSupporterHearts(event.getChanged()));
            } else
            tagHandler.setPrefix(player, event.getChanged().getUserRank().getPrefix());
        } catch (PlayerBoardException e) {
            e.printStackTrace();
        }
    }
}
