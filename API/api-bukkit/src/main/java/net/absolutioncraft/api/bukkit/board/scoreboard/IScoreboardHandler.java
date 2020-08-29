package net.absolutioncraft.api.bukkit.board.scoreboard;

import net.absolutioncraft.api.bukkit.board.PlayerBoard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface IScoreboardHandler {
    void handleBoard(@NotNull Player player, PlayerBoard playerBoard);
    void removeBoard(PlayerBoard playerBoard);
}
