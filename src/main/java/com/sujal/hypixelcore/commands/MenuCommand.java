package com.sujal.hypixelcore.commands;

import com.sujal.hypixelcore.menu.SkyblockMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            SkyblockMenu.openMenu(player);
            return true;
        }
        sender.sendMessage("Only players can use this command.");
        return true;
    }
}
