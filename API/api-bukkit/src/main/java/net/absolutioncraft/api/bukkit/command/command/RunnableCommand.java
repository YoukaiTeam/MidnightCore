package net.absolutioncraft.api.bukkit.command.command;

import org.bukkit.entity.Player;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface RunnableCommand {
    void executeCommand(Player player, String[] args);
}
