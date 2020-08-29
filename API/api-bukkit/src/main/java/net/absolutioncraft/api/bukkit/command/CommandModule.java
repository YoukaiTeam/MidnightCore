package net.absolutioncraft.api.bukkit.command;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import net.absolutioncraft.api.bukkit.command.registry.CommandRegistry;
import net.absolutioncraft.api.bukkit.command.storage.CommandStorage;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class CommandModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CommandRegistry.class).to(CommandStorage.class).in(Scopes.SINGLETON);
    }
}
