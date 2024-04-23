package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Enderchest implements CommandExecutor {
    public Command_Enderchest() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if (p.hasPermission("essentials.enderchest")) {
            if (args.length == 1) {
                if(!p.hasPermission("essentials.enderchest.o")) {
                    p.sendMessage(Essentials.pre + " §cDu darfst keine Enderchest von anderen öffnen");
                    return false;
                }
                Player open_inv = p.getServer().getPlayerExact(args[0]);
                if (open_inv != null) {
                    p.openInventory(open_inv.getEnderChest());
                } else {
                    p.sendMessage(Essentials.pre + " §cDieser Spieler ist offline");
                }
            } else if (args.length == 0) {
                p.openInventory(p.getEnderChest());
            } else {
                p.sendMessage(Essentials.pre + " §cBenutze /enderchest <Spieler>");
            }
        } else {
            p.sendMessage(Essentials.pre + " §4Das hättest du wohl gerne");
        }

        return false;
    }
}
