// Path: /skyblock-core/src/main/java/net/sujal/core/SkyblockPlugin.java
package net.sujal.core;

import net.sujal.api.profile.ProfileManager;
import net.sujal.core.service.SkyblockServiceManager;
import net.sujal.data.MongoConnection;
import net.sujal.data.listener.ProfileListener;
import net.sujal.data.profile.MongoProfileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyblockPlugin extends JavaPlugin {

    private static SkyblockPlugin instance;
    private SkyblockServiceManager serviceManager;

    @Override
    public void onEnable() {
        instance = this;
        this.serviceManager = new SkyblockServiceManager();
        
        saveDefaultConfig();
        
        getLogger().info("Initializing Skyblock Framework...");

        // 1. Initialize Database
        String uri = getConfig().getString("database.mongo-uri", "mongodb://localhost:27017");
        String dbName = getConfig().getString("database.name", "skyblock");
        MongoConnection mongoConnection = new MongoConnection(uri, dbName);
        serviceManager.registerService(MongoConnection.class, mongoConnection);

        // 2. Initialize Profile Manager
        MongoProfileManager profileManager = new MongoProfileManager(mongoConnection);
        serviceManager.registerService(ProfileManager.class, profileManager);

        // 3. Register Listeners
        getServer().getPluginManager().registerEvents(new ProfileListener(profileManager), this);

        getLogger().info("Skyblock Framework initialized successfully.");
    }

    @Override
    public void onDisable() {
        if (this.serviceManager != null) {
            this.serviceManager.disableAll();
        }
    }

    public static SkyblockPlugin getInstance() {
        return instance;
    }

    public SkyblockServiceManager getServiceManager() {
        return serviceManager;
    }
}
