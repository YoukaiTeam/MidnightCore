package net.absolutioncraft.api.bukkit.command.listener;

import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.command.command.AbstractMidnightCommand;
import net.absolutioncraft.api.bukkit.command.registry.CommandRegistry;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.concurrent.CallbackHelper;
import net.absolutioncraft.api.shared.http.response.RestResponse;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.user.model.IUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class CommandListener implements Listener {
    @Inject private CommandRegistry commandRegistry;
    @Inject private UserDataProvider userDataProvider;

    @EventHandler
    public void commandResponse(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String input = event.getMessage();
        String usedCommand = input.substring(1);
        String[] arguments = new String[0];

        if (usedCommand.contains(" ")) {
            usedCommand = usedCommand.split(" ")[0];
            arguments = input.substring(input.indexOf(' ') + 1).split(" ");
        }

        AbstractMidnightCommand command = commandRegistry.getCommandUsingAlias(usedCommand.toLowerCase()).orElse(null);
        if (command == null) return;

        event.setCancelled(true);

        if (command.getRequiredRank() == UserRank.USER) {
            command.executeCommand(player, arguments);
            return;
        }

        String[] finalArguments = arguments;
        CallbackHelper.add(userDataProvider.getCachedUserByName(player.getName(), true), response -> {
            if (response.getStatus() == RestResponse.Status.SUCCESS) {
                final IUser user = response.getResponse();
                if (user.getUserRank() == command.getRequiredRank()) {
                    command.executeCommand(player, finalArguments);
                } else if (Arrays.asList(command.getSecondaryRanks()).contains(user.getUserRank())) {
                    command.executeCommand(player, finalArguments);
                } else {
                    // No perms
                    player.sendMessage("sin permisos we");
                }
            } else {
                player.sendMessage("el core se rompio de nuevo we");
            }
        });
    }
}
