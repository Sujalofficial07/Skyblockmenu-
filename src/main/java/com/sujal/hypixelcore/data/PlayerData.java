package com.sujal.hypixelcore.data;

import com.sujal.hypixelcore.enums.SkillType;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {
    private final Player player;
    private final Map<SkillType, Integer> skills;

    public PlayerData(Player player) {
        this.player = player;
        this.skills = new HashMap<>();
        // Default sab skills level 0 par set hain
        for (SkillType skill : SkillType.values()) {
            skills.put(skill, 0); 
        }
    }

    public int getSkillLevel(SkillType type) {
        return skills.getOrDefault(type, 0);
    }

    public void setSkillLevel(SkillType type, int level) {
        skills.put(type, level);
    }

    // Wiki ke hisaab se dynamic XP calculation
    public int getSkyblockXP() {
        int totalXP = 0;
        for (int level : skills.values()) {
            for (int i = 1; i <= level; i++) {
                if (i <= 10) totalXP += 5;
                else if (i <= 25) totalXP += 10;
                else if (i <= 50) totalXP += 20;
                else totalXP += 30;
            }
        }
        return totalXP;
    }

    // 100 XP = 1 Level
    public int getSkyblockLevel() {
        return getSkyblockXP() / 100;
    }
}
