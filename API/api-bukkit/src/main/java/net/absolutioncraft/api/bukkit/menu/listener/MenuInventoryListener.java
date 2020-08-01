package net.absolutioncraft.api.bukkit.menu.listener;

import net.absolutioncraft.api.bukkit.menu.Menu;
import net.absolutioncraft.api.bukkit.menu.item.ClickableItem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class MenuInventoryListener implements Listener {

    // TODO: Make debuggers for this.
    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(final InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;
        if (!(inventory.getHolder() instanceof Menu.AbsolutionHolder)) return;

        Menu.AbsolutionHolder absolutionHolder = (Menu.AbsolutionHolder) inventory.getHolder();

        int clickedSlot = event.getSlot();
        if (clickedSlot < 0) return;

        ClickableItem clickableItem = absolutionHolder.getButtons().get(clickedSlot);
        if (clickableItem == null) return;

        event.setCancelled(clickableItem.getAction().click(event));
    }
}
