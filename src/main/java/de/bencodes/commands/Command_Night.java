package de.bencodes.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Night implements CommandExecutor {
    public Command_Night() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        World world = p.getWorld();
        if (p.hasPermission("essentials.time.night")) {
            world.setTime(13000L);
        }

        return false;
    }
}
