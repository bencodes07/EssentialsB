package de.bencodes.commands;

import de.bencodes.Essentials;
import de.bencodes.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Command_Pos implements CommandExecutor, TabCompleter {
    public Command_Pos() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("essentials.pos")) {
                FileConfiguration config = Essentials.getInstance().getConfig();

                if (args.length == 0) {
                    p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load | unload | delete | modify | list | gui> <PosName>");
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("unload")) {
                        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                        p.sendMessage(Essentials.pre + "§6Position wurde entladen");
                    } else if (args[0].equalsIgnoreCase("list")) {
                        listPositions(p, config);
                    } else if (args[0].equalsIgnoreCase("gui")) {
                        openPositionGUI(p, config);
                    } else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load | unload | delete | modify | list | gui> <PosName>");
                    }
                } else if (args.length == 2) {
                    String posName = args[1];
                    if (args[0].equalsIgnoreCase("save")) {
                        savePosition(p, config, posName);
                    } else if (args[0].equalsIgnoreCase("load")) {
                        loadPosition(p, config, posName);
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        deletePosition(p, config, posName);
                    } else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load | unload | delete | modify | list | gui> <PosName>");
                    }
                } else if (args.length == 3 && args[0].equalsIgnoreCase("modify")) {
                    String posName = args[1];
                    String modificationType = args[2];

                    if (modificationType.equalsIgnoreCase("location")) {
                        updatePositionLocation(p, config, posName);
                    } else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos modify <PosName> <location | public | rename> [value]");
                    }
                } else if (args.length == 4 && args[0].equalsIgnoreCase("modify")) {
                    String posName = args[1];
                    String modificationType = args[2];
                    String value = args[3];

                    if (modificationType.equalsIgnoreCase("public")) {
                        updatePositionPublic(p, config, posName, value);
                    } else if (modificationType.equalsIgnoreCase("rename")) {
                        renamePosition(p, config, posName, value);
                    } else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos modify <PosName> <location | public | rename> [value]");
                    }
                } else {
                    p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load | unload | delete | modify | list | gui> <PosName>");
                }
            }
        }
        return false;
    }

    private void savePosition(Player p, FileConfiguration config, String posName) {
        config.set("Position." + posName + ".X", p.getLocation().getX());
        config.set("Position." + posName + ".Y", p.getLocation().getY());
        config.set("Position." + posName + ".Z", p.getLocation().getZ());
        config.set("Position." + posName + ".World", p.getLocation().getWorld().getName());
        config.set("Position." + posName + ".Player", p.getName());
        config.set("Position." + posName + ".Public", false);
        config.set("Position." + posName + ".Created", System.currentTimeMillis());
        Essentials.getInstance().saveConfig();
        p.sendMessage(Essentials.pre + "§6Die Position §b" + posName + " §6wurde gespeichert");
    }

    private void loadPosition(Player p, FileConfiguration config, String posName) {
        if (config.contains("Position." + posName)) {
            String playerForPosition = config.getString("Position." + posName + ".Player");
            boolean isPublic = config.getBoolean("Position." + posName + ".Public", false);
            if (playerForPosition != null && (playerForPosition.equals(p.getName()) || isPublic)) {
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

                objective.getScore("§7 X: §a" + xValue).setScore(5);
                objective.getScore("§7 Y: §a" + yValue).setScore(4);
                objective.getScore("§7 Z: §a" + zValue).setScore(3);

                String worldName = config.getString("Position." + posName + ".World");
                String displayWorld = formatWorldName(worldName);
                objective.getScore("§7 World: §a" + displayWorld).setScore(2);

                if (isPublic) {
                    objective.getScore("§7 Owner: §e" + playerForPosition).setScore(1);
                }

                p.setScoreboard(scoreboard);
                p.sendMessage(Essentials.pre + "§6Position §b" + posName + " §6geladen");
            } else {
                p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung für diese Position!");
            }
        } else {
            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
        }
    }

    private void deletePosition(Player p, FileConfiguration config, String posName) {
        if (config.contains("Position." + posName)) {
            String playerForPosition = config.getString("Position." + posName + ".Player");
            if (playerForPosition != null && playerForPosition.equals(p.getName())) {
                config.set("Position." + posName, null);
                Essentials.getInstance().saveConfig();
                p.sendMessage(Essentials.pre + "§6Die Position §b" + posName + " §6wurde gelöscht");
            } else {
                p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung um diese Position zu löschen!");
            }
        } else {
            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
        }
    }

    private void updatePositionLocation(Player p, FileConfiguration config, String posName) {
        if (config.contains("Position." + posName)) {
            String playerForPosition = config.getString("Position." + posName + ".Player");
            if (playerForPosition != null && playerForPosition.equals(p.getName())) {
                config.set("Position." + posName + ".X", p.getLocation().getX());
                config.set("Position." + posName + ".Y", p.getLocation().getY());
                config.set("Position." + posName + ".Z", p.getLocation().getZ());
                config.set("Position." + posName + ".World", p.getLocation().getWorld().getName());
                Essentials.getInstance().saveConfig();
                p.sendMessage(Essentials.pre + "§6Die Position §b" + posName + " §6wurde aktualisiert");
            } else {
                p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung um diese Position zu modifizieren!");
            }
        } else {
            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
        }
    }

    private void updatePositionPublic(Player p, FileConfiguration config, String posName, String value) {
        if (config.contains("Position." + posName)) {
            String playerForPosition = config.getString("Position." + posName + ".Player");
            if (playerForPosition != null && playerForPosition.equals(p.getName())) {
                boolean isPublic = Boolean.parseBoolean(value);
                config.set("Position." + posName + ".Public", isPublic);
                Essentials.getInstance().saveConfig();
                p.sendMessage(Essentials.pre + "§6Die Position §b" + posName + " §6ist nun " + (isPublic ? "§aöffentlich" : "§cprivat") + "§6.");
            } else {
                p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung um diese Position zu modifizieren!");
            }
        } else {
            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
        }
    }

    private void renamePosition(Player p, FileConfiguration config, String oldName, String newName) {
        if (config.contains("Position." + oldName)) {
            String playerForPosition = config.getString("Position." + oldName + ".Player");
            if (playerForPosition != null && playerForPosition.equals(p.getName())) {
                if (config.contains("Position." + newName)) {
                    p.sendMessage(Essentials.pre + "§cEine Position mit dem Namen §b" + newName + " §cexistiert bereits!");
                    return;
                }

                // Copy all data to new position
                ConfigurationSection oldSection = config.getConfigurationSection("Position." + oldName);
                if (oldSection != null) {
                    config.createSection("Position." + newName, oldSection.getValues(true));
                    config.set("Position." + oldName, null);
                    Essentials.getInstance().saveConfig();
                    p.sendMessage(Essentials.pre + "§6Position §b" + oldName + " §6wurde zu §b" + newName + " §6umbenannt");
                }
            } else {
                p.sendMessage(Essentials.pre + "§cDu hast keine Berechtigung um diese Position zu modifizieren!");
            }
        } else {
            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
        }
    }

    private void listPositions(Player p, FileConfiguration config) {
        ConfigurationSection positionSection = config.getConfigurationSection("Position");
        if (positionSection == null || positionSection.getKeys(false).isEmpty()) {
            p.sendMessage(Essentials.pre + "§cKeine Positionen gefunden!");
            return;
        }

        List<String> ownPositions = new ArrayList<>();
        List<String> publicPositions = new ArrayList<>();

        for (String posName : positionSection.getKeys(false)) {
            String playerForPosition = config.getString("Position." + posName + ".Player");
            boolean isPublic = config.getBoolean("Position." + posName + ".Public", false);

            if (playerForPosition != null && playerForPosition.equals(p.getName())) {
                ownPositions.add(posName);
            } else if (isPublic) {
                publicPositions.add(posName);
            }
        }

        p.sendMessage("§8§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        p.sendMessage("§6§l           Deine Positionen");
        p.sendMessage("§8§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (ownPositions.isEmpty()) {
            p.sendMessage("§7   Keine eigenen Positionen vorhanden");
        } else {
            ownPositions.sort(String.CASE_INSENSITIVE_ORDER);
            for (String pos : ownPositions) {
                boolean isPublic = config.getBoolean("Position." + pos + ".Public", false);
                String worldName = config.getString("Position." + pos + ".World");
                String displayWorld = formatWorldName(worldName);

                Component message = Component.text("§8▸ §b" + pos + " §8(§7" + displayWorld + "§8) " + (isPublic ? "§a[Öffentlich]" : "§c[Privat]"))
                        .hoverEvent(HoverEvent.showText(Component.text("§7Klicke zum Laden\n§8Rechtsklick zum Löschen")))
                        .clickEvent(ClickEvent.runCommand("/pos load " + pos));
                p.sendMessage(message);
            }
        }

        if (!publicPositions.isEmpty()) {
            p.sendMessage("");
            p.sendMessage("§6§l         Öffentliche Positionen");
            p.sendMessage("§8§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            publicPositions.sort(String.CASE_INSENSITIVE_ORDER);
            for (String pos : publicPositions) {
                String owner = config.getString("Position." + pos + ".Player");
                String worldName = config.getString("Position." + pos + ".World");
                String displayWorld = formatWorldName(worldName);

                Component message = Component.text("§8▸ §e" + pos + " §8(§7" + displayWorld + "§8) §8von §7" + owner)
                        .hoverEvent(HoverEvent.showText(Component.text("§7Klicke zum Laden")))
                        .clickEvent(ClickEvent.runCommand("/pos load " + pos));
                p.sendMessage(message);
            }
        }

        p.sendMessage("§8§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        p.sendMessage("§7Verwende §b/pos gui §7für eine grafische Übersicht");
    }

    private void openPositionGUI(Player p, FileConfiguration config) {
        ConfigurationSection positionSection = config.getConfigurationSection("Position");
        if (positionSection == null || positionSection.getKeys(false).isEmpty()) {
            p.sendMessage(Essentials.pre + "§cKeine Positionen gefunden!");
            return;
        }

        Inventory gui = Bukkit.createInventory(null, 54, "§6§lPositions-Manager");

        List<String> ownPositions = new ArrayList<>();
        List<String> publicPositions = new ArrayList<>();

        for (String posName : positionSection.getKeys(false)) {
            String playerForPosition = config.getString("Position." + posName + ".Player");
            boolean isPublic = config.getBoolean("Position." + posName + ".Public", false);

            if (playerForPosition != null && playerForPosition.equals(p.getName())) {
                ownPositions.add(posName);
            } else if (isPublic) {
                publicPositions.add(posName);
            }
        }

        int slot = 0;

        // Own positions
        for (String posName : ownPositions) {
            if (slot >= 45) break; // Leave space for public positions

            ItemStack item = new ItemStack(Material.COMPASS);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§b§l" + posName);

            List<String> lore = new ArrayList<>();
            lore.add("§8§m─────────────────────");
            lore.add("§7World: §a" + formatWorldName(config.getString("Position." + posName + ".World")));
            lore.add("§7X: §f" + (int)config.getDouble("Position." + posName + ".X"));
            lore.add("§7Y: §f" + (int)config.getDouble("Position." + posName + ".Y"));
            lore.add("§7Z: §f" + (int)config.getDouble("Position." + posName + ".Z"));
            lore.add("");
            boolean isPublic = config.getBoolean("Position." + posName + ".Public", false);
            lore.add("§7Status: " + (isPublic ? "§aÖffentlich" : "§cPrivat"));

            long created = config.getLong("Position." + posName + ".Created", 0);
            if (created > 0) {
                Date date = new Date(created);
                lore.add("§7Erstellt: §f" + date.toString().substring(0, 10));
            }

            lore.add("§8§m─────────────────────");
            lore.add("§e► Linksklick zum Laden");
            lore.add("§c► Rechtsklick zum Löschen");
            lore.add("§6► Shift+Klick zum Bearbeiten");

            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.setItem(slot++, item);
        }

        // Separator
        if (!publicPositions.isEmpty() && slot < 45) {
            ItemStack separator = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
            ItemMeta sepMeta = separator.getItemMeta();
            sepMeta.setDisplayName("§6§lÖffentliche Positionen");
            separator.setItemMeta(sepMeta);

            for (int i = 45; i < 54; i++) {
                gui.setItem(i, separator);
            }

            slot = 45;
        }

        // Public positions
        for (String posName : publicPositions) {
            if (slot >= 54) break;

            ItemStack item = new ItemStack(Material.ENDER_PEARL);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e§l" + posName);

            List<String> lore = new ArrayList<>();
            lore.add("§8§m─────────────────────");
            lore.add("§7Besitzer: §b" + config.getString("Position." + posName + ".Player"));
            lore.add("§7World: §a" + formatWorldName(config.getString("Position." + posName + ".World")));
            lore.add("§7X: §f" + (int)config.getDouble("Position." + posName + ".X"));
            lore.add("§7Y: §f" + (int)config.getDouble("Position." + posName + ".Y"));
            lore.add("§7Z: §f" + (int)config.getDouble("Position." + posName + ".Z"));
            lore.add("§8§m─────────────────────");
            lore.add("§e► Klicke zum Laden");

            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.setItem(slot++, item);
        }

        p.openInventory(gui);
    }

    private String formatWorldName(String worldName) {
        if (worldName == null) return "Overworld";

        if (worldName.contains("world_nether")) return "Nether";
        if (worldName.contains("world_the_end")) return "End";
        if (worldName.contains("world_")) {
            String[] parts = worldName.split("_");
            if (parts.length > 1) {
                String dimension = parts[1];
                return dimension.substring(0,1).toUpperCase() + dimension.substring(1);
            }
        }
        if (worldName.equals("world")) return "Overworld";

        return worldName;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (!(sender instanceof Player)) {
            return completions;
        }

        Player player = (Player) sender;

        try {
            FileConfiguration config = Essentials.getInstance().getConfig();

            if (args.length == 1) {
                completions.addAll(Arrays.asList("save", "load", "unload", "delete", "modify", "list", "gui"));
                completions.removeIf(s -> !s.toLowerCase().startsWith(args[0].toLowerCase()));
                Collections.sort(completions);
                return completions;
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("load") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("modify")) {
                    ConfigurationSection positionSection = config.getConfigurationSection("Position");
                    if (positionSection != null) {
                        Set<String> positions = positionSection.getKeys(false);
                        if (positions != null) {
                            for (String posName : positions) {
                                String playerForPosition = config.getString("Position." + posName + ".Player");
                                boolean isPublic = config.getBoolean("Position." + posName + ".Public", false);
                                if ((playerForPosition != null && playerForPosition.equals(player.getName())) || isPublic) {
                                    completions.add(posName);
                                }
                            }
                        }
                    }
                    completions.removeIf(s -> !s.toLowerCase().startsWith(args[1].toLowerCase()));
                    Collections.sort(completions);
                } else if (args[0].equalsIgnoreCase("save")) {
                    completions.add("<PosName>");
                }
            } else if (args.length == 3 && args[0].equalsIgnoreCase("modify")) {
                completions.addAll(Arrays.asList("public", "location", "rename"));
                completions.removeIf(s -> !s.toLowerCase().startsWith(args[2].toLowerCase()));
            } else if (args.length == 4 && args[0].equalsIgnoreCase("modify")) {
                if (args[2].equalsIgnoreCase("public")) {
                    completions.addAll(Arrays.asList("true", "false"));
                    completions.removeIf(s -> !s.toLowerCase().startsWith(args[3].toLowerCase()));
                } else if (args[2].equalsIgnoreCase("rename")) {
                    completions.add("<NewName>");
                }
            }
        } catch (Exception e) {
            Essentials.getInstance().getLogger().warning("Error in tab completion for pos command: " + e.getMessage());
            return new ArrayList<>();
        }

        return completions;
    }
}