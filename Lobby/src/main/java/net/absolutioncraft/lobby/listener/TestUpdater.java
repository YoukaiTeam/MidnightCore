package net.absolutioncraft.lobby.listener;

import net.absolutioncraft.api.bukkit.board.PlayerBoard;
import net.absolutioncraft.api.bukkit.board.updater.AbstractScoreboardSlotUpdater;

/**
 * @author MelonDev
 * @since 1.0.0
 */
public final class TestUpdater extends AbstractScoreboardSlotUpdater {
    public TestUpdater(PlayerBoard playerBoard, int slotId, String text) {
        super(playerBoard, slotId, text);
    }

    @Override
    public void update(int slotId, String updateText) {
        this.setString(updateText);
        this.getPlayerBoard().setSlot(slotId, getString());
    }
}
