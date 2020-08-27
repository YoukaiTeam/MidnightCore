package net.absolutioncraft.api.bukkit.menu.example;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import net.absolutioncraft.api.bukkit.menu.Menu;
import net.absolutioncraft.api.bukkit.menu.provider.MenuProvider;

import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.concurrent.CallbackHelper;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.rank.UserRank;
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
@Singleton
public final class ExampleMenuCommand implements CommandExecutor {
    @Inject private UserDataProvider userDataProvider;
    private final MenuProvider exampleMenuProvider;

    @Inject
    public ExampleMenuCommand(Injector injector) {
        this.exampleMenuProvider = injector.getInstance(ExampleMenu.class);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CallbackHelper.add(userDataProvider.getCachedUserByName(args[0], false), response -> {
            if (response.getStatus() == RestResponse.Status.SUCCESS) {
                Menu menu = exampleMenuProvider.makeMenu(response.getResponse());
                Inventory inventory = menu.getMenuInventory();

                ((Player)sender).openInventory(inventory);
            } else {
                sender.sendMessage("La wea salió mal: #" + response.getThrowedException().statusCode());
            }
        });
        return true;
    }
}
