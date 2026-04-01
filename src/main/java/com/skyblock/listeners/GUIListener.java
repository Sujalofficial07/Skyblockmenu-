package com.skyblock.listeners;

import com.skyblock.menu.*;
import com.skyblock.utils.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getView().getTitle().contains("SkyBlock") ||
                e.getView().getTitle().contains("Stats")) {
            e.setCancelled(true);
        }

        if (e.getCurrentItem() == null) return;

        String menu = NBTUtil.getString(e.getCurrentItem(), "menu");
        if (menu == null) return;

        Player p = (Player) e.getWhoClicked();

        switch (menu) {
            case "skills" -> SkillsMenu.open(p);
            case "collections" -> CollectionsMenu.open(p);
            case "stats" -> StatsMenu.open(p);
            case "profile" -> MainMenu.open(p);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getView().getTitle().contains("SkyBlock")) {
            e.setCancelled(true);
        }
    }
}
