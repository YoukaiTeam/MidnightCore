package net.absolutioncraft.api.bukkit.board.tag;

import net.absolutioncraft.api.bukkit.board.exception.PlayerBoardException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface ITagHandler {
    void setPrefix(@NotNull Player player, @NotNull String string) throws PlayerBoardException;
    void setSuffix(@NotNull Player player, @NotNull String string) throws PlayerBoardException;
    void clearTag(@NotNull Player player) throws PlayerBoardException;
}

