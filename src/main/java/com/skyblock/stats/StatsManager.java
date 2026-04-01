package com.skyblock.stats;

import com.skyblock.SkyBlockPlugin;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsManager {

    private final SkyBlockPlugin plugin;
    private final Map<UUID, PlayerStats> cache = new HashMap<>();

    public StatsManager(SkyBlockPlugin plugin) {
        this.plugin = plugin;
    }

    public PlayerStats getStats(Player player) {
        return cache.computeIfAbsent(player.getUniqueId(), k -> new PlayerStats());
    }

    public static class PlayerStats {
        public double health = 100;
        public double defense = 0;
        public double strength = 0;
        public double speed = 100;
        public double critChance = 30;
        public double critDamage = 50;
        public double intelligence = 100;
        public double ferocity = 0;
        public double trueDefense = 0;
        public double attackSpeed = 0;
    }
}
