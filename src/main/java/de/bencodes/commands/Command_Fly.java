package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Fly implements CommandExecutor {
    public Command_Fly() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if (sender instanceof Player) {
            if (p.hasPermission("essentials.fly")) {
                p.setAllowFlight(!p.getAllowFlight());
                p.sendMessage(Essentials.pre + "§6Flugmodus umgeschlatet!");
            } else {
                p.sendMessage(Essentials.pre + "§4Das hättest du wohl gerne");
            }
        } else {
            sender.sendMessage("Du musst ein Spieler sein!");
        }

        return false;
    }
}
