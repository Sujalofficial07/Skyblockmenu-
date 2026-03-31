package com.skyblock.core;

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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GiveXpCommand implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final SkillManager skillManager;

    public GiveXpCommand(Plugin plugin, SkillManager skillManager) {
        this.plugin = plugin;
        this.skillManager = skillManager;
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
            sender.sendMessage(ColorUtil.color("&cUsage: /givexp <player> <skill> <amount>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color("&cPlayer &e" + args[0] + " &cnot found."));
            return true;
        }
        SkillType type;
        try {
            type = SkillType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ColorUtil.color("&cInvalid skill type. Valid skills: " +
                Arrays.stream(SkillType.values())
                    .map(SkillType::getId)
                    .collect(Collectors.joining(", "))));
            return true;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            sender.sendMessage(ColorUtil.color("&cAmount must be a positive number."));
            return true;
        }
        skillManager.addXp(target, type, amount);
        sender.sendMessage(ColorUtil.color(
            plugin.getConfig().getString("messages.admin-givexp",
                "&aGave &e{amount} &a{skill} XP to &e{player}")
                .replace("{amount}", ColorUtil.formatNumber((long) amount))
                .replace("{skill}", type.getDisplayName())
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
            return Arrays.stream(SkillType.values())
                .map(SkillType::getId)
                .filter(s -> s.startsWith(args[1].toLowerCase()))
                .collect(Collectors.toList());
        }
        if (args.length == 3) {
            return List.of("100", "1000", "10000", "100000");
        }
        return List.of();
    }
}
