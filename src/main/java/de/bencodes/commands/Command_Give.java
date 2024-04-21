package de.bencodes.commands;

import de.bencodes.Essentials;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Command_Give implements CommandExecutor, TabCompleter {
    public Command_Give() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("essentials.i")) {
                if (args.length == 1) {
                    Material itemtype = Material.matchMaterial(args[0]);
                    ItemStack itemToGive = new ItemStack(itemtype, 64);
                    p.getInventory().addItem(new ItemStack[]{itemToGive});
                    p.sendMessage(Essentials.pre + "§6Du hast das Item §c64 Mal §6bekommen!");
                } else if (args.length == 2) {
                    int Amt = Integer.parseInt(args[1]);
                    Material itemtype = Material.matchMaterial(args[0]);
                    ItemStack itemToGive = new ItemStack(itemtype, Amt);
                    p.getInventory().addItem(new ItemStack[]{itemToGive});
                    p.sendMessage(Essentials.pre + "§6Du hast das Item §c" + Amt + "§c Mal §6bekommen!");
                } else {
                    p.sendMessage(Essentials.pre + "§cBenutze /i <Item> (<Menge>)");
                }
            } else {
                p.sendMessage("§4Das hättest du wohl gerne");
            }
        }

        return false;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        } else {
            if (args.length == 1) {
                Material[] var6 = Material.values();
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    Material value = var6[var8];
                    list.add(value.toString().toLowerCase());
                }
            } else if (args.length == 2) {
                for(int i = 0; i < 65; ++i) {
                    list.add(String.valueOf(i));
                }
            }

            ArrayList<String> completerList = new ArrayList<>();
            String currentArg = args[args.length - 1].toLowerCase();

            for (String s : list) {
                String s1 = s.toLowerCase();
                if (s1.startsWith(currentArg)) {
                    completerList.add(s);
                }
            }

            return completerList;
        }
    }
}
