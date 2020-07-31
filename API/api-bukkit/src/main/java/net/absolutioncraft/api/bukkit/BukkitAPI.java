package net.absolutioncraft.api.bukkit;

import net.absolutioncraft.api.bukkit.binder.BinderModule;

import com.google.inject.Injector;
import com.google.inject.Module;

import net.absolutioncraft.api.shared.SharedModule;
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
    @Override
    public void onEnable() {
        this.setupInjection(new SharedModule());
        this.saveDefaultConfig();
    }

    private void setupInjection(final Module... modules) {
        final BinderModule binderModule = new BinderModule(this, modules);
        final Injector injector = binderModule.createInjector();

        injector.injectMembers(this);
    }
}
