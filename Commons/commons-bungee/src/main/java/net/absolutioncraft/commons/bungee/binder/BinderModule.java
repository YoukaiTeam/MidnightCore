package net.absolutioncraft.commons.bungee.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.absolutioncraft.commons.bungee.CommonsBungee;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class BinderModule extends AbstractModule {
    private CommonsBungee commonsBungee;
    private List<Module> moduleList;

    public BinderModule(@NotNull final CommonsBungee commonsBungee, final Module... modules) {
        moduleList = new ArrayList<>();
        moduleList.addAll(Arrays.asList(modules));

        this.commonsBungee = commonsBungee;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        moduleList.forEach(this::install);
        bind(CommonsBungee.class).toInstance(this.commonsBungee);
    }
}
