// Path: /skyblock-mobs/src/main/java/net/sujal/mobs/listener/CombatListener.java
package net.sujal.mobs.listener;

import net.sujal.api.rpg.Stat;
import net.sujal.mobs.combat.DamageCalculator;
import net.sujal.skills.engine.StatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {

    private final StatManager statManager;

    public CombatListener(StatManager statManager) {
        this.statManager = statManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        
        // 1. Calculate Attacker's Outgoing Damage (Strength & Crits)
        if (event.getDamager() instanceof Player attacker) {
            
            // Get stats in real-time
            double weaponDamage = statManager.getTotalStat(attacker, Stat.DAMAGE); // Assuming Stat.DAMAGE exists in enum
            double strength = statManager.getTotalStat(attacker, Stat.STRENGTH);
            double critChance = statManager.getTotalStat(attacker, Stat.CRIT_CHANCE);
            double critDamage = statManager.getTotalStat(attacker, Stat.CRIT_DAMAGE);

            // Run through our custom math engine
            double finalOutgoingDamage = DamageCalculator.calculateOutgoingDamage(weaponDamage, strength, critChance, critDamage);
            
            // Set the raw damage (before the defender's armor applies)
            event.setDamage(finalOutgoingDamage);
        }

        // 2. Calculate Defender's Incoming Damage (Defense)
        if (event.getEntity() instanceof Player defender) {
            
            double defense = statManager.getTotalStat(defender, Stat.DEFENSE);
            double originalDamage = event.getDamage(); // The damage coming in from the attacker/mob

            // Run through our Defense reduction formula
            double finalDamageTaken = DamageCalculator.calculateDamageTaken(originalDamage, defense);
            
            // Apply the reduced damage
            event.setDamage(finalDamageTaken);
        }
    }
}
