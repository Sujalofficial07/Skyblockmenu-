package com.skyblock.collections;

import org.bukkit.Material;
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

public class CollectionListener implements Listener {

    private final CollectionManager collectionManager;
    private final Plugin plugin;

    private static final Map<Material, CollectionType> BLOCK_COLLECTIONS = new EnumMap<>(Material.class);

    static {
        BLOCK_COLLECTIONS.put(Material.WHEAT, CollectionType.WHEAT);
        BLOCK_COLLECTIONS.put(Material.CARROTS, CollectionType.CARROT);
        BLOCK_COLLECTIONS.put(Material.POTATOES, CollectionType.POTATO);
        BLOCK_COLLECTIONS.put(Material.PUMPKIN, CollectionType.PUMPKIN);
        BLOCK_COLLECTIONS.put(Material.MELON, CollectionType.MELON);
        BLOCK_COLLECTIONS.put(Material.BROWN_MUSHROOM, CollectionType.MUSHROOM);
        BLOCK_COLLECTIONS.put(Material.RED_MUSHROOM, CollectionType.MUSHROOM);
        BLOCK_COLLECTIONS.put(Material.CACTUS, CollectionType.CACTUS);
        BLOCK_COLLECTIONS.put(Material.SUGAR_CANE, CollectionType.SUGAR_CANE);
        BLOCK_COLLECTIONS.put(Material.NETHER_WART, CollectionType.NETHER_WART);
        BLOCK_COLLECTIONS.put(Material.COCOA, CollectionType.COCOA_BEANS);

        BLOCK_COLLECTIONS.put(Material.STONE, CollectionType.COBBLESTONE);
        BLOCK_COLLECTIONS.put(Material.COBBLESTONE, CollectionType.COBBLESTONE);
        BLOCK_COLLECTIONS.put(Material.COAL_ORE, CollectionType.COAL);
        BLOCK_COLLECTIONS.put(Material.DEEPSLATE_COAL_ORE, CollectionType.COAL);
        BLOCK_COLLECTIONS.put(Material.IRON_ORE, CollectionType.IRON_INGOT);
        BLOCK_COLLECTIONS.put(Material.DEEPSLATE_IRON_ORE, CollectionType.IRON_INGOT);
        BLOCK_COLLECTIONS.put(Material.GOLD_ORE, CollectionType.GOLD_INGOT);
        BLOCK_COLLECTIONS.put(Material.DEEPSLATE_GOLD_ORE, CollectionType.GOLD_INGOT);
        BLOCK_COLLECTIONS.put(Material.DIAMOND_ORE, CollectionType.DIAMOND);
        BLOCK_COLLECTIONS.put(Material.DEEPSLATE_DIAMOND_ORE, CollectionType.DIAMOND);
        BLOCK_COLLECTIONS.put(Material.LAPIS_ORE, CollectionType.LAPIS_LAZULI);
        BLOCK_COLLECTIONS.put(Material.DEEPSLATE_LAPIS_ORE, CollectionType.LAPIS_LAZULI);
        BLOCK_COLLECTIONS.put(Material.EMERALD_ORE, CollectionType.EMERALD);
        BLOCK_COLLECTIONS.put(Material.DEEPSLATE_EMERALD_ORE, CollectionType.EMERALD);
        BLOCK_COLLECTIONS.put(Material.REDSTONE_ORE, CollectionType.REDSTONE);
        BLOCK_COLLECTIONS.put(Material.DEEPSLATE_REDSTONE_ORE, CollectionType.REDSTONE);
        BLOCK_COLLECTIONS.put(Material.NETHER_QUARTZ_ORE, CollectionType.QUARTZ);
        BLOCK_COLLECTIONS.put(Material.OBSIDIAN, CollectionType.OBSIDIAN);
        BLOCK_COLLECTIONS.put(Material.GLOWSTONE, CollectionType.GLOWSTONE_DUST);
        BLOCK_COLLECTIONS.put(Material.GRAVEL, CollectionType.GRAVEL);
        BLOCK_COLLECTIONS.put(Material.SAND, CollectionType.SAND);
        BLOCK_COLLECTIONS.put(Material.RED_SAND, CollectionType.SAND);
        BLOCK_COLLECTIONS.put(Material.END_STONE, CollectionType.ENDSTONE);
        BLOCK_COLLECTIONS.put(Material.NETHERRACK, CollectionType.NETHERRACK);
        BLOCK_COLLECTIONS.put(Material.ICE, CollectionType.ICE);
        BLOCK_COLLECTIONS.put(Material.PACKED_ICE, CollectionType.ICE);

        BLOCK_COLLECTIONS.put(Material.OAK_LOG, CollectionType.OAK_WOOD);
        BLOCK_COLLECTIONS.put(Material.SPRUCE_LOG, CollectionType.SPRUCE_WOOD);
        BLOCK_COLLECTIONS.put(Material.BIRCH_LOG, CollectionType.BIRCH_WOOD);
        BLOCK_COLLECTIONS.put(Material.JUNGLE_LOG, CollectionType.JUNGLE_WOOD);
        BLOCK_COLLECTIONS.put(Material.ACACIA_LOG, CollectionType.ACACIA_WOOD);
        BLOCK_COLLECTIONS.put(Material.DARK_OAK_LOG, CollectionType.DARK_OAK_WOOD);
    }

