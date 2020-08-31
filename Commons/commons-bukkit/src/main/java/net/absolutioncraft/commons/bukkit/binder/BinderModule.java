package net.absolutioncraft.commons.bukkit.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import net.absolutioncraft.api.bukkit.storage.UserDataStorage;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.commons.bukkit.CommonsBukkit;

import net.absolutioncraft.commons.bukkit.rank.RankChange;
import net.absolutioncraft.commons.bukkit.rank.RankManager;
import net.absolutioncraft.commons.bukkit.rank.changeable.ChangedRank;
import net.absolutioncraft.commons.bukkit.rank.sender.RankChangeSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class BinderModule extends AbstractModule {
    private CommonsBukkit commonsBukkit;
    private List<Module> moduleList;

    public BinderModule(@NotNull final CommonsBukkit commonsBukkit, final Module... modules) {
        moduleList = new ArrayList<>();
        moduleList.addAll(Arrays.asList(modules));

        this.commonsBukkit = commonsBukkit;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    public void configure() {
        moduleList.forEach(this::install);
        bind(CommonsBukkit.class).toInstance(this.commonsBukkit);
    }
}
