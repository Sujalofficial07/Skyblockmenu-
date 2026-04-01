package com.skyblock.listeners;

import com.skyblock.menu.MenuManager;
import com.skyblock.utils.NBTUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;

        String menu = NBTUtil.getString(e.getCurrentItem(), "menu");
        if (menu == null) return;

        e.setCancelled(true);

        if (menu.equals("skills")) {
            e.getWhoClicked().sendMessage("Skills Menu");
        }
    }
}
