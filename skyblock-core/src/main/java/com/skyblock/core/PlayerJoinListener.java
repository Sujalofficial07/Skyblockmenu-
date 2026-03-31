package com.skyblock.core;

import com.skyblock.collections.CollectionManager;
import com.skyblock.menu.MenuItemBuilder;
import com.skyblock.skills.SkillManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerJoinListener implements Listener {

    private final Plugin plugin;
    private final SkillManager skillManager;
    private final CollectionManager collectionManager;
    private final MenuItemBuilder menuItemBuilder;

    public PlayerJoinListener(Plugin plugin, SkillManager skillManager, CollectionManager collectionManager) {
        this.plugin = plugin;
        this.skillManager = skillManager;
        this.collectionManager = collectionManager;
        this.menuItemBuilder = new MenuItemBuilder(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        skillManager.loadPlayer(player);
        collectionManager.loadPlayer(player);
        giveSkyBlockMenuItem(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        skillManager.unloadPlayer(player.getUniqueId());
        collectionManager.unloadPlayer(player.getUniqueId());
    }

    private void giveSkyBlockMenuItem(Player player) {
        ItemStack menuItem = menuItemBuilder.buildSkyBlockMenuItem();
        boolean hasItem = false;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && com.skyblock.utils.NBTUtil.getBoolean(plugin, item, "skyblock_menu")) {
                hasItem = true;
                break;
            }
        }
        if (!hasItem) {
            player.getInventory().setItem(8, menuItem);
        }
    }
}
