package sb.sujal.profile;

import sb.sujal.stats.Stat;
import sb.sujal.stats.StatSource;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfile {
    private final UUID uuid;
    
    // Stat -> (Source -> Value)
    // Example: HEALTH -> (BASE: 100, ARMOR: 50, SKILL: 10) = Total 160
    private final Map<Stat, Map<StatSource, Double>> statModifiers = new EnumMap<>(Stat.class);

    public PlayerProfile(UUID uuid) {
        this.uuid = uuid;
        // Initialize base stats
        for (Stat stat : Stat.values()) {
            statModifiers.put(stat, new EnumMap<>(StatSource.class));
            setModifier(stat, StatSource.BASE, stat.getBaseValue());
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    // Ek specific source se stat set karna (e.g., jab player armor pehne)
    public void setModifier(Stat stat, StatSource source, double value) {
        statModifiers.get(stat).put(source, value);
    }

    // Source ka stat get karna (e.g., base health save karne ke liye)
    public double getModifier(Stat stat, StatSource source) {
        return statModifiers.get(stat).getOrDefault(source, 0.0);
    }

    // Sabse important function: Player ka final stat nikalna
    public double getTotalStat(Stat stat) {
        double total = 0;
        for (double val : statModifiers.get(stat).values()) {
            total += val;
        }
        
        // Limits & Caps apply karna
        if (stat == Stat.CRIT_CHANCE && total > 100.0) return 100.0;
        if (stat == Stat.ATTACK_SPEED && total > 100.0) return 100.0;
        if (stat == Stat.SPEED && total > 500.0) return 500.0; // Typical max speed
        
        return total;
    }
}
