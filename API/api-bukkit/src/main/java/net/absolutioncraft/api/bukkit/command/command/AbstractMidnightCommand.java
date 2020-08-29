package net.absolutioncraft.api.bukkit.command.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.absolutioncraft.api.shared.rank.UserRank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
public abstract class AbstractMidnightCommand implements RunnableCommand {
    private String commandName;
    private UserRank requiredRank;
    private UserRank[] secondaryRanks;

    private List<String> aliasList;

    public AbstractMidnightCommand(String commandName, UserRank mainRequiredRank, UserRank[] optionalRanks) {
        this.commandName = commandName;
        this.requiredRank = mainRequiredRank;
        this.secondaryRanks = optionalRanks;
        this.aliasList = Collections.emptyList();
    }

    public AbstractMidnightCommand(String commandName, UserRank mainRequiredRank, UserRank[] optionalRanks, String... aliases) {
        this.commandName = commandName;
        this.requiredRank = mainRequiredRank;
        this.secondaryRanks = optionalRanks;
        this.aliasList = Arrays.asList(aliases);
    }

    protected List<String> getUserMatches(final Player sender, final String start) {
        final List<String> matches = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> {
            final String lowerName = player.getName().toLowerCase();
            if (sender.canSee(player) && lowerName.startsWith(start.toLowerCase())) {
                matches.add(player.getName());
            }
        });
        return matches;
    }
}
