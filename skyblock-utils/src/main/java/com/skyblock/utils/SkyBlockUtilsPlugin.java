package com.skyblock.utils;

import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlockUtilsPlugin extends JavaPlugin {

    private static SkyBlockUtilsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("SkyBlockUtils module loaded.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkyBlockUtils module unloaded.");
    }

    public static SkyBlockUtilsPlugin getInstance() {
        return instance;
    }
}
