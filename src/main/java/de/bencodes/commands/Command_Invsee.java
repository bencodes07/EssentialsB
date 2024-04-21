package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Invsee implements CommandExecutor {
    public Command_Invsee() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("essentials.invsee")) {
                if (args.length == 1) {
                    Player open_inv = Bukkit.getPlayerExact(args[0]);
                    if (open_inv != null) {
                        p.openInventory(open_inv.getInventory());
                    } else {
                        p.sendMessage(Essentials.pre + " §cDieser Spieler ist offline");
                    }
                } else {
                    p.sendMessage(Essentials.pre + " §cBenutze /invsee <Spieler>");
                }
            }
        }

        return false;
    }
}
