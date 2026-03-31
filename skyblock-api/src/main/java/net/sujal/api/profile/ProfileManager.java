// Path: /skyblock-api/src/main/java/net/sujal/api/profile/ProfileManager.java
package net.sujal.api.profile;

import net.sujal.api.service.Service;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ProfileManager extends Service {
    
    /**
     * Retrieves a loaded profile, or empty if not currently cached.
     */
    Optional<SkyblockProfile> getCachedProfile(UUID uuid);

    /**
     * Asynchronously loads a profile from the database, caches it, and returns it.
     */
    CompletableFuture<SkyblockProfile> loadProfile(UUID uuid);

    /**
     * Asynchronously saves the profile to the database.
     */
    CompletableFuture<Void> saveProfile(UUID uuid);
}
