//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.bencodes.commands;

import de.bencodes.Essentials;
import de.bencodes.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Command_Pos implements CommandExecutor, TabCompleter {
    public Command_Pos() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("essentials.pos")) {
                FileConfiguration config = Essentials.getInstance().getConfig();

                if (args.length == 0) {
                    if(!p.hasPermission("essentials.pos.test")) {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load> <PosName>");
                    }
                    Inventory inv = Bukkit.createInventory(null, 3*9, "§6Positionen");
                    inv.setItem(10, new ItemBuilder(Material.LIME_DYE).setDisplayname("§aPosition speichern").build());
                    p.openInventory(inv);
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        p.sendMessage(Essentials.pre + "§6Positionen: " + Objects.requireNonNull(config.getConfigurationSection("Position")).getKeys(false));
                    } else if (args[0].equalsIgnoreCase("unload")) {
                        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                        p.sendMessage(Essentials.pre + "§6Position wurde entladen");
                    }  else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load> <PosName>");
                    }
                } else if (args.length == 2) {
                    String posName = args[1];
                    if (args[0].equalsIgnoreCase("save")) {
                        config.set("Position." + posName + ".X", p.getLocation().getX());
                        config.set("Position." + posName + ".Y", p.getLocation().getY());
                        config.set("Position." + posName + ".Z", p.getLocation().getZ());
                        config.set("Position." + posName + ".World", p.getLocation().getWorld().getName());
                        config.set("Position." + posName + ".Player", p.getName());
                        Essentials.getInstance().saveConfig();
                        p.sendMessage(Essentials.pre + "§6Die Position wurde gespeichert");
                    } else if (args[0].equalsIgnoreCase("load")) {
                        if (config.contains("Position." + posName)) {
                            Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                            // Display coordinates on scoreboard
                            Objective objective = scoreboard.registerNewObjective(Essentials.getInstance().getName(), "positions");
                            objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
                            objective.setDisplayName("§6§lPosition: §r§b" + posName);
                            double x = config.getDouble("Position." + posName + ".X");
                            int xValue = (int)x;
                            double y = config.getDouble("Position." + posName + ".Y");
                            int yValue = (int)y;
                            double z = config.getDouble("Position." + posName + ".Z");
                            int zValue = (int)z;
                            objective.getScore("§7 X: §a" + xValue).setScore(4);
                            objective.getScore("§7 Y: §a" + yValue).setScore(3);
                            objective.getScore("§7 Z: §a" + zValue).setScore(2);
                            objective.getScore("§7 World: §a" + (config.getString("Position." + posName + ".World").split("_")[1]).toString().substring(0,1).toUpperCase() + config.getString("Position." + posName + ".World").split("_")[1].toString().substring(1)).setScore(1); // Remove world_ prefix
                            p.setScoreboard(scoreboard);
//
//                            double x = config.getDouble("Position." + posName + ".X");
//                            int xValue = (int)x;
//                            double y = config.getDouble("Position." + posName + ".Y");
//                            int yValue = (int)y;
//                            double z = config.getDouble("Position." + posName + ".Z");
//                            int zValue = (int)z;
//                            Location location = new Location(p.getWorld(), (double)xValue, (double)yValue, (double)zValue);
//                            p.sendMessage(Essentials.pre + "§6Die Position lautet: X: " + location.getX() + "  Y: " + location.getY() + "  Z: " + location.getZ());
                        } else {
                            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
                        }
                    }else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load> <PosName>");
                    }
                } else {
                    p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load> <PosName>");
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("save");
            completions.add("load");
            completions.add("list");
            completions.add("unload");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("load")) {
                FileConfiguration config = Essentials.getInstance().getConfig();
                Set<String> positions = Objects.requireNonNull(config.getConfigurationSection("Position")).getKeys(false);
                completions.addAll(positions);
            }
        }

        return completions;
    }
}

/*
 * --- TODOS: ---
 * - Add a method to remove a position
 * - Add a method to list all positions accessible by a player
 * - Add a method to modify a positions name or coordinates
 * - Add a method to teleport to a position
 * - Add a method make a position public or private
 */

// Make sure your class implements TabCompleter.
//public class Test implements TabCompleter {
//    public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
//        List<String> list = new ArrayList<>();
//        // Now, it's just like any other command.
//        // Check if the sender is a player.
//        if (sender instanceof Player) {
//            // Check if the command is "something."
//            if (cmd.getName().equalsIgnoreCase("command")) {
//                // If the player has not typed anything in
//                if (args.length == 0) {
//                    // Add a list of words that you'd like to show up
//                    // when the player presses tab.
//                    list.add("hello");
//                    list.add("hello2");
//                    // Sort them alphabetically.
//                    Collections.sort(list);
//                    // return the list.
//                    return list;
//                    // If player has typed one word in.
//                    // This > "/command hello " does not count as one
//                    // argument because of the space after the hello.
//                } else if (args.length == 1) {
//                    list.add("hello");
//                    list.add("hello2");
//                    for (String s : list){
//                        // Since the player has already typed something in,
//                        // we ant to complete the word for them so we check startsWith().
//                        if (!s.toLowerCase().startsWith(args[0].toLowerCase())){
//                            list.remove(s);
//                        }
//                    }
//                    Collections.sort(list);
//                    return list;
//                }
//            }
//        }
//        // return null at the end.
//        return null;
//    }
//}