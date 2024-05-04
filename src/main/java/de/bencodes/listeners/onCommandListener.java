package de.bencodes.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class onCommandListener implements Listener {
    public onCommandListener() {
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (p.hasMetadata("cmdspy")) {
            p.sendMessage("§6" + e.getPlayer().getName() + " §c -> §7 " + e.getMessage());
        }
    }
}
