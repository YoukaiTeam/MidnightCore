package net.absolutioncraft.lobby;

import com.google.inject.Injector;
import com.google.inject.Module;

import net.absolutioncraft.lobby.binder.BinderModule;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class Lobby extends JavaPlugin {

    @Override
    public void onEnable() {
        this.setupInjection(); // Add guice modules here
        this.setupListeners(); // same
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
