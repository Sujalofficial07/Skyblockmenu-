package com.skyblock.listeners;

import com.skyblock.skills.SkillManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;
import java.util.Set;

public class BlockListener implements Listener {

    private static final Set<String> placedBlocks = new HashSet<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        placedBlocks.add(e.getBlock().getLocation().toString());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        String loc = e.getBlock().getLocation().toString();

        if (placedBlocks.contains(loc)) return;

        SkillManager.addXP(e.getPlayer().getUniqueId(), "mining", 10);
    }
}