    public CollectionListener(CollectionManager collectionManager, Plugin plugin) {
        this.collectionManager = collectionManager;
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material mat = event.getBlock().getType();
        CollectionType type = BLOCK_COLLECTIONS.get(mat);
        if (type != null) {
            collectionManager.addCollection(player, type, 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player player = event.getEntity().getKiller();
        switch (event.getEntityType()) {
            case ZOMBIE -> collectionManager.addCollection(player, CollectionType.ROTTEN_FLESH, 1);
            case SKELETON -> collectionManager.addCollection(player, CollectionType.BONE, 1);
            case SPIDER, CAVE_SPIDER -> collectionManager.addCollection(player, CollectionType.STRING, 1);
            case CREEPER -> collectionManager.addCollection(player, CollectionType.GUNPOWDER, 1);
            case ENDERMAN -> collectionManager.addCollection(player, CollectionType.ENDER_PEARL, 1);
            case GHAST -> collectionManager.addCollection(player, CollectionType.GHAST_TEAR, 1);
            case SLIME -> collectionManager.addCollection(player, CollectionType.SLIMEBALL, 1);
            case BLAZE -> collectionManager.addCollection(player, CollectionType.BLAZE_ROD, 1);
            case MAGMA_CUBE -> collectionManager.addCollection(player, CollectionType.MAGMA_CREAM, 1);
            case WITHER_SKELETON -> collectionManager.addCollection(player, CollectionType.BONE, 2);
            default -> {}
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        Player player = event.getPlayer();
        if (event.getCaught() == null) return;
        if (event.getCaught() instanceof org.bukkit.entity.Item caughtItem) {
            Material mat = caughtItem.getItemStack().getType();
            switch (mat) {
                case COD -> collectionManager.addCollection(player, CollectionType.RAW_FISH, 1);
                case SALMON -> collectionManager.addCollection(player, CollectionType.RAW_SALMON, 1);
                case TROPICAL_FISH -> collectionManager.addCollection(player, CollectionType.CLOWNFISH, 1);
                case PUFFERFISH -> collectionManager.addCollection(player, CollectionType.PUFFERFISH, 1);
                case LILY_PAD -> collectionManager.addCollection(player, CollectionType.LILY_PAD, 1);
                case PRISMARINE_SHARD -> collectionManager.addCollection(player, CollectionType.PRISMARINE_SHARD, 1);
                case PRISMARINE_CRYSTALS -> collectionManager.addCollection(player, CollectionType.PRISMARINE_CRYSTALS, 1);
                case CLAY_BALL -> collectionManager.addCollection(player, CollectionType.CLAY_BALL, 1);
                case SPONGE -> collectionManager.addCollection(player, CollectionType.SPONGE, 1);
                default -> {}
            }
        }
    }
}
