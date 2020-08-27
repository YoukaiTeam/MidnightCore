package net.absolutioncraft.api.bukkit.menu.provider;

import net.absolutioncraft.api.bukkit.menu.Menu;
import net.absolutioncraft.api.shared.user.model.IUser;

import org.jetbrains.annotations.NotNull;

public interface MenuProvider {
    Menu makeMenu(@NotNull IUser user);
}
