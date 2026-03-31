package com.skyblock.skills;

import com.skyblock.database.DatabaseManager;
import com.skyblock.menu.MenuManager;
import com.skyblock.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SkyBlockSkillsPlugin extends JavaPlugin {

    private static SkyBlockSkillsPlugin instance;
    private SkillManager skillManager;

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

        skillManager = new SkillManager(this, databaseManager);

        getServer().getServicesManager().register(
            SkillManager.class,
            skillManager,
            this,
            org.bukkit.plugin.ServicePriority.Normal
        );

        getServer().getPluginManager().registerEvents(new SkillXpListener(skillManager, this), this);
        getServer().getPluginManager().registerEvents(new SkillPlayerListener(skillManager), this);

        getCommand("skills").setExecutor(this::onSkillsCommand);
        getCommand("givexp").setExecutor(this::onGiveXpCommand);

        getLogger().info("SkyBlockSkills module loaded.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SkyBlockSkills module unloaded.");
    }

    private boolean onSkillsCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.color("&cOnly players can use this command."));
            return true;
        }
        MenuManager menuManager = getServer().getServicesManager().load(MenuManager.class);
        if (menuManager == null) {
            player.sendMessage(ColorUtil.color("&cMenu system unavailable."));
            return true;
        }
        menuManager.openMenu(player, new SkillsMenu(menuManager));
        return true;
    }

    private boolean onGiveXpCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("skyblock.skills.admin")) {
            sender.sendMessage(ColorUtil.color("&cNo permission."));
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage(ColorUtil.color("&cUsage: /givexp <player> <skill> <amount>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer not found."));
            return true;
        }
        SkillType type;
        try {
            type = SkillType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid skill type. Options: " +
                java.util.Arrays.toString(SkillType.values())));
            return true;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid amount."));
            return true;
        }
        skillManager.addXp(target, type, amount);
        sender.sendMessage(ColorUtil.color("&aGave &e" + amount + " &a" + type.getDisplayName() +
            " XP to &e" + target.getName()));
        return true;
    }

    public static SkyBlockSkillsPlugin getInstance() {
        return instance;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }
            }
