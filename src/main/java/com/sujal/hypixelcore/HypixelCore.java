package com.sujal.hypixelcore;

import com.sujal.hypixelcore.commands.MenuCommand;
import com.sujal.hypixelcore.data.PlayerManager;
import com.sujal.hypixelcore.listeners.MenuListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HypixelCore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("HypixelCore plugin has been enabled!");
        
        getCommand("menu").setExecutor(new MenuCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(this, this); // Register Join/Quit
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerManager.loadPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerManager.unloadPlayer(event.getPlayer());
    }
}
