package net.absolutioncraft.api.bukkit.storage;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Scopes;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;

/**
 * @author MelonDev
 * @since 0.1.0
 */
public final class UserStorageModule extends AbstractModule {
    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(UserDataProvider.class).to(UserDataStorage.class).in(Scopes.SINGLETON);
    }
}
