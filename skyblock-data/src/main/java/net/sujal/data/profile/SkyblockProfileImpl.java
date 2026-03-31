// Path: /skyblock-data/src/main/java/net/sujal/data/profile/SkyblockProfileImpl.java
package net.sujal.data.profile;

import net.sujal.api.profile.SkyblockProfile;
import org.bson.Document;

import java.util.UUID;

public class SkyblockProfileImpl implements SkyblockProfile {

    private final UUID uuid;
    private double coins;

    public SkyblockProfileImpl(UUID uuid) {
        this.uuid = uuid;
        this.coins = 0.0;
    }

    @Override
    public UUID getPlayerUuid() {
        return uuid;
    }

    @Override
    public double getCoins() {
        return coins;
    }

    @Override
    public void setCoins(double amount) {
        this.coins = Math.max(0, amount);
    }

    @Override
    public void addCoins(double amount) {
        this.coins += amount;
    }

    @Override
    public void removeCoins(double amount) {
        this.coins = Math.max(0, this.coins - amount);
    }

    // --- Serialization for MongoDB ---

    public Document toBson() {
        return new Document("uuid", uuid.toString())
                .append("coins", coins);
    }

    public static SkyblockProfileImpl fromBson(Document document) {
        UUID uuid = UUID.fromString(document.getString("uuid"));
        SkyblockProfileImpl profile = new SkyblockProfileImpl(uuid);
        profile.setCoins(document.getDouble("coins"));
        return profile;
    }
}
