package net.absolutioncraft.api.bukkit.board.creator;

import org.bukkit.plugin.Plugin;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface ScoreboardCreator {
    void setTitle(@NotNull String title);
    void setSlotsFromList(@NotNull List<String> list);

    void setSlot(int slot, @NotNull String text);
    void removeSlot(int slot);

    void enableGlow(Plugin plugin);
    void disableGlow();
}
