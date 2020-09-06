package net.absolutioncraft.lobby.listener;

import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.concurrent.CallbackHelper;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.serialization.StringSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class AsyncPlayerChatListener implements Listener {
    @Inject private UserDataProvider userDataProvider;

    private final static String ERR_INTERNAL = "§cHa ocurrido un error interno al intentar ejecutar tu última acción. Contacta con la administración si el problema persiste.\n§7Código de error: #%s";

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(true);
        CallbackHelper.add(userDataProvider.getCachedUserByName(player.getName(), false), response -> {
            if (response.getStatus() == RestResponse.Status.SUCCESS) {
                if (response.getResponse().getUserRank() == UserRank.VIP) {
                    Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', StringSerializer.getSupporterHearts(response.getResponse()) + response.getResponse().getDisplayName() + "&f: " + event.getMessage())));
                } else {
                    Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', response.getResponse().getUserRank().getPrefix() + response.getResponse().getDisplayName() + "&f: " + event.getMessage())));
                }
            } else {
                player.sendMessage(String.format(ERR_INTERNAL, response.getThrowedException().statusCode()));
            }
        });
    }
}
