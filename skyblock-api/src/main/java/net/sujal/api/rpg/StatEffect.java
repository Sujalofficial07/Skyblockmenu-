// Path: /skyblock-api/src/main/java/net/sujal/api/rpg/StatEffect.java
package net.sujal.api.rpg;

import org.bukkit.entity.Player;

/**
 * Interface to handle how individual stats apply their effects on the player.
 */
public interface StatEffect {
    
    /**
     * Applies the calculated stat value to the player's actual Minecraft attributes.
     * @param player The player to apply to.
     * @param totalStatValue The final calculated stat (Base + Armor + Hand + Skills).
     */
    void apply(Player player, double totalStatValue);
}
