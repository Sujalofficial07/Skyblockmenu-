// Path: /skyblock-mobs/src/main/java/net/sujal/mobs/listener/FerocityListener.java
package net.sujal.mobs.listener;

import net.sujal.api.rpg.Stat;
import net.sujal.skills.engine.StatManager;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class FerocityListener implements Listener {

    private final StatManager statManager;
    private final JavaPlugin plugin;
    private static final String FEROCITY_TAG = "is_ferocity_strike";

    public FerocityListener(JavaPlugin plugin, StatManager statManager) {
        this.plugin = plugin;
        this.statManager = statManager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMeleeHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (!(event.getEntity() instanceof LivingEntity target)) return;

        // Prevent infinite loops (Ferocity hit triggering another Ferocity hit)
        if (attacker.hasMetadata(FEROCITY_TAG)) {
            attacker.removeMetadata(FEROCITY_TAG, plugin);
            return;
        }

        double ferocity = statManager.getTotalStat(attacker, Stat.FEROCITY);
        if (ferocity <= 0) return;

        // Math: 150 Ferocity = 1 guaranteed extra hit, 50% chance for a second one.
        int extraStrikes = (int) (ferocity / 100);
        double chance = ferocity % 100;

        if (Math.random() * 100 <= chance) {
            extraStrikes++;
        }

        if (extraStrikes > 0) {
            double damageToDeal = event.getFinalDamage(); // Duplicate the exact damage
            
            for (int i = 0; i < extraStrikes; i++) {
                final int strikeNum = i;
                // Schedule the extra strikes slightly delayed so it looks like a rapid slash
                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    if (target.isDead() || !target.isValid()) return;
                    
                    attacker.setMetadata(FEROCITY_TAG, new FixedMetadataValue(plugin, true));
                    target.damage(damageToDeal, attacker);
                    
                    // Sound effect for Ferocity
                    attacker.playSound(attacker.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 0.5f);
                }, 2L * (strikeNum + 1)); // 2 ticks delay per strike
            }
        }
    }
}
