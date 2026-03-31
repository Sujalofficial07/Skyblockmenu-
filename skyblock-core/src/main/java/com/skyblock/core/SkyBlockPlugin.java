package com.skyblock.core;

import com.skyblock.collections.CollectionListener;
import com.skyblock.collections.CollectionManager;
import com.skyblock.database.DatabaseManager;
import com.skyblock.menu.MainSkyBlockMenu;
import com.skyblock.menu.MenuItemBuilder;
import com.skyblock.menu.MenuListener;
import com.skyblock.menu.MenuManager;
import com.skyblock.skills.SkillManager;
import com.skyblock.skills.SkillXpListener;
import com.skyblock.utils.ColorUtil;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class SkyBlockPlugin extends JavaPlugin {

    private static SkyBlockPlugin instance;
    private DatabaseManager databaseManager;
    private MenuManager menuManager;
    private SkillManager skillManager;
    private CollectionManager collectionManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);
        try {
            databaseManager.initialize(getConfig());
            getLogger().info("Database connection established.");
        } catch (SQLException e) {
            getLogger().severe("Failed to connect to database! Disabling plugin.");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        menuManager = new MenuManager(this);
        skillManager = new SkillManager(this, databaseManager);
        collectionManager = new CollectionManager(this, databaseManager);

        getServer().getServicesManager().register(DatabaseManager.class, databaseManager, this, ServicePriority.Normal);
        getServer().getServicesManager().register(MenuManager.class, menuManager, this, ServicePriority.Normal);
        getServer().getServicesManager().register(SkillManager.class, skillManager, this, ServicePriority.Normal);
        getServer().getServicesManager().register(CollectionManager.class, collectionManager, this, ServicePriority.Normal);

        getServer().getPluginManager().registerEvents(new MenuListener(menuManager, this), this);
        getServer().getPluginManager().registerEvents(new SkillXpListener(skillManager, this), this);
        getServer().getPluginManager().registerEvents(new CollectionListener(collectionManager, this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this, skillManager, collectionManager), this);

        SkyBlockAdminCommand adminCommand = new SkyBlockAdminCommand(
            this, menuManager, skillManager, collectionManager, databaseManager);

        registerCommand("skyblock", new SkyBlockCommand(this, menuManager));
        registerCommand("skills", new SkillsCommand(this, menuManager));
        registerCommand("collections", new CollectionsCommand(this, menuManager));
        registerCommand("givexp", new GiveXpCommand(this, skillManager));
        registerCommand("addcollection", new AddCollectionCommand(this, collectionManager));
        registerCommand("skyblockadmin", adminCommand);

        PluginCommand skyblockAdminCmd = getCommand("skyblockadmin");
        if (skyblockAdminCmd != null) {
            skyblockAdminCmd.setExecutor(adminCommand);
            skyblockAdminCmd.setTabCompleter(adminCommand);
        }

        applyResourcePack();

        getLogger().info("SkyBlock plugin enabled successfully on Paper 1.21.1!");
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.shutdown();
        }
        getLogger().info("SkyBlock plugin disabled.");
    }

    private void registerCommand(String name, org.bukkit.command.CommandExecutor executor) {
        PluginCommand cmd = getCommand(name);
        if (cmd != null) {
            cmd.setExecutor(executor);
            if (executor instanceof org.bukkit.command.TabCompleter tc) {
                cmd.setTabCompleter(tc);
            }
        } else {
            getLogger().warning("Command '" + name + "' not found in plugin.yml!");
        }
    }

    private void applyResourcePack() {
        if (!getConfig().getBoolean("resourcepack.enabled", false)) return;
        String url = getConfig().getString("resourcepack.url", "");
        String hash = getConfig().getString("resourcepack.hash", "");
        boolean force = getConfig().getBoolean("resourcepack.force", false);
        String prompt = getConfig().getString("resourcepack.prompt", "&aPlease accept the resource pack!");
        if (url.isEmpty()) return;
        for (Player player : getServer().getOnlinePlayers()) {
            player.setResourcePack(url, hash, force,
                net.kyori.adventure.text.Component.text(
                    net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
                        .legacyAmpersand().deserialize(prompt).toString()));
        }
    }

    public static SkyBlockPlugin getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public MenuManager getMenuManager() { return menuManager; }
    public SkillManager getSkillManager() { return skillManager; }
    public CollectionManager getCollectionManager() { return collectionManager; }
}
