package com.skyblock.menu;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager {

    private final Plugin plugin;
    private final Map<UUID, SkyBlockMenu> openMenus = new HashMap<>();

    public MenuManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void openMenu(Player player, SkyBlockMenu menu) {
        closeMenu(player);
        openMenus.put(player.getUniqueId(), menu);
        menu.open(player);
    }

    public void closeMenu(Player player) {
        SkyBlockMenu existing = openMenus.remove(player.getUniqueId());
        if (existing != null) {
            existing.onClose(player);
        }
    }

    public SkyBlockMenu getOpenMenu(Player player) {
        return openMenus.get(player.getUniqueId());
    }

    public boolean hasOpenMenu(Player player) {
        return openMenus.containsKey(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        openMenus.remove(player.getUniqueId());
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
