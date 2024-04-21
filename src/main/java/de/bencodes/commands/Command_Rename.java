package de.bencodes.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Command_Rename implements CommandExecutor {
    public Command_Rename() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("essentials.rename") && args.length == 1) {
                String itemName = args[0];
                ItemStack i = p.getInventory().getItemInMainHand();
                ItemMeta im = i.getItemMeta();
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));
                i.setItemMeta(im);
            }
        }

        return false;
    }
}
