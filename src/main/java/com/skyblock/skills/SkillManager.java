package com.skyblock.skills;

import java.util.HashMap;
import java.util.UUID;

public class SkillManager {

    private static final HashMap<UUID, HashMap<String, Double>> skills = new HashMap<>();

    public static void addXP(UUID uuid, String skill, double xp) {
        skills.putIfAbsent(uuid, new HashMap<>());
        skills.get(uuid).put(skill, skills.get(uuid).getOrDefault(skill, 0.0) + xp);
    }

    public static double getXP(UUID uuid, String skill) {
        return skills.getOrDefault(uuid, new HashMap<>()).getOrDefault(skill, 0.0);
    }
}
