package net.absolutioncraft.commons.bukkit.user.task;

import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author MelonDev
 * @since 0.1.0
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Singleton
public final class SynchronousKickTask extends BukkitRunnable {
    private Player player;
    private String reason;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        player.kickPlayer(reason);
    }
}
