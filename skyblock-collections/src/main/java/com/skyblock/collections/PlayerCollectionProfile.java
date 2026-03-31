package com.skyblock.collections;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCollectionProfile {

    private final UUID uuid;
    private final Map<CollectionType, CollectionData> collections = new EnumMap<>(CollectionType.class);

    public PlayerCollectionProfile(UUID uuid) {
        this.uuid = uuid;
        for (CollectionType type : CollectionType.values()) {
            collections.put(type, new CollectionData(type, 0, 0));
        }
    }

    public UUID getUuid() { return uuid; }

    public CollectionData getCollection(CollectionType type) {
        return collections.get(type);
    }

    public void setCollection(CollectionType type, long amount, int tier) {
        collections.put(type, new CollectionData(type, amount, tier));
    }

    public Map<CollectionType, CollectionData> getAllCollections() {
        return collections;
    }

    public Map<CollectionType, CollectionData> getByCategory(CollectionCategory category) {
        Map<CollectionType, CollectionData> result = new EnumMap<>(CollectionType.class);
        for (Map.Entry<CollectionType, CollectionData> entry : collections.entrySet()) {
            if (entry.getKey().getCategory() == category) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }
}
