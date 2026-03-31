package com.skyblock.core;

import com.skyblock.collections.CollectionManager;
import com.skyblock.menu.MenuItemBuilder;
import com.skyblock.skills.SkillManager;
import com.skyblock.utils.NBTUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    public PlayerJoinListener(Plugin plugin, SkillManager skillManager,
                               CollectionManager collectionManager) {
        this.plugin = plugin;
        this.skillManager = skillManager;
        this.collectionManager = collectionManager;
        this.menuItemBuilder = new MenuItemBuilder(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        skillManager.loadPlayer(player);
        collectionManager.loadPlayer(player);

        if (plugin.getConfig().getBoolean("settings.give-menu-item-on-join", true)) {
            int slot = plugin.getConfig().getInt("settings.menu-item-slot", 8);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (!player.isOnline()) return;
                if (!hasMenuItemAnywhere(player)) {
                    player.getInventory().setItem(slot, menuItemBuilder.buildSkyBlockMenuItem());
                }
            }, 5L);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        skillManager.unloadPlayer(player.getUniqueId());
        collectionManager.unloadPlayer(player.getUniqueId());
    }

    private boolean hasMenuItemAnywhere(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && NBTUtil.getBoolean(plugin, item, "skyblock_menu")) {
                return true;
            }
        }
        return false;
    }
}
