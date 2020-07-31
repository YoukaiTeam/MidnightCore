package net.absolutioncraft.commons;

import com.google.inject.Injector;
import com.google.inject.Module;

import net.absolutioncraft.commons.binder.BinderModule;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public class CommonsBukkit extends JavaPlugin {

    @Override
    public void onEnable() {
        this.setupInjection();
    }

    private void setupInjection(final Module... modules) {
        final BinderModule binderModule = new BinderModule(this, modules);
        final Injector injector = binderModule.createInjector();

        injector.injectMembers(this);
    }
}
