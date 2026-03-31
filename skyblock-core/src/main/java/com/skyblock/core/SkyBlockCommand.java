package com.skyblock.core;

import com.skyblock.menu.MainSkyBlockMenu;
import com.skyblock.menu.MenuItemBuilder;
import com.skyblock.menu.MenuManager;
import com.skyblock.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SkyBlockCommand implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final MenuManager menuManager;
    private final MenuItemBuilder menuItemBuilder;

    public SkyBlockCommand(Plugin plugin, MenuManager menuManager) {
        this.plugin = plugin;
        this.menuManager = menuManager;
        this.menuItemBuilder = new MenuItemBuilder(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                "messages.player-only", "&cOnly players can use this.")));
            return true;
        }
        if (!player.hasPermission("skyblock.use")) {
            player.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                "messages.no-permission", "&cNo permission.")));
            return true;
        }
        if (args.length == 0 || args[0].equalsIgnoreCase("menu")) {
            menuManager.openMenu(player, new MainSkyBlockMenu(menuManager));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "item" -> {
                player.getInventory().setItem(8, menuItemBuilder.buildSkyBlockMenuItem());
                player.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                    "messages.menu-item-given", "&aMenu item given!")));
            }
            case "reload" -> {
                if (!player.hasPermission("skyblock.admin")) {
                    player.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                        "messages.no-permission", "&cNo permission.")));
                    return true;
                }
                plugin.reloadConfig();
                player.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                    "messages.reload-success", "&aReloaded!")));
            }
            case "help" -> sendHelp(player);
            default -> {
                player.sendMessage(ColorUtil.color("&cUnknown subcommand. Use &e/skyblock help"));
            }
        }
        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage(ColorUtil.color("&6===== SkyBlock Help ====="));
        player.sendMessage(ColorUtil.color("&e/skyblock &7- Opens SkyBlock menu"));
        player.sendMessage(ColorUtil.color("&e/skyblock menu &7- Opens SkyBlock menu"));
        player.sendMessage(ColorUtil.color("&e/skyblock item &7- Get the SkyBlock menu item"));
        player.sendMessage(ColorUtil.color("&e/skills &7- Opens the Skills menu"));
        player.sendMessage(ColorUtil.color("&e/collections &7- Opens the Collections menu"));
        if (player.hasPermission("skyblock.admin")) {
            player.sendMessage(ColorUtil.color("&e/skyblock reload &7- Reload config"));
            player.sendMessage(ColorUtil.color("&e/sbadmin &7- Admin commands"));
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                 @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return List.of();
        if (args.length == 1) {
            List<String> options = new java.util.ArrayList<>(Arrays.asList("menu", "item", "help"));
            if (player.hasPermission("skyblock.admin")) options.add("reload");
            return options.stream()
                .filter(s -> s.startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }
        return List.of();
    }
}
