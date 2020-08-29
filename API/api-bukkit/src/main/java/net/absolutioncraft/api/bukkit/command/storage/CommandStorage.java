package net.absolutioncraft.api.bukkit.command.storage;

import net.absolutioncraft.api.bukkit.command.command.AbstractMidnightCommand;
import net.absolutioncraft.api.bukkit.command.registry.CommandRegistry;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class CommandStorage implements CommandRegistry {
    private static Map<String, AbstractMidnightCommand> commandList = new ConcurrentHashMap<>();

    @Override
    public void registerCommand(AbstractMidnightCommand command) {
        final String commandName = command.getCommandName();
        commandList.put(commandName, command);

        // Aliases listing
        command.getAliasList().forEach(alias -> commandList.put(alias, command));
        Bukkit.getLogger().log(Level.INFO, "[API] Midnight Command registered: {0}", new Object[] { commandName });
    }

    @Override
    public void removeCommand(AbstractMidnightCommand command) {
        final String commandName = command.getCommandName();
        commandList.remove(commandName);

        // Aliases listing
        command.getAliasList().forEach(alias -> commandList.remove(alias));
        Bukkit.getLogger().log(Level.INFO, "[API] Midnight Command removed: {0}", new Object[] { commandName });
    }

    @Override
    public Optional<AbstractMidnightCommand> getCommandUsingAlias(String commandAlias) {
        return Optional.ofNullable(commandList.get(commandAlias));
    }
}
