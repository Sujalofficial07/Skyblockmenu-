// Path: /skyblock-api/src/main/java/net/sujal/api/profile/SkyblockProfile.java
package net.sujal.api.profile;

import net.sujal.api.rpg.Skill;
import net.sujal.api.rpg.Stat;

import java.util.UUID;

/**
 * Represents a player's data profile.
 */
public interface SkyblockProfile {
    UUID getPlayerUuid();
    
    // Economy
    double getCoins();
    void setCoins(double amount);
    void addCoins(double amount);
    void removeCoins(double amount);
    
    // Skills
    double getSkillXp(Skill skill);
    void addSkillXp(Skill skill, double amount);
    void setSkillXp(Skill skill, double amount);

    // Stats
    double getBaseStat(Stat stat);
    void setBaseStat(Stat stat, double amount);
}
