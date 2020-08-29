package net.absolutioncraft.api.bukkit.board.scoreboard;

import net.absolutioncraft.api.bukkit.board.PlayerBoard;
import net.absolutioncraft.api.bukkit.board.util.StringSplitter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class ScoreboardHandler implements IScoreboardHandler {
    @Override
    public void handleBoard(final @NotNull Player player, final @NotNull PlayerBoard playerBoard) {
        Scoreboard scoreboard;
        Objective objective;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("MIO_SB", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i = 1; i <= 15; i++) {
            Team team = scoreboard.registerNewTeam("SB_" + i);
            team.addEntry(StringSplitter.genEntry(i));
        }

        playerBoard.setScoreboard(scoreboard);
        playerBoard.setObjective(objective);

        player.setScoreboard(scoreboard);

        Bukkit.getLogger().log(Level.INFO, "[API] Scoreboard created for: {0}",
                new Object[]{ player.getName() });
    }

    @Override
    public void removeBoard(PlayerBoard playerBoard) {
        if (playerBoard != null) {
            playerBoard.disableGlow();
            playerBoard.getObjective().unregister();

            Bukkit.getLogger().log(Level.INFO, "[API] Scoreboard removed for: {0}",
                    new Object[]{ playerBoard.getScoreboard().getPlayers().iterator().next().getName() });
        }
    }
}
