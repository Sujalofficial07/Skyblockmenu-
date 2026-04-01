package com.skyblock.stats;

import java.util.HashMap;
import java.util.UUID;

public class StatsManager {

    private static final HashMap<UUID, HashMap<String, Double>> stats = new HashMap<>();

    public static void set(UUID uuid, String stat, double value) {
        stats.putIfAbsent(uuid, new HashMap<>());
        stats.get(uuid).put(stat, value);
    }

    public static double get(UUID uuid, String stat) {
        return stats.getOrDefault(uuid, new HashMap<>()).getOrDefault(stat, 0.0);
    }
}
