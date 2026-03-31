// Path: /skyblock-skills/src/main/java/net/sujal/skills/engine/StatManager.java
package net.sujal.skills.engine;

import net.sujal.api.profile.ProfileManager;
import net.sujal.api.profile.SkyblockProfile;
import net.sujal.api.rpg.Stat;
import net.sujal.api.service.Service;
import net.sujal.items.util.ItemKeys;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class StatManager implements Service {

    private final ProfileManager profileManager;

    public StatManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public void onEnable() {
        // Initialization tasks
    }

    @Override
    public void onDisable() {
    }

    /**
     * Calculates the total live stat of a player (Base + Armor + Hand + Skill Bonuses)
     */
    public double getTotalStat(Player player, Stat stat) {
        Optional<SkyblockProfile> profileOpt = profileManager.getCachedProfile(player.getUniqueId());
        if (profileOpt.isEmpty()) return stat.getBaseValue();

        SkyblockProfile profile = profileOpt.get();
        double total = stat.getBaseValue() + profile.getBaseStat(stat);

        // Add Skill Bonuses (Pseudo-logic: Every combat level gives +1 crit chance, etc.)
        // total += calculateSkillBonus(profile, stat);

        // Add Armor Stats
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            total += getItemStat(armor, stat);
        }

        // Add Held Item Stats
        total += getItemStat(player.getInventory().getItemInMainHand(), stat);

        return total;
    }

    /**
     * Extracts a specific stat from an item's PersistentDataContainer (PDC)
     */
    private double getItemStat(ItemStack item, Stat stat) {
        if (item == null || !item.hasItemMeta()) return 0.0;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Map Stat enum to ItemKeys (We will need a mapping method)
        if (stat == Stat.DAMAGE && pdc.has(ItemKeys.DAMAGE, PersistentDataType.DOUBLE)) {
            return pdc.get(ItemKeys.DAMAGE, PersistentDataType.DOUBLE);
        }
        if (stat == Stat.STRENGTH && pdc.has(ItemKeys.STRENGTH, PersistentDataType.DOUBLE)) {
            return pdc.get(ItemKeys.STRENGTH, PersistentDataType.DOUBLE);
        }
        // Add other stat mappings here...

        return 0.0;
    }

    /**
     * Updates the vanilla attributes (Health, Speed) to match Skyblock Stats
     */
    public void updatePlayerAttributes(Player player) {
        double maxHealth = getTotalStat(player, Stat.HEALTH);
        double speed = getTotalStat(player, Stat.SPEED);

        // Update Max Health
        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH) != null) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        }

        // Update Movement Speed (Skyblock base 100 = Bukkit 0.2)
        float bukkitSpeed = (float) (speed / 100.0 * 0.2);
        player.setWalkSpeed(Math.min(bukkitSpeed, 1.0f));
    }
}
