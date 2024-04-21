package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class Command_Commandspy implements CommandExecutor {
    public Command_Commandspy() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Essentials.pre + "Du musst ein Spieler sein!");
        }

        Player p = (Player)sender;
        if (p.hasPermission("essentials.commandspy")) {
            if (p.hasMetadata("cmdspy")) {
                p.removeMetadata("cmdspy", Essentials.getInstance());
                p.sendMessage(Essentials.pre + ChatColor.GOLD + "Du siehst die Commands der Spieler nun nicht mehr!");
            } else {
                p.setMetadata("cmdspy", new FixedMetadataValue(Essentials.getInstance(), true));
                p.sendMessage(Essentials.pre + ChatColor.GOLD + "Du siehst nun alle Commands der Spieler!");
            }
        } else {
            p.sendMessage(Essentials.pre + ChatColor.DARK_RED + "Das hättest du wohl gerne");
        }

        return false;
    }
}
