package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Command_Vanish implements CommandExecutor {

    private final Set<UUID> vanishedPlayers = new HashSet<>();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player targetPlayer = null;

        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(Essentials.pre + " §cDu musst ein Spieler sein, um diesen Befehl auszuführen!");

                return true;
            }
            targetPlayer = (Player) sender;
        }  else {
            targetPlayer = Bukkit.getPlayer(args[0]);

            if(targetPlayer == null) {
                sender.sendMessage(Essentials.pre + " §cDieser Spieler ist offline");

                return true;
            }
        }

        boolean isVanished = vanishedPlayers.contains(targetPlayer.getUniqueId());

        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if(otherPlayer.equals(targetPlayer)) {
                continue;
            }

            if(isVanished) {
                otherPlayer.showPlayer(Essentials.getInstance(), targetPlayer);
            } else {
                otherPlayer.hidePlayer(Essentials.getInstance(), targetPlayer);
            }
        }

        sender.sendMessage(Essentials.pre + " §7Du hast den Spieler §b" + targetPlayer.getName() + " §7" + (isVanished ? "sichtbar" : "unsichtbar") + " §7gemacht");

        if(isVanished) {
            vanishedPlayers.remove(targetPlayer.getUniqueId());
        } else {
            vanishedPlayers.add(targetPlayer.getUniqueId());
        }

        return true;
    }
}
