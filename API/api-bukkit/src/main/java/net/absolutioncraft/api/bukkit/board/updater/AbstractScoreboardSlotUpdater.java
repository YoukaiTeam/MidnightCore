package net.absolutioncraft.api.bukkit.board.updater;

import lombok.*;
import net.absolutioncraft.api.bukkit.board.PlayerBoard;

/**
 * @author MelonDev
 * @since 1.0.0
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class AbstractScoreboardSlotUpdater {
    @Getter(value = AccessLevel.PUBLIC)
    public PlayerBoard playerBoard;
    public int slotId;
    @Getter(value = AccessLevel.PUBLIC) @Setter(value = AccessLevel.PUBLIC)
    public String string;

    public abstract void update(int slotId, String string);
}
