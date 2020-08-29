package net.absolutioncraft.api.bukkit.command.registry;

import net.absolutioncraft.api.bukkit.command.command.AbstractMidnightCommand;

import java.util.Optional;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public interface CommandRegistry {
    void registerCommand(AbstractMidnightCommand command);
    void removeCommand(AbstractMidnightCommand command);
    Optional<AbstractMidnightCommand> getCommandUsingAlias(String commandAlias);
}
