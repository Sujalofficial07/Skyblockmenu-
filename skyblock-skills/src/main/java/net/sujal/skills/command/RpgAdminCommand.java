// Path: /skyblock-skills/src/main/java/net/sujal/skills/command/RpgAdminCommand.java
package net.sujal.skills.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.api.profile.ProfileManager;
import net.sujal.api.profile.SkyblockProfile;
import net.sujal.api.rpg.Skill;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RpgAdminCommand implements CommandExecutor {

    private final ProfileManager profileManager;

    public RpgAdminCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("skyblock.admin")) {
            sender.sendMessage(Component.text("No permission.", NamedTextColor.RED));
            return true;
        }

        // Usage: /rpgadmin skill add <player> <skill> <amount>
        if (args.length == 5 && args[0].equalsIgnoreCase("skill") && args[1].equalsIgnoreCase("add")) {
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                sender.sendMessage(Component.text("Player not found.", NamedTextColor.RED));
                return true;
            }

            try {
                Skill skill = Skill.valueOf(args[3].toUpperCase());
                double amount = Double.parseDouble(args[4]);

                Optional<SkyblockProfile> profileOpt = profileManager.getCachedProfile(target.getUniqueId());
                if (profileOpt.isPresent()) {
                    SkyblockProfile profile = profileOpt.get();
                    profile.addSkillXp(skill, amount);
                    
                    sender.sendMessage(Component.text("Added " + amount + " XP to " + target.getName() + "'s " + skill.getName() + " skill.", NamedTextColor.GREEN));
                    target.sendMessage(Component.text("You gained " + amount + " " + skill.getName() + " XP!", NamedTextColor.AQUA));
                }
            } catch (IllegalArgumentException e) {
                sender.sendMessage(Component.text("Invalid skill or amount.", NamedTextColor.RED));
            }
            return true;
        }

        sender.sendMessage(Component.text("Usage: /rpgadmin skill add <player> <skill> <amount>", NamedTextColor.RED));
        return true;
    }
}
