package com.skyblock.skills;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.Plugin;

import java.util.EnumMap;
import java.util.Map;

public class SkillXpListener implements Listener {

    private final SkillManager skillManager;
    private final Plugin plugin;

    private static final Map<Material, Double> FARMING_XP = new EnumMap<>(Material.class);
    private static final Map<Material, Double> MINING_XP = new EnumMap<>(Material.class);
    private static final Map<Material, Double> FORAGING_XP = new EnumMap<>(Material.class);
    private static final Map<EntityType, Double> COMBAT_XP = new EnumMap<>(EntityType.class);

    static {
        FARMING_XP.put(Material.WHEAT, 3.0);
        FARMING_XP.put(Material.CARROTS, 3.0);
        FARMING_XP.put(Material.POTATOES, 3.0);
        FARMING_XP.put(Material.PUMPKIN, 3.0);
        FARMING_XP.put(Material.MELON, 3.0);
        FARMING_XP.put(Material.COCOA, 3.0);
        FARMING_XP.put(Material.CACTUS, 3.0);
        FARMING_XP.put(Material.SUGAR_CANE, 3.0);
        FARMING_XP.put(Material.NETHER_WART, 5.0);
        FARMING_XP.put(Material.BROWN_MUSHROOM, 3.0);
        FARMING_XP.put(Material.RED_MUSHROOM, 3.0);

        MINING_XP.put(Material.STONE, 1.0);
        MINING_XP.put(Material.COBBLESTONE, 1.0);
        MINING_XP.put(Material.COAL_ORE, 5.0);
        MINING_XP.put(Material.DEEPSLATE_COAL_ORE, 5.5);
        MINING_XP.put(Material.IRON_ORE, 10.0);
        MINING_XP.put(Material.DEEPSLATE_IRON_ORE, 11.0);
        MINING_XP.put(Material.GOLD_ORE, 18.0);
        MINING_XP.put(Material.DEEPSLATE_GOLD_ORE, 19.8);
        MINING_XP.put(Material.LAPIS_ORE, 21.0);
        MINING_XP.put(Material.DEEPSLATE_LAPIS_ORE, 23.1);
        MINING_XP.put(Material.REDSTONE_ORE, 27.5);
        MINING_XP.put(Material.DEEPSLATE_REDSTONE_ORE, 30.0);
        MINING_XP.put(Material.DIAMOND_ORE, 46.5);
        MINING_XP.put(Material.DEEPSLATE_DIAMOND_ORE, 51.0);
        MINING_XP.put(Material.EMERALD_ORE, 65.5);
        MINING_XP.put(Material.DEEPSLATE_EMERALD_ORE, 72.0);
        MINING_XP.put(Material.NETHER_QUARTZ_ORE, 12.0);
        MINING_XP.put(Material.OBSIDIAN, 6.9);
        MINING_XP.put(Material.GRAVEL, 1.0);
        MINING_XP.put(Material.SAND, 1.0);
        MINING_XP.put(Material.SANDSTONE, 2.0);
        MINING_XP.put(Material.END_STONE, 2.5);
        MINING_XP.put(Material.NETHERRACK, 1.5);
        MINING_XP.put(Material.GLOWSTONE, 4.0);

        FORAGING_XP.put(Material.OAK_LOG, 6.0);
        FORAGING_XP.put(Material.SPRUCE_LOG, 6.0);
        FORAGING_XP.put(Material.BIRCH_LOG, 6.0);
        FORAGING_XP.put(Material.JUNGLE_LOG, 6.0);
        FORAGING_XP.put(Material.ACACIA_LOG, 6.0);
        FORAGING_XP.put(Material.DARK_OAK_LOG, 6.0);
        FORAGING_XP.put(Material.MANGROVE_LOG, 6.0);
        FORAGING_XP.put(Material.CHERRY_LOG, 6.0);

        COMBAT_XP.put(EntityType.ZOMBIE, 2.0);
        COMBAT_XP.put(EntityType.SKELETON, 2.0);
        COMBAT_XP.put(EntityType.SPIDER, 2.0);
        COMBAT_XP.put(EntityType.CREEPER, 4.0);
        COMBAT_XP.put(EntityType.WITCH, 6.0);
        COMBAT_XP.put(EntityType.ENDERMAN, 8.0);
        COMBAT_XP.put(EntityType.BLAZE, 10.0);
        COMBAT_XP.put(EntityType.GHAST, 10.0);
        COMBAT_XP.put(EntityType.WITHER_SKELETON, 14.0);
        COMBAT_XP.put(EntityType.GUARDIAN, 14.0);
        COMBAT_XP.put(EntityType.ELDER_GUARDIAN, 40.0);
        COMBAT_XP.put(EntityType.WITHER, 200.0);
        COMBAT_XP.put(EntityType.ENDER_DRAGON, 200.0);
        COMBAT_XP.put(EntityType.SLIME, 1.5);
        COMBAT_XP.put(EntityType.MAGMA_CUBE, 2.0);
        COMBAT_XP.put(EntityType.SILVERFISH, 1.0);
        COMBAT_XP.put(EntityType.CAVE_SPIDER, 2.5);
        COMBAT_XP.put(EntityType.PIG_ZOMBIE, 5.0);
    }

    public SkillXpListener(SkillManager skillManager, Plugin plugin) {
        this.skillManager = skillManager;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material mat = event.getBlock().getType();

        if (FARMING_XP.containsKey(mat)) {
            skillManager.addXp(player, SkillType.FARMING, FARMING_XP.get(mat));
        } else if (MINING_XP.containsKey(mat)) {
            skillManager.addXp(player, SkillType.MINING, MINING_XP.get(mat));
        } else if (FORAGING_XP.containsKey(mat)) {
            skillManager.addXp(player, SkillType.FORAGING, FORAGING_XP.get(mat));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player player = event.getEntity().getKiller();
        EntityType type = event.getEntityType();
        Double xp = COMBAT_XP.get(type);
        if (xp != null) {
            skillManager.addXp(player, SkillType.COMBAT, xp);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH &&
            event.getState() != PlayerFishEvent.State.CAUGHT_ENTITY) return;
        Player player = event.getPlayer();
        skillManager.addXp(player, SkillType.FISHING, 6.0);
    }
}
