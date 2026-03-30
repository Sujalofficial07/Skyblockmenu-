package com.sujal.hypixelcore;

import com.sujal.hypixelcore.commands.MenuCommand;
import com.sujal.hypixelcore.listeners.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HypixelCore extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("HypixelCore plugin has been enabled!");
        
        // Registering Commands & Listeners
        getCommand("menu").setExecutor(new MenuCommand());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("HypixelCore plugin has been disabled!");
    }
}
