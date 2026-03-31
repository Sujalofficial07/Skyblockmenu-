// Path: /skyblock-skills/src/main/java/net/sujal/skills/stats/effects/SpeedStat.java
package net.sujal.skills.stats.effects;

import net.sujal.api.rpg.StatEffect;
import org.bukkit.entity.Player;

public class SpeedStat implements StatEffect {

    @Override
    public void apply(Player player, double totalSpeed) {
        // Skyblock Formula: 100 Speed = 0.2f Bukkit Speed
        // Max limit in Bukkit is 1.0f (which would be 500 Skyblock Speed)
        float bukkitSpeed = (float) ((totalSpeed / 100.0) * 0.2);
        
        // Ensure it never crashes the client by exceeding 1.0f
        if (bukkitSpeed > 1.0f) bukkitSpeed = 1.0f;
        if (bukkitSpeed < 0.0f) bukkitSpeed = 0.0f;

        player.setWalkSpeed(bukkitSpeed);
    }
}
