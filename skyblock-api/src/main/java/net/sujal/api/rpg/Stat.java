// Path: /skyblock-api/src/main/java/net/sujal/api/rpg/Stat.java
package net.sujal.api.rpg;

public enum Stat {
    HEALTH("Health", "❤", 100.0),
    DEFENSE("Defense", "❈", 0.0),
    STRENGTH("Strength", "❁", 0.0),
    INTELLIGENCE("Intelligence", "✎", 100.0),
    CRIT_CHANCE("Crit Chance", "☣", 30.0), // Base 30%
    CRIT_DAMAGE("Crit Damage", "☠", 50.0), // Base 50%
    SPEED("Speed", "✦", 100.0), // Base 100 (Walk speed 0.2)
    FEROCITY("Ferocity", "⫽", 0.0),
    MINING_SPEED("Mining Speed", "⸕", 0.0),
    FARMING_FORTUNE("Farming Fortune", "☘", 0.0),
    ABILITY_DAMAGE("Ability Damage", "๑", 0.0),
    DAMAGE("Damage", "⚔", 0.0); // FIXED: Added base Weapon Damage stat

    private final String name;
    private final String symbol;
    private final double baseValue;

    Stat(String name, String symbol, double baseValue) {
        this.name = name;
        this.symbol = symbol;
        this.baseValue = baseValue;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public double getBaseValue() { return baseValue; }
}
