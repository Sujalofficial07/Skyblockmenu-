// Path: /skyblock-data/src/main/java/net/sujal/data/profile/MongoProfileManager.java
package net.sujal.data.profile;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.sujal.api.profile.ProfileManager;
import net.sujal.api.profile.SkyblockProfile;
import net.sujal.data.MongoConnection;
import org.bson.Document;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MongoProfileManager implements ProfileManager {

    private final MongoConnection mongoConnection;
    private MongoCollection<Document> collection;
    
    // Thread pool dedicated to database operations to keep them off the main server thread
    private final ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    
    // Fast in-memory cache
    private final Cache<UUID, SkyblockProfile> profileCache;

    public MongoProfileManager(MongoConnection mongoConnection) {
        this.mongoConnection = mongoConnection;
        this.profileCache = Caffeine.newBuilder()
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public void onEnable() {
        this.collection = mongoConnection.getDatabase().getCollection("profiles");
    }

    @Override
    public void onDisable() {
        // Save all cached profiles sequentially before shutdown
        for (UUID uuid : profileCache.asMap().keySet()) {
            saveProfileSync(uuid); 
        }
        dbExecutor.shutdown();
    }

    @Override
    public Optional<SkyblockProfile> getCachedProfile(UUID uuid) {
        return Optional.ofNullable(profileCache.getIfPresent(uuid));
    }

    @Override
    public CompletableFuture<SkyblockProfile> loadProfile(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            Document doc = collection.find(Filters.eq("uuid", uuid.toString())).first();
            
            SkyblockProfileImpl profile;
            if (doc != null) {
                profile = SkyblockProfileImpl.fromBson(doc);
            } else {
                profile = new SkyblockProfileImpl(uuid); // New player
            }
            
            profileCache.put(uuid, profile);
            return profile;
        }, dbExecutor);
    }

    @Override
    public CompletableFuture<Void> saveProfile(UUID uuid) {
        return CompletableFuture.runAsync(() -> saveProfileSync(uuid), dbExecutor);
    }
    
    public void unloadProfile(UUID uuid) {
        profileCache.invalidate(uuid);
    }

    private void saveProfileSync(UUID uuid) {
        SkyblockProfile profile = profileCache.getIfPresent(uuid);
        if (profile instanceof SkyblockProfileImpl impl) {
            Document doc = impl.toBson();
            collection.replaceOne(
                    Filters.eq("uuid", uuid.toString()), 
                    doc, 
                    new ReplaceOptions().upsert(true)
            );
        }
    }
}
