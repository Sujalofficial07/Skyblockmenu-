package com.skyblock.menu;

import com.skyblock.utils.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MenuListener implements Listener {

    private final MenuManager menuManager;
    private final Plugin plugin;

    public MenuListener(MenuManager menuManager, Plugin plugin) {
        this.menuManager = menuManager;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!menuManager.hasOpenMenu(player)) return;
        if (event.getClickedInventory() == null) return;

        SkyBlockMenu menu = menuManager.getOpenMenu(player);
        if (menu == null) return;

        if (event.getClickedInventory().equals(menu.getInventory())) {
            event.setCancelled(true);
            menu.handleClick(event);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (menuManager.hasOpenMenu(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        menuManager.removePlayer(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) return;

        boolean isSkyBlockMenu = NBTUtil.getBoolean(plugin, item, "skyblock_menu");
        if (!isSkyBlockMenu) return;

        event.setCancelled(true);
        menuManager.openMenu(player, new MainSkyBlockMenu(menuManager));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        menuManager.removePlayer(event.getPlayer());
    }
}
