package net.absolutioncraft.commons.bukkit.rank.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.absolutioncraft.api.bukkit.command.command.AbstractMidnightCommand;
import net.absolutioncraft.api.bukkit.storage.provider.UserDataProvider;
import net.absolutioncraft.api.shared.concurrent.CallbackHelper;
import net.absolutioncraft.api.shared.http.exception.InternalServerErrorException;
import net.absolutioncraft.api.shared.http.exception.NotFoundException;
import net.absolutioncraft.api.shared.http.exception.ServiceUnavailableException;
import net.absolutioncraft.api.shared.rank.UserRank;
import net.absolutioncraft.api.shared.serialization.TimeSerializer;
import net.absolutioncraft.api.shared.user.model.IUser;
import net.absolutioncraft.commons.bukkit.rank.sender.RankChangeSender;
import net.absolutioncraft.commons.bukkit.rank.validator.RankValidator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 0.0.1
 */
@Singleton
public final class SetRankCommand extends AbstractMidnightCommand {
    @Inject private UserDataProvider userDataProvider;
    @Inject private RankChangeSender rankChangeSender;

    private final static String ERROR_SYNTAX = "§cUso: /%s (usuario) (rango) (-1/tiempo)";
    private final static String INVALID_RANK = "§cEl rango introducido no es válido.";
    private final static String REQUEST_SENT = "§aEnviando solicitud...";
    private final static String REQUEST_SUCCESSFUL = "§aSolicitud exitosa. Detalles:\n§7Emisor de solicitud: §e%s\n§7Usuario de destino: §e%s\n§7Rango a colocar: §e%s\n§7Tiempo: §e%s";
    private final static String SENT_THROUGH_REDIS = "§aEl jugador se encuentra conectado en otro servidor, la solicitud fue enviada al servidor de destino.";
    private final static String DATA_UPDATE_FAIL = "§cHa ocurrido un error al actualizar los datos en la nube. #%s";

    public SetRankCommand() {
        super("setrank", UserRank.USER, new UserRank[0], "changerank");
    }

    @Override
    public void executeCommand(Player player, String[] args) {
        if (args == null) {
            player.sendMessage(String.format(ERROR_SYNTAX, this.getCommandName()));
            return;
        }
        if (args.length < 3) {
            player.sendMessage(String.format(ERROR_SYNTAX, this.getCommandName()));
            return;
        }
        if (!RankValidator.isValid(args[1].toUpperCase())) {
            player.sendMessage(INVALID_RANK);
            return;
        }
        try {
            IUser changer = userDataProvider.getCachedUserByNameSync(player.getName(), false);
            IUser changed = userDataProvider.getCachedUserByNameSync(args[0], true);
            UserRank destRank = UserRank.valueOf(args[1]);
            int unixTime = (int)TimeSerializer.parseDuration(args[2]);

            player.sendMessage(REQUEST_SENT);
            CallbackHelper.add(rankChangeSender.sendRankChange(changer, changed, destRank, unixTime), response -> {
                switch (response.getResponse()) {
                    case SUCCESS: {
                        player.sendMessage(String.format(REQUEST_SUCCESSFUL, changer.getDisplayName(), changed.getDisplayName(), destRank.toString(), TimeSerializer.parseUnixStamp(unixTime)));
                        break;
                    }
                    case SENT_THROUGH_REDIS: {
                        player.sendMessage(SENT_THROUGH_REDIS);
                        break;
                    }
                    case INTERNAL_ERROR: {
                        player.sendMessage(String.format(DATA_UPDATE_FAIL, response.getException().statusCode()));
                        break;
                    }
                }
            });
        } catch (NotFoundException | InternalServerErrorException | ServiceUnavailableException e) {
            Bukkit.getLogger().log(Level.SEVERE, "[Commons] Something went wrong updating User rank. #" + e.statusCode());
            player.sendMessage(String.format(DATA_UPDATE_FAIL, e.statusCode()));
            e.printStackTrace();
        }
    }
}
