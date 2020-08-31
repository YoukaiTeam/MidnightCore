package net.absolutioncraft.commons.bukkit.listener;

import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.serialization.StringSerializer;
import net.absolutioncraft.commons.bukkit.rank.event.UserRankChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class UserRankListener implements Listener {
    private final static String RANK_CHANGED = "§aTu rango fue actualizado por %s§a. Tu rango se ha establecido en %s§a.";

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRankChange(UserRankChangeEvent event) {
        Player player = Bukkit.getPlayerExact(event.getChanged().getDisplayName());
        String changerDisplay = event.getChanger().getUserRank().getColor() + event.getChanger().getDisplayName();
        String destinationRankDisplay = event.getDestinationRank().getPrefix();
        if (event.getDestinationRank() == UserRank.VIP) {
            destinationRankDisplay = StringSerializer.getSupporterHearts(event.getChanged());
        }
        if (player != null) {
            player.sendMessage(String.format(RANK_CHANGED, changerDisplay, destinationRankDisplay));
        }
    }
}
