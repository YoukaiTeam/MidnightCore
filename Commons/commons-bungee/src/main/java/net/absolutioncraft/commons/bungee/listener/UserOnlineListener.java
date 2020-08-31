package net.absolutioncraft.commons.bungee.listener;

import com.google.inject.Inject;
import net.absolutioncraft.api.shared.online.provider.OnlineProvider;
import net.absolutioncraft.commons.bungee.CommonsBungee;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class UserOnlineListener implements Listener {
    @Inject private CommonsBungee commonsBungee;
    @Inject private OnlineProvider onlineProvider;

    private final static String ALREADY_LOGGED = "§cYa estás conectado en el servidor.\n\n§7Por favor intenta dentro de un momento.\n§7Si el problema persiste, por favor contacta con la administración del serviodr.";

    @EventHandler
    public void onConnect(LoginEvent event) {
        event.registerIntent(commonsBungee);
        commonsBungee.getProxy().getScheduler().runAsync(commonsBungee, () -> {
            if (event.isCancelled()) return;

            String username = event.getConnection().getName().toLowerCase();
            if (onlineProvider.isUserOnline(username)) {
                event.setCancelled(true);
                event.setCancelReason();
                return;
            }

            onlineProvider.setStatus(username, true);
            commonsBungee.getLogger().log(Level.INFO, "[Commons] User now online: {0}", new Object[] { username });

            event.completeIntent(commonsBungee);
        });
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        commonsBungee.getProxy().getScheduler().runAsync(commonsBungee, () -> {
            String username = event.getPlayer().getName().toLowerCase();

            onlineProvider.setStatus(username, false);
            commonsBungee.getLogger().log(Level.INFO, "[Commons] User now disconnected: {0}", new Object[] { username });
        });
    }
}
