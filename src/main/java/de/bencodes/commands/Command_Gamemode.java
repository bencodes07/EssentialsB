package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_Gamemode implements CommandExecutor {
    public Command_Gamemode() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if (p.hasPermission("essentials.gamemode")) {
            if (args[0].equalsIgnoreCase("1")) {
                if (p.hasPermission("essentials.gamemode.c")) {
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(Essentials.pre + "§6Spielmodus auf §cKreativ §6gesetzt");
                } else {
                    p.sendMessage(Essentials.pre + " §4Das hättest du wohl gerne");
                }
            } else if (args[0].equalsIgnoreCase("0")) {
                if (p.hasPermission("essentials.gamemode.s")) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(Essentials.pre + "§6Spielmodus auf §cSurvival §6gesetzt");
                } else {
                    p.sendMessage(Essentials.pre + " §4Das hättest du wohl gerne");
                }
            } else if (args[0].equalsIgnoreCase("2")) {
                if (p.hasPermission("essentials.gamemode.a")) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(Essentials.pre + "§6Spielmodus auf §cAdventure §6gesetzt");
                } else {
                    p.sendMessage(Essentials.pre + " §4Das hättest du wohl gerne");
                }
            } else if (args[0].equalsIgnoreCase("3")) {
                if (p.hasPermission("essentials.gamemode.sp")) {
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(Essentials.pre + "§6Spielmodus auf §cSpectator §6gesetzt");
                } else {
                    p.sendMessage(Essentials.pre + " §4Das hättest du wohl gerne");
                }
            } else {
                p.sendMessage(Essentials.pre + "§cBenutze /gm 0|1|2|3");
            }
        } else {
            p.sendMessage(Essentials.pre + " §4Das hättest du wohl gerne");
        }

        return false;
    }
}
