//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.bencodes;

import de.bencodes.commands.Command_ChatClear;
import de.bencodes.commands.Command_Commandspy;
import de.bencodes.commands.Command_Day;
import de.bencodes.commands.Command_Fly;
import de.bencodes.commands.Command_Gamemode;
import de.bencodes.commands.Command_Give;
import de.bencodes.commands.Command_Invsee;
import de.bencodes.commands.Command_Kopf;
import de.bencodes.commands.Command_Night;
import de.bencodes.commands.Command_Pos;
import de.bencodes.commands.Command_Rename;
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
