package com.skyblock.skills;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class PlayerSkillProfile {

    private final UUID uuid;
    private final Map<SkillType, SkillData> skills = new EnumMap<>(SkillType.class);

    public PlayerSkillProfile(UUID uuid) {
        this.uuid = uuid;
        for (SkillType type : SkillType.values()) {
            skills.put(type, new SkillData(type, 0, 0));
        }
    }

    public UUID getUuid() { return uuid; }

    public SkillData getSkill(SkillType type) {
        return skills.get(type);
    }

    public void setSkill(SkillType type, double xp, int level) {
        skills.put(type, new SkillData(type, xp, level));
    }

    public Map<SkillType, SkillData> getAllSkills() {
        return skills;
    }

    public double getAverageSkillLevel() {
        double total = 0;
        int count = 0;
        for (SkillType type : new SkillType[]{
            SkillType.FARMING, SkillType.MINING, SkillType.COMBAT,
            SkillType.FISHING, SkillType.FORAGING, SkillType.ENCHANTING,
            SkillType.ALCHEMY, SkillType.TAMING
        }) {
            total += skills.get(type).getLevel();
            count++;
        }
        return count == 0 ? 0 : total / count;
    }
}
