package com.skyblock.collections;

import java.util.HashMap;
import java.util.UUID;

public class CollectionManager {

    private static final HashMap<UUID, HashMap<String, Integer>> collections = new HashMap<>();

    public static void add(UUID uuid, String type, int amount) {
        collections.putIfAbsent(uuid, new HashMap<>());
        collections.get(uuid).put(type, collections.get(uuid).getOrDefault(type, 0) + amount);
    }
}
