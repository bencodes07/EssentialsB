package de.bencodes.commands;

import de.bencodes.Essentials;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class Command_Kopf implements CommandExecutor {
    public Command_Kopf() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("kopf")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Essentials.pre + "Du musst ein Spieler sein!");
                return true;
            } else {
                Player p = (Player)sender;
                if (args.length == 0) {
                    p.sendMessage(Essentials.pre + "§6Du hast den Kopf von §c" + p.getName() + " §6bekommen");
                    p.getInventory().addItem(new ItemStack[]{this.getPlayerHead(p.getName())});
                    return true;
                } else {
                    p.sendMessage(Essentials.pre + "§6Du hast den Kopf von §c" + args[0] + " §6bekommen");
                    p.getInventory().addItem(new ItemStack[]{this.getPlayerHead(args[0])});
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    public ItemStack getPlayerHead(String player) {
        boolean isNewVersion = ((List<?>)Arrays.stream(Material.values()).map(Enum::name).collect(Collectors.toList())).contains("PLAYER_HEAD");
        Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
        ItemStack item = new ItemStack(type, 1);
        if (!isNewVersion) {
            item.setDurability((short)3);
        }

        SkullMeta meta = (SkullMeta)item.getItemMeta();
        meta.setOwner(player);
        item.setItemMeta(meta);
        return item;
    }
}
