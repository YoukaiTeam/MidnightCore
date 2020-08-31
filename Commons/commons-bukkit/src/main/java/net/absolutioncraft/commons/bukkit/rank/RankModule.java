package net.absolutioncraft.commons.bukkit.rank;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Scopes;

import net.absolutioncraft.commons.bukkit.rank.sender.RankChangeSender;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class RankModule extends AbstractModule {
    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(RankChangeSender.class).to(RankManager.class).in(Scopes.SINGLETON);
    }
}
