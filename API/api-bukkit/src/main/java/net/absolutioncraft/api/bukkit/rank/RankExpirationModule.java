package net.absolutioncraft.api.bukkit.rank;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import net.absolutioncraft.api.bukkit.rank.expirer.RankExpirer;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public class RankExpirationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RankExpirer.class).to(RankExpirationChecker.class).in(Scopes.SINGLETON);
    }
}
