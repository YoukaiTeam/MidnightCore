package net.absolutioncraft.commons.bukkit;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

import net.absolutioncraft.api.bukkit.command.CommandModule;
import net.absolutioncraft.api.bukkit.command.registry.CommandRegistry;
import net.absolutioncraft.api.bukkit.rank.RankExpirationModule;
import net.absolutioncraft.api.bukkit.storage.UserStorageModule;
import net.absolutioncraft.api.shared.SharedModule;
import net.absolutioncraft.commons.bukkit.binder.BinderModule;
import net.absolutioncraft.commons.bukkit.listener.UserRankListener;
import net.absolutioncraft.commons.bukkit.rank.listener.RankListener;
import net.absolutioncraft.commons.bukkit.rank.RankModule;
import net.absolutioncraft.commons.bukkit.rank.command.SetRankCommand;
import net.absolutioncraft.commons.bukkit.user.UserJoinListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class CommonsBukkit extends JavaPlugin {
    @Inject private UserJoinListener userJoinListener;
    @Inject private UserRankListener userRankListener;
    @Inject private RankListener rankListener;

    @Inject private CommandRegistry commandRegistry;
    @Inject private SetRankCommand setRankCommand;

    @Override
    public void onEnable() {
        this.setupInjection(new SharedModule(),
                            new UserStorageModule(),
                            new CommandModule(),
                            new RankExpirationModule(),
                            new RankModule()
                            );

        commandRegistry.registerCommand(setRankCommand);
        this.setupListeners(rankListener, userJoinListener, userRankListener);
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
