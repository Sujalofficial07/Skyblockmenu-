package com.skyblock.core;

import com.skyblock.collections.CollectionManager;
import com.skyblock.collections.CollectionType;
import com.skyblock.database.DatabaseManager;
import com.skyblock.menu.MenuItemBuilder;
import com.skyblock.menu.MenuManager;
import com.skyblock.skills.SkillManager;
import com.skyblock.skills.SkillType;
import com.skyblock.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SkyBlockAdminCommand implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final MenuManager menuManager;
    private final SkillManager skillManager;
    private final CollectionManager collectionManager;
    private final DatabaseManager databaseManager;
    private final MenuItemBuilder menuItemBuilder;

    public SkyBlockAdminCommand(Plugin plugin, MenuManager menuManager,
                                 SkillManager skillManager, CollectionManager collectionManager,
                                 DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.menuManager = menuManager;
        this.skillManager = skillManager;
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
        this.menuItemBuilder = new MenuItemBuilder(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("skyblock.admin")) {
            sender.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                "messages.no-permission", "&cNo permission.")));
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> handleReload(sender);
            case "reset" -> handleReset(sender, args);
            case "info" -> handleInfo(sender, args);
            case "givexp" -> handleGiveXp(sender, args);
            case "addcollection" -> handleAddCollection(sender, args);
            case "giveitem" -> handleGiveItem(sender, args);
            default -> sendHelp(sender);
        }
        return true;
    }

    private void handleReload(CommandSender sender) {
        plugin.reloadConfig();
        sender.sendMessage(ColorUtil.color(plugin.getConfig().getString(
            "messages.reload-success", "&aConfiguration reloaded!")));
    }

    private void handleReset(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ColorUtil.color("&cUsage: /sbadmin reset <player> <skills|collections|all>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer not found."));
            return;
        }
        switch (args[2].toLowerCase()) {
            case "skills" -> {
                for (SkillType type : SkillType.values()) {
                    skillManager.getSkillsDAO().saveSkill(target.getUniqueId(), type.getId(), 0, 0);
                    skillManager.getProfile(target.getUniqueId()).setSkill(type, 0, 0);
                }
                sender.sendMessage(ColorUtil.color("&aReset all skills for &e" + target.getName()));
            }
            case "collections" -> {
                for (CollectionType type : CollectionType.values()) {
                    collectionManager.getCollectionsDAO().saveCollection(target.getUniqueId(), type.getId(), 0, 0);
                    collectionManager.getProfile(target.getUniqueId()).setCollection(type, 0, 0);
                }
                sender.sendMessage(ColorUtil.color("&aReset all collections for &e" + target.getName()));
            }
            case "all" -> {
                for (SkillType type : SkillType.values()) {
                    skillManager.getSkillsDAO().saveSkill(target.getUniqueId(), type.getId(), 0, 0);
                    skillManager.getProfile(target.getUniqueId()).setSkill(type, 0, 0);
                }
                for (CollectionType type : CollectionType.values()) {
                    collectionManager.getCollectionsDAO().saveCollection(target.getUniqueId(), type.getId(), 0, 0);
                    collectionManager.getProfile(target.getUniqueId()).setCollection(type, 0, 0);
                }
                sender.sendMessage(ColorUtil.color("&aReset all skills and collections for &e" + target.getName()));
            }
            default -> sender.sendMessage(ColorUtil.color("&cInvalid type. Use: skills, collections, all"));
        }
    }

    private void handleInfo(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ColorUtil.color("&cUsage: /sbadmin info <player>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer not found."));
            return;
        }
        sender.sendMessage(ColorUtil.color("&6===== SkyBlock Info: &e" + target.getName() + " &6====="));
        sender.sendMessage(ColorUtil.color("&7UUID: &f" + target.getUniqueId()));
        sender.sendMessage(ColorUtil.color("&7Online: &a" + target.isOnline()));
        sender.sendMessage(ColorUtil.color(""));
        sender.sendMessage(ColorUtil.color("&6Skills:"));
        for (SkillType type : SkillType.values()) {
            com.skyblock.skills.SkillData data = skillManager.getProfile(target.getUniqueId()).getSkill(type);
            sender.sendMessage(ColorUtil.color("  &7" + type.getDisplayName() +
                ": &aLevel " + data.getLevel() +
                " &7(&e" + ColorUtil.formatNumber((long) data.getXp()) + " XP&7)"));
        }
        sender.sendMessage(ColorUtil.color(""));
        sender.sendMessage(ColorUtil.color("&6Collections Summary:"));
        int totalTiers = 0;
        int unlockedCollections = 0;
        for (CollectionType type : CollectionType.values()) {
            com.skyblock.collections.CollectionData data =
                collectionManager.getProfile(target.getUniqueId()).getCollection(type);
            if (data.getTier() > 0) {
                unlockedCollections++;
                totalTiers += data.getTier();
            }
        }
        sender.sendMessage(ColorUtil.color("  &7Unlocked: &a" + unlockedCollections +
            " &7/ &a" + CollectionType.values().length));
        sender.sendMessage(ColorUtil.color("  &7Total Tiers Earned: &a" + totalTiers));
    }

    private void handleGiveXp(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(ColorUtil.color("&cUsage: /sbadmin givexp <player> <skill> <amount>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer not found."));
            return;
        }
        SkillType type;
        try {
            type = SkillType.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid skill. Options: " +
                Arrays.stream(SkillType.values())
                    .map(SkillType::getId)
                    .collect(Collectors.joining(", "))));
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid amount."));
            return;
        }
        skillManager.addXp(target, type, amount);
        sender.sendMessage(ColorUtil.color("&aGave &e" + amount +
            " &a" + type.getDisplayName() + " XP to &e" + target.getName()));
    }

    private void handleAddCollection(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(ColorUtil.color("&cUsage: /sbadmin addcollection <player> <collection> <amount>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer not found."));
            return;
        }
        CollectionType type;
        try {
            type = CollectionType.valueOf(args[2].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid collection type."));
            return;
        }
        long amount;
        try {
            amount = Long.parseLong(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid amount."));
            return;
        }
        collectionManager.addCollection(target, type, amount);
        sender.sendMessage(ColorUtil.color("&aAdded &e" + amount +
            " &a" + type.getDisplayName() + " to &e" + target.getName()));
    }

    private void handleGiveItem(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.color("&cOnly players can use this subcommand."));
            return;
        }
        Player target = args.length >= 2 ? Bukkit.getPlayer(args[1]) : player;
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer not found."));
            return;
        }
        target.getInventory().setItem(8, menuItemBuilder.buildSkyBlockMenuItem());
        sender.sendMessage(ColorUtil.color("&aGiven SkyBlock menu item to &e" + target.getName()));
        if (!target.equals(sender)) {
            target.sendMessage(ColorUtil.color("&aYou have been given the SkyBlock Menu item!"));
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ColorUtil.color("&6===== SkyBlock Admin Help ====="));
        sender.sendMessage(ColorUtil.color("&e/sbadmin reload &7- Reload configuration"));
        sender.sendMessage(ColorUtil.color("&e/sbadmin reset <player> <skills|collections|all> &7- Reset player data"));
        sender.sendMessage(ColorUtil.color("&e/sbadmin info <player> &7- View player SkyBlock info"));
        sender.sendMessage(ColorUtil.color("&e/sbadmin givexp <player> <skill> <amount> &7- Give skill XP"));
        sender.sendMessage(ColorUtil.color("&e/sbadmin addcollection <player> <collection> <amount> &7- Add collection"));
        sender.sendMessage(ColorUtil.color("&e/sbadmin giveitem [player] &7- Give SkyBlock menu item"));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                 @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("skyblock.admin")) return new ArrayList<>();

        if (args.length == 1) {
            return filterStartingWith(args[0],
                "reload", "reset", "info", "givexp", "addcollection", "giveitem");
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "reset", "info", "givexp", "addcollection", "giveitem" -> {
                    return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(n -> n.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
                }
            }
        }

        if (args.length == 3) {
            switch (args[0].toLowerCase()) {
                case "reset" -> {
                    return filterStartingWith(args[2], "skills", "collections", "all");
                }
                case "givexp" -> {
                    return Arrays.stream(SkillType.values())
                        .map(SkillType::getId)
                        .filter(s -> s.startsWith(args[2].toLowerCase()))
                        .collect(Collectors.toList());
                }
                case "addcollection" -> {
                    return Arrays.stream(CollectionType.values())
                        .map(CollectionType::getId)
                        .filter(s -> s.startsWith(args[2].toLowerCase()))
                        .collect(Collectors.toList());
                }
            }
        }

        return new ArrayList<>();
    }

    private List<String> filterStartingWith(String input, String... options) {
        return Arrays.stream(options)
            .filter(s -> s.startsWith(input.toLowerCase()))
            .collect(Collectors.toList());
    }
          }
