package com.skyblock.core;

import com.skyblock.menu.MenuManager;
import com.skyblock.skills.SkillsMenu;
import com.skyblock.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkillsCommand implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final MenuManager menuManager;

    public SkillsCommand(Plugin plugin, MenuManager menuManager) {
        this.plugin = plugin;
        this.menuManager = menuManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                "messages.player-only", "&cOnly players can use this.")));
            return true;
        }
        if (!player.hasPermission("skyblock.skills.use")) {
            player.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                "messages.no-permission", "&cNo permission.")));
            return true;
        }
        menuManager.openMenu(player, new SkillsMenu(menuManager));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                 @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
