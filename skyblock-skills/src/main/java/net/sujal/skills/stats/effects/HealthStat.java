// Path: /skyblock-skills/src/main/java/net/sujal/skills/stats/effects/HealthStat.java
package net.sujal.skills.stats.effects;

import net.sujal.api.rpg.StatEffect;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class HealthStat implements StatEffect {

    @Override
    public void apply(Player player, double totalHealth) {
        AttributeInstance maxHealthAttr = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttr != null) {
            // Set the absolute max health
            maxHealthAttr.setBaseValue(totalHealth);
            
            // If player's current health exceeds the new max (e.g., they took off armor), scale it down
            if (player.getHealth() > totalHealth) {
                player.setHealth(totalHealth);
            }
            
            // Optional: You can add health scaling here if you want 1000 health to look like 10 hearts (Hypixel style)
            // player.setHealthScale(40.0); // 20 hearts visually on screen
            // player.setHealthScaled(true);
        }
    }
}
