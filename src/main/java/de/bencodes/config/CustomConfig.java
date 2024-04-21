package de.bencodes.config;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfig {
    private static File file;
    private static FileConfiguration customFile;

    public CustomConfig() {
    }

    public static void setup() {
        if(Bukkit.getServer().getPluginManager().getPlugin("EssentialsB_1.0") == null) {
            return;
        }
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("EssentialsB_1.0")).getDataFolder(), "posconfig.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var1) {
                System.out.println("Datei konnte nicht erstellt werden");
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException var1) {
            System.out.println("Datei konnte nicht gespeichert werden");
        }

    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
