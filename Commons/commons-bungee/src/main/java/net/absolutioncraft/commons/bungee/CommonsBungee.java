package net.absolutioncraft.commons.bungee;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.absolutioncraft.api.shared.SharedModule;
import net.absolutioncraft.commons.bungee.binder.BinderModule;
import net.absolutioncraft.commons.bungee.listener.UserOnlineListener;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class CommonsBungee extends Plugin {
    @Inject private UserOnlineListener userOnlineListener;

    @Override
    public void onEnable() {
        this.setupInjection(new SharedModule());
        this.setupListeners(userOnlineListener);
    }

    private void setupListeners(final Listener... listeners) {
        for (Listener listener : listeners) this.getProxy().getPluginManager().registerListener(this, listener);
    }

    private void setupInjection(final Module... modules) {
        final BinderModule binderModule = new BinderModule(this, modules);
        final Injector injector = binderModule.createInjector();

        injector.injectMembers(this);
    }
}
