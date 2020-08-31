package net.absolutioncraft.api.bungee.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.absolutioncraft.api.bungee.BungeeAPI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class BinderModule extends AbstractModule {
    private BungeeAPI bungeeAPI;
    private List<Module> moduleList;

    public BinderModule(@NotNull final BungeeAPI bungeeAPI, final Module... modules) {
        moduleList = new ArrayList<>();
        moduleList.addAll(Arrays.asList(modules));

        this.bungeeAPI = bungeeAPI;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        moduleList.forEach(this::install);
        bind(BungeeAPI.class).toInstance(this.bungeeAPI);
    }
}
