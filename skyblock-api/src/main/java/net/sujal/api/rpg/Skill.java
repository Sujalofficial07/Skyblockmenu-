// Path: /skyblock-api/src/main/java/net/sujal/api/rpg/Skill.java
package net.sujal.api.rpg;

public enum Skill {
    COMBAT("Combat", 60),
    FARMING("Farming", 60),
    MINING("Mining", 60),
    FISHING("Fishing", 50),
    FORAGING("Foraging", 50),
    ENCHANTING("Enchanting", 60),
    ALCHEMY("Alchemy", 50),
    TAMING("Taming", 50),
    CARPENTRY("Carpentry", 50),
    SOCIAL("Social", 25),
    HUNTING("Hunting", 50);

    private final String name;
    private final int maxLevel;

    Skill(String name, int maxLevel) {
        this.name = name;
        this.maxLevel = maxLevel;
    }

    public String getName() { return name; }
    public int getMaxLevel() { return maxLevel; }
}
