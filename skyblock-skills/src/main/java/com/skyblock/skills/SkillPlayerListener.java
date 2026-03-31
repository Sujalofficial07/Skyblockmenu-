package com.skyblock.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SkillPlayerListener implements Listener {

    private final SkillManager skillManager;

    public SkillPlayerListener(SkillManager skillManager) {
        this.skillManager = skillManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        skillManager.loadPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        skillManager.unloadPlayer(event.getPlayer().getUniqueId());
    }
}
