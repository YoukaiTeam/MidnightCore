package net.absolutioncraft.api.bukkit.board.registry;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface ViewerRegistry {
    void addViewer(@NotNull Player player);
    void removeViewer(@NotNull Player player);

    boolean isViewing(@NotNull Player player);
}
