package net.absolutioncraft.api.bungee;

import com.google.inject.Injector;
import com.google.inject.Module;
import net.absolutioncraft.api.bungee.binder.BinderModule;
import net.absolutioncraft.api.shared.SharedModule;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class BungeeAPI extends Plugin {
    @Override
    public void onEnable() {
        this.setupInjection(new SharedModule());
    }

    private void setupInjection(final Module... modules) {
        final BinderModule binderModule = new BinderModule(this, modules);
        final Injector injector = binderModule.createInjector();

        injector.injectMembers(this);
    }
}
