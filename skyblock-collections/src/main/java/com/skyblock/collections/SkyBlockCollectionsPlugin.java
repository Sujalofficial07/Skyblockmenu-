package com.skyblock.collections;

import com.skyblock.database.DatabaseManager;
import com.skyblock.menu.MenuManager;
import com.skyblock.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlockCollectionsPlugin extends JavaPlugin {

    private static SkyBlockCollectionsPlugin instance;
    private CollectionManager collectionManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        DatabaseManager databaseManager = getServer().getServicesManager().load(DatabaseManager.class);
        if (databaseManager == null) {
            getLogger().severe("DatabaseManager service not found! Is SkyBlockDatabase enabled?");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MenuManager menuManager = getServer().getServicesManager().load(MenuManager.class);
        if (menuManager == null) {
            getLogger().severe("MenuManager service not found! Is SkyBlockMenu enabled?");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        collectionManager = new CollectionManager(this, databaseManager);

        getServer().getServicesManager().register(
            CollectionManager.class,
            collectionManager,
            this,
            org.bukkit.plugin.ServicePriority.Normal
        );

        getServer().getPluginManager().registerEvents(new CollectionListener(collectionManager, this), this);
        getServer().getPluginManager().registerEvents(new CollectionPlayerListener(collectionManager), this);

        getCommand("collections").setExecutor(this::onCollectionsCommand);
        getCommand("addcollection").setExecutor(this::onAddCollectionCommand);

        getLogger().info("SkyBlockCollections module loaded.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkyBlockCollections module unloaded.");
    }

    private boolean onCollectionsCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.color("&cOnly players can use this command."));
            return true;
        }
        MenuManager menuManager = getServer().getServicesManager().load(MenuManager.class);
        if (menuManager == null) {
            player.sendMessage(ColorUtil.color("&cMenu system unavailable."));
            return true;
        }
        menuManager.openMenu(player, new CollectionsMenu(menuManager));
        return true;
    }

    private boolean onAddCollectionCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("skyblock.collections.admin")) {
            sender.sendMessage(ColorUtil.color("&cNo permission."));
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage(ColorUtil.color("&cUsage: /addcollection <player> <collection> <amount>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer not found."));
            return true;
        }
        CollectionType type;
        try {
            type = CollectionType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid collection type."));
            return true;
        }
        long amount;
        try {
            amount = Long.parseLong(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid amount."));
            return true;
        }
        collectionManager.addCollection(target, type, amount);
        sender.sendMessage(ColorUtil.color("&aAdded &e" + amount + " &a" + type.getDisplayName() +
            " collection to &e" + target.getName()));
        return true;
    }

    public static SkyBlockCollectionsPlugin getInstance() {
        return instance;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
