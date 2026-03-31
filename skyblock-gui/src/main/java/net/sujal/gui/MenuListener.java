// Path: /skyblock-gui/src/main/java/net/sujal/gui/MenuListener.java
package net.sujal.gui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        // If the inventory holder is our custom menu type, cancel the click to prevent stealing
        if (event.getInventory().getHolder() instanceof SkyblockMenu) {
            event.setCancelled(true);
            
            SkyblockMenu menu = (SkyblockMenu) event.getInventory().getHolder();
            menu.handleClick(event);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof SkyblockMenu) {
            event.setCancelled(true);
        }
    }
}
