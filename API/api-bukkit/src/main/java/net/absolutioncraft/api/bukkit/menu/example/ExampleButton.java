package net.absolutioncraft.api.bukkit.menu.example;

import net.absolutioncraft.api.bukkit.menu.item.ClickableItem;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class ExampleButton {
    @NotNull
    public ClickableItem makeButton(@NotNull String itemTitle) {
        return new ClickableItem(new ItemStack(Material.DIAMOND, 1), click -> {
            HumanEntity clicker = click.getWhoClicked();
            clicker.sendMessage("Click! | item name: " + itemTitle);
            clicker.closeInventory();
            return true;
        });
    }
}
