package de.bencodes.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Day implements CommandExecutor {
    public Command_Day() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player p = (Player)sender;
        World world = p.getWorld();
        if (p.hasPermission("essentials.time.day")) {
            world.setTime(0L);
        }

        return false;
    }
}
