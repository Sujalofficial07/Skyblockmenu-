// Path: /skyblock-skills/src/main/java/net/sujal/skills/engine/ManaManager.java
package net.sujal.skills.engine;

import net.sujal.api.rpg.Stat;
import net.sujal.api.service.Service;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManaManager implements Service {

    private final StatManager statManager;
    // Cache to store current mana for online players
    private final Map<UUID, Double> currentMana = new HashMap<>();

    public ManaManager(StatManager statManager) {
        this.statManager = statManager;
    }

    @Override
    public void onEnable() {
        // Initialization
    }

    @Override
    public void onDisable() {
        currentMana.clear();
    }

    public double getMaxMana(Player player) {
        // In Skyblock, Base Mana is 100. Intelligence adds to it 1:1.
        return statManager.getTotalStat(player, Stat.INTELLIGENCE);
    }

    public double getCurrentMana(Player player) {
        UUID uuid = player.getUniqueId();
        currentMana.putIfAbsent(uuid, getMaxMana(player));
        return currentMana.get(uuid);
    }

    public void consumeMana(Player player, double amount) {
        double mana = getCurrentMana(player);
        currentMana.put(player.getUniqueId(), Math.max(0, mana - amount));
    }

    public void addMana(Player player, double amount) {
        double maxMana = getMaxMana(player);
        double mana = getCurrentMana(player);
        currentMana.put(player.getUniqueId(), Math.min(maxMana, mana + amount));
    }

    // This should be called by a repeating task every second (Regen 2% of Max Mana per second)
    public void regenerateMana(Player player) {
        double maxMana = getMaxMana(player);
        double regenAmount = maxMana * 0.02; // 2% regen
        addMana(player, regenAmount);
    }
}
