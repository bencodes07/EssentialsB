package de.bencodes.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

public class onKickListener implements Listener {
    public onKickListener() {
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        e.setLeaveMessage("§c" + e.getPlayer().getName() + " wurde gekickt");
    }
}
