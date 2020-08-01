package net.absolutioncraft.api.bukkit.menu;

import lombok.Builder;

import net.absolutioncraft.api.bukkit.menu.item.ClickableItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Builder
public final class Menu {
    private String menuName;
    private int menuSize;
    private Map<Integer, ClickableItem> items;

    public Menu addButton(int slot, ClickableItem button) {
        this.items.put(slot, button);
        return this;
    }

    public Inventory getMenuInventory() {
        return new AbsolutionHolder(this).getInventory();
    }

    public static final class AbsolutionHolder implements InventoryHolder {
        private final Menu builder;
        private final Map<Integer, ClickableItem> buttons;

        AbsolutionHolder(@NotNull Menu builder) {
            this.builder = builder;
            this.buttons = builder.items;
        }

        @Override
        public Inventory getInventory() {
            Inventory inventory = Bukkit.createInventory(this, builder.menuSize, builder.menuName);

            builder.items.forEach((slot, button) -> inventory.setItem(slot, button.getItemStack()));

            return inventory;
        }

        public Map<Integer, ClickableItem> getButtons() {
            return buttons;
        }
    }
}
