//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.bencodes.commands;

import de.bencodes.Essentials;
import de.bencodes.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load/unload | delete | modify> <PosName>");
                    }
                    Inventory inv = Bukkit.createInventory(null, 3*9, "§6Positionen");
                    inv.setItem(10, new ItemBuilder(Material.LIME_DYE).setDisplayname("§aPosition speichern").build());
                    p.openInventory(inv);
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("unload")) {
                        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                        p.sendMessage(Essentials.pre + "§6Position wurde entladen");
                    }  else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load/unload | delete | modify> <PosName>");
                    }
                } else if (args.length == 2) {
                    String posName = args[1];
                    if (args[0].equalsIgnoreCase("save")) {
                        config.set("Position." + posName + ".X", p.getLocation().getX());
                        config.set("Position." + posName + ".Y", p.getLocation().getY());
                        config.set("Position." + posName + ".Z", p.getLocation().getZ());
                        config.set("Position." + posName + ".World", p.getLocation().getWorld().getName());
                        config.set("Position." + posName + ".Player", p.getName());
                        config.set("Position." + posName + ".Public", false);
                        Essentials.getInstance().saveConfig();
                        p.sendMessage(Essentials.pre + "§6Die Position wurde gespeichert");
                    } else if (args[0].equalsIgnoreCase("load")) {
                        if (config.contains("Position." + posName)) {
                            String playerForPosition = config.getString("Position." + posName + ".Player");
                            boolean isPublic = config.getBoolean("Position." + posName + ".Public", false);
                            if (playerForPosition.equals(p.getName()) || isPublic) {
                                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
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
                            } else {
                                p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung für diese Position!");
                            }
                        } else {
                            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
                        }
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        if (config.contains("Position." + posName)) {
                            String playerForPosition = config.getString("Position." + posName + ".Player");
                            if (playerForPosition.equals(p.getName())) {
                                config.set("Position." + posName, null);
                                Essentials.getInstance().saveConfig();
                                p.sendMessage(Essentials.pre + "§6Die Position wurde gelöscht");
                            } else {
                                p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung um diese Position zu löschen!");
                            }
                        } else {
                            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
                        }
                    } else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load/unload | delete | modify> <PosName>");
                    }
                } else if (args.length == 4 && args[0].equalsIgnoreCase("modify")) {
                    String posName = args[1];
                    if (config.contains("Position." + posName)) {
                        String playerForPosition = config.getString("Position." + posName + ".Player");
                        if (playerForPosition.equals(p.getName())) {
                            String modificationType = args[2];
                            if (modificationType.equalsIgnoreCase("public")) {
                                boolean isPublic = Boolean.parseBoolean(args[3]);
                                config.set("Position." + posName + ".Public", isPublic);
                                Essentials.getInstance().saveConfig();
                                p.sendMessage(Essentials.pre + "§6Die Position ist nun " + (isPublic ? "öffentlich" : "privat") + ".");
                            } else if (modificationType.equalsIgnoreCase("displayName")) {
                                String newDisplayName = args[3];
                                config.set("Position." + posName + ".DisplayName", newDisplayName);
                                Essentials.getInstance().saveConfig();
                                p.sendMessage(Essentials.pre + "§6Der Name der Position wurde geändert.");
                            } else {
                                p.sendMessage(Essentials.pre + "§cInvalid modification type. Use 'public true/false' or 'displayName <NewDisplayName>'.");
                            }
                        } else {
                            p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung um diese Position zu modifizieren!");
                        }
                    } else {
                        p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
                    }
                } else {
                    p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load/unload | delete | modify> <PosName>");
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        FileConfiguration config = Essentials.getInstance().getConfig();
        Set<String> positions = config.getConfigurationSection("Position").getKeys(false);

        Player player = (Player) sender;

        if (sender instanceof Player) {
            if (args.length == 1) {
                completions.add("save");
                completions.add("load");
                completions.add("unload");
                completions.add("delete");
                completions.add("modify");

                completions.removeIf(s -> !s.toLowerCase().startsWith(args[0].toLowerCase()));
                Collections.sort(completions);
                return completions;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("load") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("modify")) {
                    for (String posName : positions) {
                        String playerForPosition = config.getString("Position." + posName + ".Player");
                        boolean isPublic = config.getBoolean("Position." + posName + ".Public", false);
                        if ((playerForPosition != null && playerForPosition.equals(player.getName())) || isPublic) {
                            completions.add(posName);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("save")) {
                    completions.add("<PosName>");
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("modify")) {
                completions.add("public");
                completions.add("displayName");
            } else if (args.length == 4 && args[0].equalsIgnoreCase("modify")) {
                if (args[2].equalsIgnoreCase("public")) {
                    completions.add("true");
                    completions.add("false");
                }
            }
        }
        return completions;
    }
}