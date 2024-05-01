//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.bencodes;

import de.bencodes.commands.*;
import de.bencodes.config.CustomConfig;
import de.bencodes.listeners.onCommandListener;
import de.bencodes.listeners.onJoinListener;
import de.bencodes.listeners.onKickListener;
import de.bencodes.listeners.onQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Essentials extends JavaPlugin {
    public static String pre = "§8[§bEssentialsB§8] ";
    private static Essentials plugin;

    public Essentials() {
    }

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "EssentialsB aktiviert!");
        plugin = this;
        this.getCommand("commandspy").setExecutor(new Command_Commandspy());
        this.getCommand("clearchat").setExecutor(new Command_ChatClear());
        this.getCommand("gm").setExecutor(new Command_Gamemode());
        this.getCommand("kopf").setExecutor(new Command_Kopf());
        this.getCommand("invsee").setExecutor(new Command_Invsee());
        this.getCommand("pos").setExecutor(new Command_Pos());
        this.getCommand("day").setExecutor(new Command_Day());
        this.getCommand("night").setExecutor(new Command_Night());
        this.getCommand("i").setExecutor(new Command_Give());
        this.getCommand("fly").setExecutor(new Command_Fly());
        this.getCommand("rename").setExecutor(new Command_Rename());
        this.getCommand("enderchest").setExecutor(new Command_Enderchest());
        this.getCommand("vanish").setExecutor(new Command_Vanish());
        this.getCommand("heal").setExecutor(new Command_Heal());
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new onCommandListener(), this);
        pm.registerEvents(new onJoinListener(), this);
        pm.registerEvents(new onQuitListener(), this);
        pm.registerEvents(new onKickListener(), this);
        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        CustomConfig.setup();
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();
    }

    public void onDisable() {
    }

    public static Essentials getInstance() {
        return plugin;
    }
}
