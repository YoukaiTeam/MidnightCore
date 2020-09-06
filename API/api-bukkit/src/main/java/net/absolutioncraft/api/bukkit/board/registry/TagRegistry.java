package net.absolutioncraft.api.bukkit.board.registry;

import net.absolutioncraft.api.bukkit.board.player.PlayerTag;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface TagRegistry {
    PlayerTag addTag(@NotNull Player player);
    void removeTag(@NotNull Player player);
    PlayerTag getPlayerTag(@NotNull UUID uuid);

    boolean hasTag(@NotNull UUID uuid);
}
