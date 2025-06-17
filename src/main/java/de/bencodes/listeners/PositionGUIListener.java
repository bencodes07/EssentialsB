package de.bencodes.listeners;

import de.bencodes.Essentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PositionGUIListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("§6§lPositions-Manager")) {
            e.setCancelled(true);

            if (!(e.getWhoClicked() instanceof Player)) return;
            Player p = (Player) e.getWhoClicked();

            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            ItemMeta meta = clickedItem.getItemMeta();
            if (meta == null || !meta.hasDisplayName()) return;

            String displayName = ChatColor.stripColor(meta.getDisplayName());

            // Skip separator items
            if (displayName.equals("Öffentliche Positionen")) return;

            if (e.isLeftClick() && !e.isShiftClick()) {
                // Load position
                p.closeInventory();
                p.performCommand("pos load " + displayName);
            } else if (e.isRightClick() && !e.isShiftClick()) {
                // Delete position (only for own positions)
                if (clickedItem.getType().toString().equals("COMPASS")) {
                    p.closeInventory();
                    p.performCommand("pos delete " + displayName);
                }
            } else if (e.isShiftClick()) {
                // Edit position (only for own positions)
                if (clickedItem.getType().toString().equals("COMPASS")) {
                    p.closeInventory();
                    p.sendMessage("§8§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    p.sendMessage("§6§l       Position bearbeiten: §b" + displayName);
                    p.sendMessage("§8§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                    p.sendMessage("§7▸ §e/pos modify " + displayName + " location §8- §7Position aktualisieren");
                    p.sendMessage("§7▸ §e/pos modify " + displayName + " public true/false §8- §7Öffentlich machen");
                    p.sendMessage("§7▸ §e/pos modify " + displayName + " rename <name> §8- §7Umbenennen");
                    p.sendMessage("§7▸ §c/pos delete " + displayName + " §8- §7Löschen");
                    p.sendMessage("§8§l━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                }
            }
        }
    }
}