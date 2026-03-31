package com.skyblock.core;

import com.skyblock.collections.CollectionManager;
import com.skyblock.collections.CollectionType;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddCollectionCommand implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final CollectionManager collectionManager;

    public AddCollectionCommand(Plugin plugin, CollectionManager collectionManager) {
        this.plugin = plugin;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("skyblock.admin")) {
            sender.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                "messages.no-permission", "&cNo permission.")));
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage(ColorUtil.color("&cUsage: /addcollection <player> <collection> <amount>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer &e" + args[0] + " &cnot found."));
            return true;
        }
        CollectionType type;
        try {
            type = CollectionType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid collection type."));
            sender.sendMessage(ColorUtil.color("&7Valid types: " +
                Arrays.stream(CollectionType.values())
                    .map(CollectionType::getId)
                    .collect(Collectors.joining(", "))));
            return true;
        }
        long amount;
        try {
            amount = Long.parseLong(args[2]);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtil.color("&cAmount must be a positive integer."));
            return true;
        }
        collectionManager.addCollection(target, type, amount);
        sender.sendMessage(ColorUtil.color(
            plugin.getConfig().getString("messages.admin-addcollection",
                "&aAdded &e{amount} &a{collection} to &e{player}")
                .replace("{amount}", ColorUtil.formatNumber(amount))
                .replace("{collection}", type.getDisplayName())
                .replace("{player}", target.getName())
        ));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                                 @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("skyblock.admin")) return List.of();
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(n -> n.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }
        if (args.length == 2) {
            return Arrays.stream(CollectionType.values())
                .map(CollectionType::getId)
                .filter(s -> s.startsWith(args[1].toLowerCase()))
                .collect(Collectors.toList());
        }
        if (args.length == 3) {
            return List.of("50", "100", "500", "1000", "10000");
        }
        return List.of();
    }
}
