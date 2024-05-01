package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Heal implements CommandExecutor {
    public Command_Heal() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if (p.hasPermission("essentials.heal")) {
            p.setHealth(20.0D);
            p.setFoodLevel(20);
            p.sendMessage(Essentials.pre + "§6Du wurdest geheilt!");
        } else {
            p.sendMessage(Essentials.pre + "§cDazu hast du keine Rechte!");
        }

        return false;
    }
}
