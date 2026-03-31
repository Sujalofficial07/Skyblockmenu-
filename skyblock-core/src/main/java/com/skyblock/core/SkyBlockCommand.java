package com.skyblock.core;

import com.skyblock.menu.MainSkyBlockMenu;
import com.skyblock.menu.MenuItemBuilder;
import com.skyblock.menu.MenuManager;
import com.skyblock.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SkyBlockCommand implements CommandExecutor {

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
            sender.sendMessage(ColorUtil.color("&cOnly players can use this command."));
            return true;
        }

        if (args.length == 0) {
            menuManager.openMenu(player, new MainSkyBlockMenu(menuManager));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "menu" -> menuManager.openMenu(player, new MainSkyBlockMenu(menuManager));
            case "item" -> {
                ItemStack menuItem = menuItemBuilder.buildSkyBlockMenuItem();
                player.getInventory().setItem(8, menuItem);
                player.sendMessage(ColorUtil.color("&aGiven SkyBlock Menu item to your hotbar slot 9!"));
            }
            case "reload" -> {
                if (!player.hasPermission("skyblock.admin")) {
                    player.sendMessage(ColorUtil.color("&cYou don't have permission to do that."));
                    return true;
                }
                plugin.reloadConfig();
                player.sendMessage(ColorUtil.color("&aConfig reloaded!"));
            }
            default -> player.sendMessage(ColorUtil.color("&cUnknown subcommand. Use /skyblock, /skyblock item, or /skyblock reload"));
        }
        return true;
    }
}
