package de.bencodes.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onQuitListener implements Listener {
    public onQuitListener() {
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("essentials.admin")) {
            e.setQuitMessage("§3Der Admin§4 " + e.getPlayer().getName() + " §3 ist offline...");
        } else if (p.hasPermission("essentials.jean")) {
            e.setQuitMessage("§b§lJeanieBeanie §r§bist offline...");
        } else {
            e.setQuitMessage("§c[§f-§c] §2" + e.getPlayer().getName());
        }

    }
}
