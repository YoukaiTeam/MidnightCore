package net.absolutioncraft.lobby.board;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import net.absolutioncraft.lobby.board.helper.LobbyBoardHelper;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class LobbyBoardModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LobbyBoardHelper.class).to(LobbyBoardHandler.class).in(Scopes.SINGLETON);
    }
}
