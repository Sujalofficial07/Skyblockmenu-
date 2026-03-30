package com.sujal.hypixelcore;

import com.sujal.hypixelcore.commands.MenuCommand;
import com.sujal.hypixelcore.data.PlayerManager;
import com.sujal.hypixelcore.listeners.MenuListener;
import com.sujal.hypixelcore.menu.SkyblockMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HypixelCore extends JavaPlugin implements Listener {

    private static HypixelCore instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("HypixelCore (Ultimate Edition) Enabled!");
        
        // Register Commands & Listeners
        getCommand("menu").setExecutor(new MenuCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    public static HypixelCore getInstance() {
        return instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerManager.loadPlayer(event.getPlayer());
        // Automatically give the unbreakable menu star on join
        SkyblockMenu.giveMenuStar(event.getPlayer());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        // Ensure they get the star back if they die
        SkyblockMenu.giveMenuStar(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerManager.unloadPlayer(event.getPlayer());
    }
}
