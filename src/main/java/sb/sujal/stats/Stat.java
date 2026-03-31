package sb.sujal.stats;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum Stat {
    // Core Stats
    HEALTH("Health", "❤", NamedTextColor.RED, 100.0),
    DEFENSE("Defense", "❈", NamedTextColor.GREEN, 0.0),
    TRUE_DEFENSE("True Defense", "❂", NamedTextColor.WHITE, 0.0),
    SPEED("Speed", "✦", NamedTextColor.WHITE, 100.0),
    INTELLIGENCE("Intelligence", "✎", NamedTextColor.AQUA, 100.0),
    
    // Combat Stats
    STRENGTH("Strength", "❁", NamedTextColor.RED, 0.0),
    CRIT_CHANCE("Crit Chance", "☣", NamedTextColor.BLUE, 30.0),
    CRIT_DAMAGE("Crit Damage", "☠", NamedTextColor.BLUE, 50.0),
    ATTACK_SPEED("Bonus Attack Speed", "⚔", NamedTextColor.YELLOW, 0.0),
    FEROCITY("Ferocity", "⫽", NamedTextColor.RED, 0.0),
    MAGIC_FIND("Magic Find", "✯", NamedTextColor.AQUA, 0.0),
    PET_LUCK("Pet Luck", "♣", NamedTextColor.LIGHT_PURPLE, 0.0),
    
    // Gathering Stats
    MINING_SPEED("Mining Speed", "⸕", NamedTextColor.GOLD, 0.0),
    MINING_FORTUNE("Mining Fortune", "☘", NamedTextColor.GOLD, 0.0),
    FARMING_FORTUNE("Farming Fortune", "☘", NamedTextColor.GOLD, 0.0),
    FORAGING_FORTUNE("Foraging Fortune", "☘", NamedTextColor.GOLD, 0.0),
    PRISTINE("Pristine", "✧", NamedTextColor.DARK_PURPLE, 0.0);

    private final String name;
    private final String symbol;
    private final TextColor color;
    private final double baseValue;

    Stat(String name, String symbol, TextColor color, double baseValue) {
        this.name = name;
        this.symbol = symbol;
        this.color = color;
        this.baseValue = baseValue;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public TextColor getColor() { return color; }
    public double getBaseValue() { return baseValue; }
}
