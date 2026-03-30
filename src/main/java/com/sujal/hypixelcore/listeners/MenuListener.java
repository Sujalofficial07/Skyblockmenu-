package com.sujal.hypixelcore.listeners;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Plain text title nikalna (Paper API safe)
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        
        if (title.equals("SkyBlock Menu")) {
            event.setCancelled(true); // Prevent item moving

            if (event.getCurrentItem() == null) return;
            
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            // Click Sound Play (Like Hypixel)
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            // Execute actual features based on exact slot
            switch (slot) {
                case 13: // Diamond Sword
                    player.sendMessage("§a[Features] §eOpening Skills Menu...");
                    // player.openInventory(SkillsMenu.getInventory(player));
                    break;
                case 19: // Painting
                    player.sendMessage("§a[Features] §eOpening Collections...");
                    break;
                case 20: // Book
                    player.sendMessage("§a[Features] §eOpening Recipe Book...");
                    break;
                case 21: // Emerald
                    player.sendMessage("§a[Features] §eOpening Trades...");
                    break;
                case 22: // Writable Book
                    player.sendMessage("§a[Features] §eOpening Quest Log...");
                    break;
                case 23: // Clock
                    player.sendMessage("§a[Features] §eOpening Calendar...");
                    break;
                case 24: // Ender Chest
                    player.sendMessage("§a[Features] §eOpening Storage...");
                    player.closeInventory();
                    player.openInventory(player.getEnderChest()); // Workable Storage!
                    break;
                case 25: // Bone
                    player.sendMessage("§a[Features] §eOpening Pets Menu...");
                    break;
                case 30: // Leather Chestplate
                    player.sendMessage("§a[Features] §eOpening Wardrobe...");
                    break;
                case 31: // Gold Block
                    player.sendMessage("§a[Features] §eAccessing Personal Bank...");
                    break;
                case 32: // Map
                    player.sendMessage("§a[Features] §eOpening Fast Travel...");
                    break;
                case 48: // Name Tag
                    player.sendMessage("§a[Features] §eOpening Profile Management...");
                    break;
                case 49: // Nether Star
                    player.sendMessage("§a[Features] §eViewing SkyBlock Level & Stats...");
                    break;
                case 50: // Redstone Torch
                    player.sendMessage("§a[Features] §eOpening Settings...");
                    break;
            }
        }
    }
}
