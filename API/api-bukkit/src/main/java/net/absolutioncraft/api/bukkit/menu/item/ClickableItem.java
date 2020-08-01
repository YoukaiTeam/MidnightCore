package net.absolutioncraft.api.bukkit.menu.item;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.inventory.ItemStack;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public final class ClickableItem {
    private ItemStack itemStack;
    private ClickableItemAction action;
}
