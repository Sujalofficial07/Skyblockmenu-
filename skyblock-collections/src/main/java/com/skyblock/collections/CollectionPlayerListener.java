package com.skyblock.collections;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CollectionPlayerListener implements Listener {

    private final CollectionManager collectionManager;

    public CollectionPlayerListener(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        collectionManager.loadPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        collectionManager.unloadPlayer(event.getPlayer().getUniqueId());
    }
}
