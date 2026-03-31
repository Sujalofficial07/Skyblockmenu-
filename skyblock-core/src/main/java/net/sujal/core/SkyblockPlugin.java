// Path: /skyblock-core/src/main/java/net/sujal/core/SkyblockPlugin.java
package net.sujal.core;

import net.sujal.core.service.SkyblockServiceManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * The main entry point for the Skyblock Core plugin.
 */
public class SkyblockPlugin extends JavaPlugin {

    private static SkyblockPlugin instance;
    private SkyblockServiceManager serviceManager;

    @Override
    public void onEnable() {
        instance = this;
        this.serviceManager = new SkyblockServiceManager();
        
        getLogger().info("Initializing Skyblock Core Framework...");

        // TODO: Register Core Services here in subsequent steps
        // e.g., serviceManager.registerService(ProfileManager.class, new MongoProfileManager(this));

        getLogger().info("Skyblock Core Framework initialized successfully.");
    }

    @Override
    public void onDisable() {
        if (this.serviceManager != null) {
            getLogger().info("Shutting down services...");
            this.serviceManager.disableAll();
        }
        getLogger().info("Skyblock Core Framework disabled.");
    }

    public static SkyblockPlugin getInstance() {
        return instance;
    }

    public SkyblockServiceManager getServiceManager() {
        return serviceManager;
    }
}
