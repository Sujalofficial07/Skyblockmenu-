package com.sujal.hypixelcore.listeners;

import com.sujal.hypixelcore.HypixelCore;
import com.sujal.hypixelcore.menu.SkyblockMenu;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MenuListener implements Listener {

    private final NamespacedKey MENU_KEY = new NamespacedKey(HypixelCore.getInstance(), "sb_menu_star");

    private boolean isMenuStar(ItemStack item) {
        if (item == null || item.getItemMeta() == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(MENU_KEY, PersistentDataType.BYTE);
    }

    // --- 1. INTERACTION LOGIC (Open Menu) ---
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        if (isMenuStar(event.getItem())) {
            event.setCancelled(true);
            SkyblockMenu.openMenu(event.getPlayer());
        }
    }

    // --- 2. GUI INTERACTION (Handling the Custom Inventories) ---
    @EventHandler
    public void onGUIClick(InventoryClickEvent event) {
        String title = PlainTextComponentSerializer.plainText().serialize(event.getView().title());
        
        if (title.equals("SkyBlock Menu") || title.equals("Your Skills")) {
            event.setCancelled(true); // Stop all theft from our menus
            
            if (event.getCurrentItem() == null) return;
            Player player = (Player) event.getWhoClicked();
            
            // Only play sound if they click a real item, not empty glass
            if (event.getCurrentItem().getType() != org.bukkit.Material.BLACK_STAINED_GLASS_PANE) {
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            }
            
            // Further logic for skills, wardrobe etc. goes here (refer to previous snippets)
        }
    }

    // --- 3. BULLETPROOF ITEM SECURITY (Stopping all movement) ---

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Prevent clicking directly on the star in ANY inventory
        if (isMenuStar(event.getCurrentItem()) || isMenuStar(event.getCursor())) {
            event.setCancelled(true);
            return;
        }

        // Prevent using number keys (1-9) to swap the star out of its slot
        if (event.getClick() == ClickType.NUMBER_KEY) {
            ItemStack hotbarItem = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
            if (isMenuStar(hotbarItem)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        // Prevent middle-click dragging the star
        if (isMenuStar(event.getOldCursor()) || isMenuStar(event.getCursor())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        // Prevent 'Q' drop
        if (isMenuStar(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        // Prevent 'F' offhand swap
        if (isMenuStar(event.getMainHandItem()) || isMenuStar(event.getOffHandItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // Remove the star from dropping on the ground when the player dies
        event.getDrops().removeIf(this::isMenuStar);
    }
}
