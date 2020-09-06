package net.absolutioncraft.lobby.board.helper;

import net.absolutioncraft.api.shared.user.model.IUser;
import org.bukkit.entity.Player;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface LobbyBoardHelper {
    void requestBoard(Player player, IUser user);
    void requestTag(Player player, IUser user);

    void clearPlayer(Player player);
}
