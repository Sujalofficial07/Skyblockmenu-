package com.sujal.hypixelcore.listeners;

import com.sujal.hypixelcore.menu.SkillsMenu;
import com.sujal.hypixelcore.menu.SkyblockMenu;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        
        // Handle Main Menu
        if (title.equals("SkyBlock Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            if (event.getSlot() == 13) {
                SkillsMenu.openMenu(player); // Diamond sword click = Skills menu open!
            }
            // (Baaki cases wahi purane rahenge jo pehle the)
        }
        
        // Handle Skills Menu
        if (title.equals("Your Skills")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            Player player = (Player) event.getWhoClicked();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);

            // Back Arrow Button
            if (event.getSlot() == 49) {
                SkyblockMenu.openMenu(player);
            }
        }
    }
}
