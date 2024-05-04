package de.bencodes.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoinListener implements Listener {
    public onJoinListener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
//        if (p.hasPermission("essentials.admin")) {
//            e.setJoinMessage("§3Der Admin§4 " + e.getPlayer().getName() + " §3ist gejoined");
//        } else if (p.hasPermission("essentials.jean")) {
//            e.setJoinMessage("§b§lJeanieBeanie §r§bist gejoined");
//        } else {
            e.setJoinMessage("§a[§f+§a] §2" + e.getPlayer().getName());
//        }

    }
}
