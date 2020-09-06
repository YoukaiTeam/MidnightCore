package net.absolutioncraft.api.bukkit.board.player;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@Getter @Setter
public final class PlayerTag {
    private Scoreboard tagScoreboard;
    private Team tagTeam;

    private String prefix;
    private String suffix;
}
