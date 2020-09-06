package net.absolutioncraft.api.bukkit.board.storage;

import net.absolutioncraft.api.bukkit.board.player.PlayerTag;
import net.absolutioncraft.api.bukkit.board.registry.TagRegistry;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class TagStorage implements TagRegistry {
    private static Map<UUID, PlayerTag> tagStorage = new HashMap<>();

    @Override
    public PlayerTag addTag(@NotNull Player player) {
        PlayerTag playerTag = new PlayerTag();
        tagStorage.put(player.getUniqueId(), playerTag);
        return getPlayerTag(player.getUniqueId());
    }

    @Override
    public void removeTag(@NotNull Player player) {
        final PlayerTag playerTag = tagStorage.get(player.getUniqueId());
        playerTag.getTagTeam().unregister();
        tagStorage.remove(player.getUniqueId());
    }

    @Override
    public PlayerTag getPlayerTag(@NotNull UUID uuid) {
        return tagStorage.get(uuid);
    }

    @Override
    public boolean hasTag(@NotNull UUID uuid) {
        return tagStorage.containsKey(uuid);
    }
}
