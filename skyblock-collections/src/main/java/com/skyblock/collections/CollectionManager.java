package com.skyblock.collections;

import com.skyblock.database.CollectionTierCalculator;
import com.skyblock.database.CollectionsDAO;
import com.skyblock.database.DatabaseManager;
import com.skyblock.utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CollectionManager {

    private final Plugin plugin;
    private final CollectionsDAO collectionsDAO;
    private final Map<UUID, PlayerCollectionProfile> cache = new HashMap<>();

    public CollectionManager(Plugin plugin, DatabaseManager databaseManager) {
        this.plugin = plugin;
        this.collectionsDAO = new CollectionsDAO(databaseManager);
    }

    public void loadPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        collectionsDAO.loadCollections(uuid, data -> {
            PlayerCollectionProfile profile = new PlayerCollectionProfile(uuid);
            for (Map.Entry<String, long[]> entry : data.entrySet()) {
                try {
                    CollectionType type = CollectionType.valueOf(entry.getKey().toUpperCase());
                    profile.setCollection(type, entry.getValue()[0], (int) entry.getValue()[1]);
                } catch (IllegalArgumentException ignored) {}
            }
            cache.put(uuid, profile);
        });
    }

    public void unloadPlayer(UUID uuid) {
        cache.remove(uuid);
    }

    public PlayerCollectionProfile getProfile(UUID uuid) {
        return cache.computeIfAbsent(uuid, PlayerCollectionProfile::new);
    }

    public void addCollection(Player player, CollectionType collectionType, long amount) {
        UUID uuid = player.getUniqueId();
        PlayerCollectionProfile profile = getProfile(uuid);
        CollectionData data = profile.getCollection(collectionType);
        int oldTier = data.getTier();

        collectionsDAO.addCollection(uuid, collectionType.getId(), amount, result -> {
            long newAmount = result[0];
            int newTier = (int) result[1];
            profile.setCollection(collectionType, newAmount, newTier);

            if (newTier > oldTier) {
                player.sendMessage(ColorUtil.color(
                    "&r &r&6&l  COLLECTION TIER UP &r&e" + collectionType.getDisplayName() +
                    " &r&7Tier " + toRoman(oldTier) + " ➜ " +
                    "&r&e" + toRoman(newTier)
                ));
                player.sendMessage(ColorUtil.color(
                    "&r &r&7Keep collecting &e" + collectionType.getDisplayName() +
                    "&7 to unlock more rewards!"
                ));
            }
        });
    }

    private String toRoman(int n) {
        return ColorUtil.romanNumeral(n);
    }

    public CollectionsDAO getCollectionsDAO() {
        return collectionsDAO;
    }
}
