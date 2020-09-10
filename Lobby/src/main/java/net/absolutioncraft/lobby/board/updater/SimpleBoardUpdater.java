package net.absolutioncraft.lobby.board.updater;

import net.absolutioncraft.api.bukkit.board.player.PlayerBoard;
import net.absolutioncraft.api.bukkit.board.updater.AbstractScoreboardSlotUpdater;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class SimpleBoardUpdater extends AbstractScoreboardSlotUpdater {
    public SimpleBoardUpdater(PlayerBoard playerBoard, int slotId, String text) {
        super(playerBoard, slotId, text);
    }

    @Override
    public void update(int slotId, String updateText) {
        this.setString(updateText);
        this.getPlayerBoard().setSlot(slotId, getString());
    }
}
