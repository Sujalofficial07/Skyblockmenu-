// Path: /skyblock-data/src/main/java/net/sujal/data/listener/ProfileListener.java
package net.sujal.data.listener;

import net.sujal.api.profile.ProfileManager;
import net.sujal.data.profile.MongoProfileManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ProfileListener implements Listener {

    private final MongoProfileManager profileManager;

    public ProfileListener(ProfileManager profileManager) {
        this.profileManager = (MongoProfileManager) profileManager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        
        try {
            // Wait up to 5 seconds for the DB to respond
            profileManager.loadProfile(uuid).get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            // Disallow login to prevent creating a fresh profile and overwriting their real data
            event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                    Component.text("Failed to load your Skyblock profile. Please try again or contact an administrator.", NamedTextColor.RED)
            );
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        
        // Trigger async save, then invalidate cache once completed
        profileManager.saveProfile(uuid).thenRun(() -> {
            profileManager.unloadProfile(uuid);
        });
    }
}
