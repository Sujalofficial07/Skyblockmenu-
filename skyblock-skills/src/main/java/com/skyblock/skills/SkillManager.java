package com.skyblock.skills;

import com.skyblock.database.DatabaseManager;
import com.skyblock.database.SkillLevelCalculator;
import com.skyblock.database.SkillsDAO;
import com.skyblock.utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillManager {

    private final Plugin plugin;
    private final SkillsDAO skillsDAO;
    private final Map<UUID, PlayerSkillProfile> cache = new HashMap<>();

    public SkillManager(Plugin plugin, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.skillsDAO = new SkillsDAO(databaseManager);
    }

    public void loadPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        skillsDAO.loadSkills(uuid, data -> {
            PlayerSkillProfile profile = new PlayerSkillProfile(uuid);
            for (Map.Entry<String, double[]> entry : data.entrySet()) {
                try {
                    SkillType type = SkillType.valueOf(entry.getKey().toUpperCase());
                    profile.setSkill(type, entry.getValue()[0], (int) entry.getValue()[1]);
                } catch (IllegalArgumentException ignored) {}
            }
            cache.put(uuid, profile);
        });
    }

    public void unloadPlayer(UUID uuid) {
        cache.remove(uuid);
    }

    public PlayerSkillProfile getProfile(UUID uuid) {
        return cache.computeIfAbsent(uuid, PlayerSkillProfile::new);
    }

    public void addXp(Player player, SkillType skillType, double xp) {
        UUID uuid = player.getUniqueId();
        PlayerSkillProfile profile = getProfile(uuid);
        double oldXp = profile.getSkill(skillType).getXp();
        int oldLevel = profile.getSkill(skillType).getLevel();

        skillsDAO.addXp(uuid, skillType.getId(), xp, result -> {
            double newXp = result[0];
            int newLevel = (int) result[1];
            profile.setSkill(skillType, newXp, newLevel);

            if (newLevel > oldLevel) {
                player.sendMessage(ColorUtil.color(
                    "&r &r&6&l  SKILL LEVEL UP &r&a" + skillType.getDisplayName() +
                    " &r&7" + ColorUtil.romanNumeral(oldLevel) + " ➜ " +
                    "&r&a" + ColorUtil.romanNumeral(newLevel)
                ));
                player.sendMessage(ColorUtil.color(
                    "&r &r&7Reach &a" + skillType.getDisplayName() + " " +
                    ColorUtil.romanNumeral(newLevel + 1) + "&7 in " + formatXpNeeded(skillType, newXp) + " XP."
                ));
            }
        });
    }

    private String formatXpNeeded(SkillType type, double currentXp) {
        double needed = SkillLevelCalculator.getXpToNextLevel(type.getId(), currentXp);
        if (needed <= 0) return "MAX";
        return ColorUtil.formatNumber((long) needed);
    }

    public SkillsDAO getSkillsDAO() {
        return skillsDAO;
    }
}
