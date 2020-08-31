package net.absolutioncraft.lobby;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import net.absolutioncraft.api.bukkit.storage.UserStorageModule;
import net.absolutioncraft.api.shared.SharedModule;
import net.absolutioncraft.lobby.binder.BinderModule;

import net.absolutioncraft.lobby.listener.TestListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class Lobby extends JavaPlugin {
    @Inject private TestListener testListener;

    @Override
    public void onEnable() {
        this.setupInjection(new SharedModule(), new UserStorageModule()); // Add guice modules here
        this.setupListeners(testListener); // same
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
