//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.bencodes.commands;

import de.bencodes.Essentials;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Command_Pos implements CommandExecutor, TabCompleter {
    public Command_Pos() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("essentials.pos")) {
                FileConfiguration config = Essentials.getInstance().getConfig();

                if (args.length == 0) {
                    p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load> <PosName>");
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")) {
                        p.sendMessage(Essentials.pre + "§6Positionen: " + Objects.requireNonNull(config.getConfigurationSection("Position")).getKeys(false));
                    } else {
                        p.sendMessage(Essentials.pre + "§cBenutze /pos <save | load> <PosName>");
                    }
                } else if (args.length == 2) {
                    String posName = args[1];
                    if (args[0].equalsIgnoreCase("save")) {
                         config.set("Position." + posName + ".X", p.getLocation().getX());
                        config.set("Position." + posName + ".Y", p.getLocation().getY());
                        config.set("Position." + posName + ".Z", p.getLocation().getZ());
                        Essentials.getInstance().saveConfig();
                        p.sendMessage(Essentials.pre + "§6Die Position wurde gespeichert");
                    } else if (args[0].equalsIgnoreCase("load")) {
                        if (config.contains("Position." + posName)) {
                            double x = config.getDouble("Position." + posName + ".X");
                            int xValue = (int)x;
                            double y = config.getDouble("Position." + posName + ".Y");
                            int yValue = (int)y;
                            double z = config.getDouble("Position." + posName + ".Z");
                            int zValue = (int)z;
                            Location location = new Location(p.getWorld(), (double)xValue, (double)yValue, (double)zValue);
                            p.sendMessage(Essentials.pre + "§6Die Position lautet: X: " + location.getX() + "  Y: " + location.getY() + "  Z: " + location.getZ());
                        } else {
                            p.sendMessage(Essentials.pre + "§cDiese Position existiert nicht!");
                        }
                    } else {
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
