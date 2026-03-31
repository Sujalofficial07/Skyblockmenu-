// Path: /skyblock-skills/src/main/java/net/sujal/skills/listener/StatUpdateListener.java
package net.sujal.skills.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import net.sujal.skills.engine.StatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class StatUpdateListener implements Listener {

    private final StatManager statManager;

    public StatUpdateListener(StatManager statManager) {
        this.statManager = statManager;
    }

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {
        statManager.updatePlayerAttributes(event.getPlayer());
    }

    @EventHandler
    public void onHandItemChange(PlayerItemHeldEvent event) {
        // Schedule sync task so item is actually in hand when calculated
        event.getPlayer().getServer().getScheduler().runTask(
                // Plugin instance required here, pass via constructor ideally
                org.bukkit.plugin.java.JavaPlugin.getProvidingPlugin(StatManager.class),
                () -> statManager.updatePlayerAttributes(event.getPlayer())
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        statManager.updatePlayerAttributes(event.getPlayer());
    }
}
