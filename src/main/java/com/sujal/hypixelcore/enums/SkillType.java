package com.sujal.hypixelcore.enums;

public enum SkillType {
    FARMING("Farming", "Health"),
    MINING("Mining", "Defense"),
    COMBAT("Combat", "Crit Chance"),
    FORAGING("Foraging", "Strength"),
    FISHING("Fishing", "Health"),
    ENCHANTING("Enchanting", "Intelligence"),
    ALCHEMY("Alchemy", "Intelligence");

    private final String name;
    private final String statBonus;

    SkillType(String name, String statBonus) {
        this.name = name;
        this.statBonus = statBonus;
    }

    public String getName() { return name; }
    public String getStatBonus() { return statBonus; }
}
