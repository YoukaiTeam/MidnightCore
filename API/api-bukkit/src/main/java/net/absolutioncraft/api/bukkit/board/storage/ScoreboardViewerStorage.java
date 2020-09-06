package net.absolutioncraft.api.bukkit.board.storage;

import com.google.inject.Inject;

import net.absolutioncraft.api.bukkit.board.player.PlayerBoard;
import net.absolutioncraft.api.bukkit.board.registry.ViewerRegistry;
import net.absolutioncraft.api.bukkit.board.scoreboard.ScoreboardHandler;

import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class ScoreboardViewerStorage implements ViewerRegistry {
    @Inject private ScoreboardHandler scoreboardHandler;

    private static Map<UUID, PlayerBoard> viewers = new HashMap<>();

    @Override
    public void addViewer(@NotNull Player player) {
        final PlayerBoard scoreboard = new PlayerBoard();

        scoreboardHandler.handleBoard(player, scoreboard);

        viewers.put(player.getUniqueId(), scoreboard);
    }

    public PlayerBoard getPlayerBoard(Player player) {
        return viewers.get(player.getUniqueId());
    }

    @Override
    public void removeViewer(@NotNull Player player) {
        final PlayerBoard playerBoard = getPlayerBoard(player);
        scoreboardHandler.removeBoard(playerBoard);

        viewers.remove(player.getUniqueId());
    }


    @Override
    public boolean isViewing(@NotNull Player player) {
        return viewers.containsKey(player.getUniqueId());
    }
}
