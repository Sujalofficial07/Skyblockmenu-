// Path: /skyblock-data/src/main/java/net/sujal/data/profile/SkyblockProfileImpl.java
package net.sujal.data.profile;

import net.sujal.api.profile.SkyblockProfile;
import net.sujal.api.rpg.Skill;
import net.sujal.api.rpg.Stat;
import org.bson.Document;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class SkyblockProfileImpl implements SkyblockProfile {

    private final UUID uuid;
    private double coins;
    private final Map<Skill, Double> skillXp = new EnumMap<>(Skill.class);
    private final Map<Stat, Double> baseStats = new EnumMap<>(Stat.class);

    public SkyblockProfileImpl(UUID uuid) {
        this.uuid = uuid;
        this.coins = 0.0;
        // Initialize default stats
        for (Stat stat : Stat.values()) {
            baseStats.put(stat, stat.getBaseValue());
        }
        for (Skill skill : Skill.values()) {
            skillXp.put(skill, 0.0);
        }
    }

    @Override
    public UUID getPlayerUuid() { return uuid; }

    @Override
    public double getCoins() { return coins; }

    @Override
    public void setCoins(double amount) { this.coins = Math.max(0, amount); }

    @Override
    public void addCoins(double amount) { this.coins += amount; }

    @Override
    public void removeCoins(double amount) { this.coins = Math.max(0, this.coins - amount); }

    public double getSkillXp(Skill skill) { return skillXp.getOrDefault(skill, 0.0); }
    public void addSkillXp(Skill skill, double amount) { skillXp.put(skill, getSkillXp(skill) + amount); }
    public void setSkillXp(Skill skill, double amount) { skillXp.put(skill, amount); }

    public double getBaseStat(Stat stat) { return baseStats.getOrDefault(stat, stat.getBaseValue()); }
    public void setBaseStat(Stat stat, double amount) { baseStats.put(stat, amount); }

    // --- MongoDB Serialization ---
    public Document toBson() {
        Document skillDoc = new Document();
        skillXp.forEach((k, v) -> skillDoc.append(k.name(), v));

        Document statDoc = new Document();
        baseStats.forEach((k, v) -> statDoc.append(k.name(), v));

        return new Document("uuid", uuid.toString())
                .append("coins", coins)
                .append("skills", skillDoc)
                .append("stats", statDoc);
    }

    public static SkyblockProfileImpl fromBson(Document document) {
        UUID uuid = UUID.fromString(document.getString("uuid"));
        SkyblockProfileImpl profile = new SkyblockProfileImpl(uuid);
        profile.setCoins(document.getDouble("coins"));

        Document skillDoc = (Document) document.get("skills");
        if (skillDoc != null) {
            for (String key : skillDoc.keySet()) {
                try { profile.skillXp.put(Skill.valueOf(key), skillDoc.getDouble(key)); } catch (Exception ignored) {}
            }
        }

        Document statDoc = (Document) document.get("stats");
        if (statDoc != null) {
            for (String key : statDoc.keySet()) {
                try { profile.baseStats.put(Stat.valueOf(key), statDoc.getDouble(key)); } catch (Exception ignored) {}
            }
        }
        return profile;
    }
}
