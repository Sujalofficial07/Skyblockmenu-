package sb.sujal;

import org.bukkit.plugin.java.JavaPlugin;
import sb.sujal.profile.ProfileManager;
import sb.sujal.listeners.StatUpdateListener;

public final class SkyblockPlugin extends JavaPlugin {

    private static SkyblockPlugin instance;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        instance = this;
        
        // Ensure data folder exists
        if (!getDataFolder().exists()) getDataFolder().mkdirs();

        this.profileManager = new ProfileManager();

        // Register Listeners
        getServer().getPluginManager().registerEvents(new StatUpdateListener(), this);

        getLogger().info("Sujal's Skyblock Core has been enabled!");
    }

    @Override
    public void onDisable() {
        // Save all online players
        getServer().getOnlinePlayers().forEach(p -> profileManager.saveProfile(p.getUniqueId()));
        getLogger().info("Data saved successfully.");
    }

    public static SkyblockPlugin getInstance() {
        return instance;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }
}
