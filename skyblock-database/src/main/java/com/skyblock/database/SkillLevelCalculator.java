package com.skyblock.database;

public final class SkillLevelCalculator {

    private SkillLevelCalculator() {}

    private static final long[] SKILL_XP_TABLE = {
        0, 50, 175, 375, 675, 1175, 1925, 2925, 4425, 6425,
        9925, 14925, 22425, 32425, 47425, 67425, 97425, 147425, 222425, 322425,
        522425, 822425, 1222425, 1722425, 2322425, 3022425, 3822425, 4722425, 5722425, 6822425,
        8022425, 9322425, 10722425, 12222425, 13822425, 15522425, 17322425, 19222425, 21222425, 23322425,
        25522425, 27822425, 30222425, 32722425, 35322425, 38022425, 40822425, 43722425, 46722425, 49822425
    };

    private static final long[] RUNECRAFTING_XP_TABLE = {
        0, 50, 150, 275, 435, 635, 885, 1200, 1600, 2100,
        2725, 3510, 4510, 5760, 7410, 9460, 12010, 15210, 19210, 24210,
        30210, 38210, 48210, 60210, 75210
    };

    public static int calculateLevel(String skillType, double totalXp) {
        long[] table = getTable(skillType);
        int level = 0;
        for (int i = 0; i < table.length; i++) {
            if (totalXp >= table[i]) {
                level = i;
            } else {
                break;
            }
        }
        return Math.min(level, getMaxLevel(skillType));
    }

    public static double getXpForLevel(String skillType, int level) {
        long[] table = getTable(skillType);
        if (level <= 0) return 0;
        if (level >= table.length) return table[table.length - 1];
        return table[level];
    }

    public static double getXpToNextLevel(String skillType, double totalXp) {
        int currentLevel = calculateLevel(skillType, totalXp);
        int maxLevel = getMaxLevel(skillType);
        if (currentLevel >= maxLevel) return 0;
        long[] table = getTable(skillType);
        if (currentLevel + 1 >= table.length) return 0;
        return table[currentLevel + 1] - totalXp;
    }

    public static double getProgressToNextLevel(String skillType, double totalXp) {
        int currentLevel = calculateLevel(skillType, totalXp);
        int maxLevel = getMaxLevel(skillType);
        if (currentLevel >= maxLevel) return 1.0;
        long[] table = getTable(skillType);
        if (currentLevel >= table.length || currentLevel + 1 >= table.length) return 1.0;
        double currentLevelXp = table[currentLevel];
        double nextLevelXp = table[currentLevel + 1];
        double range = nextLevelXp - currentLevelXp;
        double progress = totalXp - currentLevelXp;
        if (range <= 0) return 1.0;
        return Math.max(0.0, Math.min(1.0, progress / range));
    }

    private static long[] getTable(String skillType) {
        if ("runecrafting".equalsIgnoreCase(skillType)) return RUNECRAFTING_XP_TABLE;
        return SKILL_XP_TABLE;
    }

    public static int getMaxLevel(String skillType) {
        switch (skillType.toLowerCase()) {
            case "runecrafting": return 25;
            case "social": return 25;
            default: return 50;
        }
    }

    public static long getTotalXpForLevel(String skillType, int level) {
        long[] table = getTable(skillType);
        if (level <= 0) return 0;
        int idx = Math.min(level, table.length - 1);
        return table[idx];
    }
}
