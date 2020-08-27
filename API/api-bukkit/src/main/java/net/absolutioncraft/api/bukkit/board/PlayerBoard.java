package net.absolutioncraft.api.bukkit.board;

import lombok.Getter;
import lombok.Setter;

import net.absolutioncraft.api.bukkit.board.creator.ScoreboardCreator;

import net.absolutioncraft.api.bukkit.board.effect.GlowingTask;
import net.absolutioncraft.api.bukkit.board.util.StringSplitter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class PlayerBoard implements ScoreboardCreator {
    @Getter @Setter
    private Scoreboard scoreboard;
    @Getter @Setter
    private Objective objective;

    @Getter
    private String title;

    private int glowingTaskId;

    @Override
    public void enableGlow(final @NotNull Plugin taskPlugin) {
        this.glowingTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(taskPlugin, new GlowingTask(this, this.title), 0L, 4L).getTaskId();
    }

    @Override
    public void disableGlow() {
        if (glowingTaskId != 0) {
            Bukkit.getScheduler().cancelTask(glowingTaskId);
        }
    }

    @Override
    public void setTitle(@NotNull final String string) {
        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', string));
        this.title = string;
    }

    @Override
    public void setSlot(final int slot, @NotNull String string) {
        final Team team = scoreboard.getTeam("SB_" + slot);
        final String entry = StringSplitter.genEntry(slot);

        if (!scoreboard.getEntries().contains(entry)) {
            objective.getScore(entry).setScore(slot);
        }

        string = ChatColor.translateAlternateColorCodes('&', string);

        final String pre =  StringSplitter.getFirstSplit(string);
        final String suf = StringSplitter.getFirstSplit(ChatColor.getLastColors(pre) + StringSplitter.getSecondSplit(string));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    @Override
    public void removeSlot(final int slot) {
        final String entry = StringSplitter.genEntry(slot);
        if (scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }

    @Override
    public void setSlotsFromList(@NotNull List<String> list) {
        while(list.size()>15) {
            list.remove(list.size()-1);
        }

        int slot = list.size();

        if(slot < 15) {
            for(int i=(slot +1); i<=15; i++) {
                removeSlot(i);
            }
        }

        for(String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }
}
