package net.absolutioncraft.api.bukkit.menu.example;

import com.google.inject.Inject;
import net.absolutioncraft.api.bukkit.menu.Menu;
import net.absolutioncraft.api.bukkit.menu.provider.MenuProvider;
import net.absolutioncraft.api.shared.user.model.IUser;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * @author MelonDev
 * @since 0.0.1
 */
public final class ExampleMenu implements MenuProvider {
    @Inject private ExampleButton exampleButton;

    @Override
    public Menu makeMenu(@NotNull IUser user) {
        return Menu.builder()
                .menuName("Test")
                .menuSize(27)
                .items(new HashMap<>())
                .build()
                .addButton(1, exampleButton.makeButton("Tu nombre: " + user.getDisplayName()));
    }
}
