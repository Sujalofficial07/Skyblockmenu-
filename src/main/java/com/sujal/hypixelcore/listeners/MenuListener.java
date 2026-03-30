package com.sujal.hypixelcore.listeners;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if the title matches our SkyBlock Menu
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        
        if (title.equals("SkyBlock Menu")) {
            event.setCancelled(true); // Prevent players from taking items

            if (event.getCurrentItem() == null) return;
            
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            // Handle Clicks based on slot numbers
            switch (slot) {
                case 13:
                    player.sendMessage("§eOpening Skills Menu...");
                    // Add your open skills logic here
                    break;
                case 31:
                    player.sendMessage("§eOpening Personal Bank...");
                    // Add personal bank logic here
                    break;
                case 32:
                    player.sendMessage("§eOpening Fast Travel...");
                    // Add fast travel logic here
                    break;
                // Add more cases for other slots as needed
            }
        }
    }
}
