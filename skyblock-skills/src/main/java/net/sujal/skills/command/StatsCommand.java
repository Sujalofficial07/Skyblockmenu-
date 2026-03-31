// Path: /skyblock-skills/src/main/java/net/sujal/skills/command/StatsCommand.java
package net.sujal.skills.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.api.rpg.Stat;
import net.sujal.skills.engine.StatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StatsCommand implements CommandExecutor {

    private final StatManager statManager;

    public StatsCommand(StatManager statManager) {
        this.statManager = statManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }

        player.sendMessage(Component.text("--- Your Skyblock Stats ---", NamedTextColor.GOLD));
        for (Stat stat : Stat.values()) {
            double value = statManager.getTotalStat(player, stat);
            player.sendMessage(Component.text(stat.getSymbol() + " " + stat.getName() + ": ", NamedTextColor.GRAY)
                    .append(Component.text(String.format("%.1f", value), NamedTextColor.WHITE)));
        }
        
        // Next Step me yahan GUI open hoga bajaye chat messages ke!
        return true;
    }
}
