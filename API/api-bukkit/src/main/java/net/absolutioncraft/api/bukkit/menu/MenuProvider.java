package net.absolutioncraft.api.bukkit.menu;

import net.absolutioncraft.api.shared.user.model.User;

import org.jetbrains.annotations.NotNull;

public interface MenuProvider {
    Menu makeMenu(@NotNull User user);
}
