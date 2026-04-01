package com.skyblock.listeners;

import com.skyblock.menu.*;
import com.skyblock.utils.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;

        String menu = NBTUtil.getString(e.getCurrentItem(),"menu");
        if(menu == null) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();

        switch (menu) {
            case "skills" -> SkillsMenu.open(p);
            case "collections" -> CollectionsMenu.open(p);
            case "stats" -> StatsMenu.open(p);
            case "profile" -> MainMenu.open(p);
        }
    }
}
