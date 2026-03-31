// Path: /skyblock-api/src/main/java/net/sujal/api/profile/SkyblockProfile.java
package net.sujal.api.profile;

import java.util.UUID;

/**
 * Represents a player's data profile.
 */
public interface SkyblockProfile {
    UUID getPlayerUuid();
    
    double getCoins();
    void setCoins(double amount);
    void addCoins(double amount);
    void removeCoins(double amount);
    
    // Future expansion: Stats, Skills, Collections will be accessible here.
}
