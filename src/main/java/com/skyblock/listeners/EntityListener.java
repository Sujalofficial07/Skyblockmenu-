package com.skyblock.listeners;

import com.skyblock.collections.CollectionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;

        CollectionManager.add(e.getEntity().getKiller().getUniqueId(), "combat", 1);
    }
}
