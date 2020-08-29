package net.absolutioncraft.api.bukkit;

import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.binder.BinderModule;

import com.google.inject.Injector;
import com.google.inject.Module;

import net.absolutioncraft.api.bukkit.command.CommandModule;
import net.absolutioncraft.api.bukkit.command.listener.CommandListener;
import net.absolutioncraft.api.bukkit.menu.listener.MenuInventoryListener;
import net.absolutioncraft.api.bukkit.rank.RankExpirationModule;
import net.absolutioncraft.api.bukkit.storage.UserStorageModule;
import net.absolutioncraft.api.shared.SharedModule;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for Network BukkitAPI.
 * This plugin objective is keeping bukkit instances
 * organized using Redis for a clean network
 * memory usage.
 *
 * @author MelonDev
 * @since 0.0.1
 */
public final class BukkitAPI extends JavaPlugin {
    @Inject private MenuInventoryListener menuInventoryListener;
    @Inject private CommandListener commandListener;

    @Override
    public void onEnable() {
        this.setupInjection(new SharedModule(),
                            new UserStorageModule(),
                            new CommandModule(),
                            new RankExpirationModule());

        this.saveDefaultConfig();

        this.setupListeners(menuInventoryListener, commandListener);
    }

    private void setupListeners(final Listener... listeners) {
        for (Listener listener : listeners) this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private void setupInjection(final Module... modules) {
        final BinderModule binderModule = new BinderModule(this, modules);
        final Injector injector = binderModule.createInjector();

        injector.injectMembers(this);
    }
}
