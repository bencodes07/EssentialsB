package de.bencodes.commands;

import de.bencodes.Essentials;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Command_ChatClear implements CommandExecutor {
    public Command_ChatClear() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player p = (Player)sender;
        if (!p.hasPermission("essentials.chatclear")) {
            p.sendMessage("§4Dazu hast du keine Rechte");
            return false;
        } else {
            for(int i = 0; i < 105; ++i) {

                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.sendMessage("");
                }
            }

            Bukkit.broadcastMessage(Essentials.pre + "§6Der Chat wurde von §c" + p.getName() + " §6gecleared");
            return true;
        }
    }
}
