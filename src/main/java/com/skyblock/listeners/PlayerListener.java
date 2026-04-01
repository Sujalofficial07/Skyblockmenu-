package com.skyblock.listeners;

import com.skyblock.stats.StatsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        StatsManager.init(e.getPlayer().getUniqueId());
    }
}
