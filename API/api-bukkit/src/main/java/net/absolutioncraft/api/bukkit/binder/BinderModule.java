package net.absolutioncraft.api.bukkit.binder;

import net.absolutioncraft.api.bukkit.BukkitAPI;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class BinderModule extends AbstractModule {
    private BukkitAPI bukkitAPI;
    private List<Module> moduleList;

    public BinderModule(@NotNull final BukkitAPI bukkitAPI, final Module... modules) {
        moduleList = new ArrayList<>();
        moduleList.addAll(Arrays.asList(modules));

        this.bukkitAPI = bukkitAPI;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        moduleList.forEach(this::install);
        bind(BukkitAPI.class).toInstance(this.bukkitAPI);
    }
}
