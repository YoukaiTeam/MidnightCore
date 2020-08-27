package net.absolutioncraft.api.bukkit.board.effect;

import net.absolutioncraft.api.bukkit.board.PlayerBoard;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.jetbrains.annotations.NotNull;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class GlowingTask implements Runnable {
    @NotNull private Objective objective;
    @NotNull private String title;

    private int counter;
    private int tilting;

    public GlowingTask(final @NotNull PlayerBoard playerBoard, final @NotNull String title) {
        this.objective = playerBoard.getObjective();
        this.title = title;

        this.counter = 0;
        this.tilting = 0;
    }

    @Override
    public void run() {
        if (this.counter < this.title.length()) {
            final String whitePart = ChatColor.WHITE + "" + ChatColor.BOLD + this.title.substring(0, this.counter);
            final String yellowPart = ChatColor.YELLOW + "" + ChatColor.BOLD + this.title.charAt(this.counter);
            final String orangePart = (this.counter < this.title.length() - 1) ? (ChatColor.GOLD + "" + ChatColor.BOLD + this.title.substring(this.counter + 1)) : "";
            this.objective.setDisplayName(whitePart + yellowPart + orangePart);
            ++this.counter;
        }
        else {
            if (this.tilting % 2 == 0) {
                this.objective.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + this.title);
            }
            else {
                this.objective.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + this.title);
            }
            if (this.tilting == 4) {
                this.tilting = 0;
                this.counter = 0;
            }
            else {
                ++this.tilting;
            }
        }
    }
}
