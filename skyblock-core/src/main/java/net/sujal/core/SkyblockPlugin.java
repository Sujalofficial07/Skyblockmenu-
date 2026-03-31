// Path: /skyblock-core/src/main/java/net/sujal/core/SkyblockPlugin.java
package net.sujal.core;

import net.sujal.api.profile.ProfileManager;
import net.sujal.core.service.SkyblockServiceManager;
import net.sujal.data.MongoConnection;
import net.sujal.data.listener.ProfileListener;
import net.sujal.data.profile.MongoProfileManager;
import net.sujal.gui.MenuListener;
import net.sujal.items.util.ItemKeys;
import net.sujal.mobs.listener.CombatListener;
import net.sujal.mobs.listener.FerocityListener;
import net.sujal.skills.engine.ManaManager;
import net.sujal.skills.engine.StatManager;
import net.sujal.skills.listener.AntiExploitListener;
import net.sujal.skills.listener.FortuneListener;
import net.sujal.skills.listener.StatUpdateListener;
import net.sujal.skills.task.ActionBarTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyblockPlugin extends JavaPlugin {

    private static SkyblockPlugin instance;
    private SkyblockServiceManager serviceManager;

    @Override
    public void onEnable() {
        instance = this;
        this.serviceManager = new SkyblockServiceManager();
        
        // Generate default config.yml if it doesn't exist
        saveDefaultConfig();
        
        getLogger().info("Starting Skyblock Framework initialization...");

        // 1. Initialize Item Keys (For NBT/PDC System)
        ItemKeys.init(this);

        // 2. Initialize Database Services
        String uri = getConfig().getString("database.mongo-uri", "mongodb://localhost:27017");
        String dbName = getConfig().getString("database.name", "skyblock");
        
        MongoConnection mongoConnection = new MongoConnection(uri, dbName);
        serviceManager.registerService(MongoConnection.class, mongoConnection);

        // 3. Initialize Profile Manager (User Data)
        MongoProfileManager profileManager = new MongoProfileManager(mongoConnection);
        serviceManager.registerService(ProfileManager.class, profileManager);

        // 4. Initialize Core RPG Engines (Stats & Mana)
        StatManager statManager = new StatManager(profileManager);
        serviceManager.registerService(StatManager.class, statManager);
        
        ManaManager manaManager = new ManaManager(statManager);
        serviceManager.registerService(ManaManager.class, manaManager);

        // 5. Register All Event Listeners
        registerListeners(profileManager, statManager, manaManager);

        // 6. Start Background Tasks (Action Bar & Mana Regen)
        new ActionBarTask(this, statManager, manaManager);

        getLogger().info("Skyblock Framework loaded successfully! 100% Ready.");
    }

    private void registerListeners(MongoProfileManager profileManager, StatManager statManager, ManaManager manaManager) {
        PluginManager pm = Bukkit.getPluginManager();

        // Data Listeners
        pm.registerEvents(new ProfileListener(profileManager), this);

        // GUI Listeners
        pm.registerEvents(new MenuListener(), this);

        // Skill & Stat Listeners
        pm.registerEvents(new StatUpdateListener(statManager), this);
        pm.registerEvents(new AntiExploitListener(this), this);
        pm.registerEvents(new FortuneListener(statManager), this);

        // Combat Listeners
        pm.registerEvents(new CombatListener(statManager), this);
        pm.registerEvents(new FerocityListener(this, statManager), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving player data and shutting down services...");
        
        // Disable all services in reverse order safely
        if (this.serviceManager != null) {
            this.serviceManager.disableAll();
        }
        
        getLogger().info("Skyblock Framework disabled safely.");
    }

    public static SkyblockPlugin getInstance() {
        return instance;
    }

    public SkyblockServiceManager getServiceManager() {
        return serviceManager;
    }
}
