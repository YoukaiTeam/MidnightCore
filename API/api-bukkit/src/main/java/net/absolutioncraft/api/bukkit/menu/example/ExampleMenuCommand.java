package net.absolutioncraft.api.bukkit.menu.example;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.absolutioncraft.api.bukkit.menu.Menu;
import net.absolutioncraft.api.bukkit.menu.MenuProvider;

import net.absolutioncraft.api.shared.serialization.TimeSerializer;
import net.absolutioncraft.api.shared.user.model.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Date;
import java.util.UUID;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class ExampleMenuCommand implements CommandExecutor {
    private final MenuProvider exampleMenuProvider;

    @Inject
    public ExampleMenuCommand(Injector injector) {
        this.exampleMenuProvider = injector.getInstance(ExampleMenu.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // This is an example User data, need to replace with real user data storage get method.
        User user = new User(UUID.randomUUID().toString(), "melon", "Melon", "melon@absolutioncraft.net", TimeSerializer.getUnixStamp(new Date()), "SKYWARS", TimeSerializer.getUnixStamp(new Date()), false, 1, 0, false, false, false, false);
        Menu menu = exampleMenuProvider.makeMenu(user);
        Inventory inventory = menu.getMenuInventory();

        ((Player)sender).openInventory(inventory);
        return true;
    }
}
