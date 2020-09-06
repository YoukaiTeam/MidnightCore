package net.absolutioncraft.api.bukkit.board.tag;

import com.google.inject.Inject;

import net.absolutioncraft.api.bukkit.board.player.PlayerBoard;
import net.absolutioncraft.api.bukkit.board.player.PlayerTag;
import net.absolutioncraft.api.bukkit.board.exception.PlayerBoardException;
import net.absolutioncraft.api.bukkit.board.storage.ScoreboardViewerStorage;
import net.absolutioncraft.api.bukkit.board.storage.TagStorage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class TagHandler implements ITagHandler {
    @Inject private ScoreboardViewerStorage scoreboardViewerStorage;
    @Inject private TagStorage tagStorage;
    @Inject private Random random;

    @Override
    public void setPrefix(@NotNull Player player, @NotNull String string) throws PlayerBoardException {
        final UUID uuid = player.getUniqueId();

        final PlayerBoard playerBoard = scoreboardViewerStorage.getPlayerBoard(player);
        if (playerBoard == null) throw new PlayerBoardException("PlayerBoard must be created before setting Player prefix.");

        final Scoreboard scoreboard = playerBoard.getScoreboard();
        final PlayerTag playerTag = tagStorage.hasTag(uuid) ? tagStorage.getPlayerTag(player.getUniqueId()) : tagStorage.addTag(player);
        final String teamName = "TAG_" + random.nextInt(99999);

        playerTag.setTagScoreboard(scoreboard);
        playerTag.setTagTeam(scoreboard.registerNewTeam(teamName));
        playerTag.setPrefix(ChatColor.translateAlternateColorCodes('&', string));

        scoreboard.getTeam(playerTag.getTagTeam().getName()).setPrefix(ChatColor.translateAlternateColorCodes('&', string));
        scoreboard.getTeam(playerTag.getTagTeam().getName()).addEntry(player.getName());

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.getName().equals(player.getName())) return;

            // TODO 6/9/20: Fix this mess.
            final PlayerTag onlinePlayerTag = tagStorage.getPlayerTag(onlinePlayer.getUniqueId());
            scoreboard.registerNewTeam(onlinePlayerTag.getTagTeam().getName());
            scoreboard.getTeam(onlinePlayerTag.getTagTeam().getName()).setPrefix(onlinePlayerTag.getPrefix());
            scoreboard.getTeam(onlinePlayerTag.getTagTeam().getName()).addEntry(onlinePlayer.getName());

            onlinePlayerTag.getTagScoreboard().getTeam(onlinePlayerTag.getTagTeam().getName()).addEntry(player.getName());

            onlinePlayerTag.getTagScoreboard().registerNewTeam(playerTag.getTagTeam().getName());
            onlinePlayerTag.getTagScoreboard().getTeam(playerTag.getTagTeam().getName()).setPrefix(playerTag.getPrefix());
            onlinePlayerTag.getTagScoreboard().getTeam(playerTag.getTagTeam().getName()).addEntry(player.getName());
        });

        Bukkit.getLogger().log(Level.INFO, "[API] Tag Prefix set for: {0} ({1})",
                new Object[]{player.getName(), string});
    }

    @Override
    public void setSuffix(@NotNull Player player, @NotNull String string) throws PlayerBoardException {

    }

    @Override
    public void clearTag(@NotNull Player player) {
        if (tagStorage.hasTag(player.getUniqueId())) {
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                if (tagStorage.hasTag(onlinePlayer.getUniqueId())) {
                    final PlayerTag onlinePlayerTag = tagStorage.getPlayerTag(onlinePlayer.getUniqueId());
                    final Team onlineTeam = onlinePlayerTag.getTagTeam();
                    onlinePlayerTag.getTagScoreboard().getTeam(onlineTeam.getName()).removeEntry(player.getName());
                }
            });

            Bukkit.getLogger().log(Level.INFO, "[API] Tag removed for: {0}",
                    new Object[]{ player.getName() });
        }
    }
}
