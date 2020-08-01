package net.absolutioncraft.api.bukkit.menu.item;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.jetbrains.annotations.NotNull;

public interface ClickableItemAction {
    boolean click(@NotNull InventoryClickEvent event);
}
