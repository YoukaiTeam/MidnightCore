package net.absolutioncraft.lobby.board;

import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.board.exception.PlayerBoardException;
import net.absolutioncraft.api.bukkit.board.player.PlayerBoard;
import net.absolutioncraft.api.bukkit.board.scoreboard.ScoreboardHandler;
import net.absolutioncraft.api.bukkit.board.storage.ScoreboardViewerStorage;
import net.absolutioncraft.api.bukkit.board.tag.TagHandler;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.online.provider.OnlineProvider;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.serialization.StringSerializer;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.lobby.Lobby;
import net.absolutioncraft.lobby.board.helper.LobbyBoardHelper;
import net.absolutioncraft.lobby.board.updater.BoardUpdater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class LobbyBoardHandler implements LobbyBoardHelper {
    @Inject private Lobby lobby;
    @Inject private OnlineProvider onlineProvider;
    @Inject private UserDataProvider userDataProvider;

    @Inject private ScoreboardViewerStorage scoreboardViewerStorage;
    @Inject private ScoreboardHandler scoreboardHandler;
    @Inject private TagHandler tagHandler;

    @Override
    public void requestBoard(Player player, IUser user) {
        scoreboardViewerStorage.addViewer(player);

        PlayerBoard playerBoard = scoreboardViewerStorage.getPlayerBoard(player);
        // Connected players updater
        BoardUpdater boardUpdater = new BoardUpdater(playerBoard, 0, "");
        Bukkit.getScheduler().runTaskTimerAsynchronously(lobby, () -> boardUpdater.update(2, "Jug. conectados: &b" + onlineProvider.getOnlinePlayers()), 0L, 20L);

        playerBoard.setSlot(1, "test");
        playerBoard.setTitle("ABSOLUTION");
        playerBoard.enableGlow(lobby);
    }

    @Override
    public void requestTag(Player player, IUser user) {
        try {
            if (user.getUserRank() == UserRank.VIP) {
                tagHandler.setPrefix(player, StringSerializer.getSupporterHearts(user));
            } else {
                tagHandler.setPrefix(player, user.getUserRank().getPrefix());
            }
        } catch (PlayerBoardException e) {
            lobby.getLogger().log(Level.SEVERE, "[Lobby] Could not set player tag prefix for {0} ({1}).", new Object[]{ player.getName(), e.getCause()});
        }
    }

    @Override
    public void clearPlayer(Player player) {
        tagHandler.clearTag(player);
        scoreboardViewerStorage.removeViewer(player);
    }
}
