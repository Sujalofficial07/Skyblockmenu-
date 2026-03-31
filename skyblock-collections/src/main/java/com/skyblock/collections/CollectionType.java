package com.skyblock.collections;

import org.bukkit.Material;

public enum CollectionType {

    // Farming
    WHEAT("Wheat", Material.WHEAT, CollectionCategory.FARMING, 201001),
    CARROT("Carrot", Material.CARROT, CollectionCategory.FARMING, 201002),
    POTATO("Potato", Material.POTATO, CollectionCategory.FARMING, 201003),
    PUMPKIN("Pumpkin", Material.PUMPKIN, CollectionCategory.FARMING, 201004),
    MELON("Melon", Material.MELON_SLICE, CollectionCategory.FARMING, 201005),
    MUSHROOM("Mushroom", Material.BROWN_MUSHROOM, CollectionCategory.FARMING, 201006),
    COCOA_BEANS("Cocoa Beans", Material.COCOA_BEANS, CollectionCategory.FARMING, 201007),
    CACTUS("Cactus", Material.CACTUS, CollectionCategory.FARMING, 201008),
    SUGAR_CANE("Sugar Cane", Material.SUGAR_CANE, CollectionCategory.FARMING, 201009),
    FEATHER("Feather", Material.FEATHER, CollectionCategory.FARMING, 201010),
    LEATHER("Leather", Material.LEATHER, CollectionCategory.FARMING, 201011),
    RAW_RABBIT("Raw Rabbit", Material.RABBIT, CollectionCategory.FARMING, 201012),
    RAW_CHICKEN("Raw Chicken", Material.CHICKEN, CollectionCategory.FARMING, 201013),
    RAW_PORK("Raw Pork", Material.PORKCHOP, CollectionCategory.FARMING, 201014),
    RAW_BEEF("Raw Beef", Material.BEEF, CollectionCategory.FARMING, 201015),
    MUTTON("Mutton", Material.MUTTON, CollectionCategory.FARMING, 201016),
    NETHER_WART("Nether Wart", Material.NETHER_WART, CollectionCategory.FARMING, 201017),
    INK_SAC("Ink Sac", Material.INK_SAC, CollectionCategory.FARMING, 201018),
    EGG("Egg", Material.EGG, CollectionCategory.FARMING, 201019),

    // Mining
    COBBLESTONE("Cobblestone", Material.COBBLESTONE, CollectionCategory.MINING, 202001),
    COAL("Coal", Material.COAL, CollectionCategory.MINING, 202002),
    IRON_INGOT("Iron Ingot", Material.IRON_INGOT, CollectionCategory.MINING, 202003),
    GOLD_INGOT("Gold Ingot", Material.GOLD_INGOT, CollectionCategory.MINING, 202004),
    DIAMOND("Diamond", Material.DIAMOND, CollectionCategory.MINING, 202005),
    LAPIS_LAZULI("Lapis Lazuli", Material.LAPIS_LAZULI, CollectionCategory.MINING, 202006),
    EMERALD("Emerald", Material.EMERALD, CollectionCategory.MINING, 202007),
    REDSTONE("Redstone", Material.REDSTONE, CollectionCategory.MINING, 202008),
    QUARTZ("Nether Quartz", Material.QUARTZ, CollectionCategory.MINING, 202009),
    OBSIDIAN("Obsidian", Material.OBSIDIAN, CollectionCategory.MINING, 202010),
    GLOWSTONE_DUST("Glowstone Dust", Material.GLOWSTONE_DUST, CollectionCategory.MINING, 202011),
    GRAVEL("Gravel", Material.GRAVEL, CollectionCategory.MINING, 202012),
    ICE("Ice", Material.ICE, CollectionCategory.MINING, 202013),
    NETHERRACK("Netherrack", Material.NETHERRACK, CollectionCategory.MINING, 202014),
    SAND("Sand", Material.SAND, CollectionCategory.MINING, 202015),
    ENDSTONE("End Stone", Material.END_STONE, CollectionCategory.MINING, 202016),
    MITHRIL_ORE("Mithril", Material.PRISMARINE_SHARD, CollectionCategory.MINING, 202017),
    HARD_STONE("Hard Stone", Material.STONE, CollectionCategory.MINING, 202018),

    // Combat
    ROTTEN_FLESH("Rotten Flesh", Material.ROTTEN_FLESH, CollectionCategory.COMBAT, 203001),
    BONE("Bone", Material.BONE, CollectionCategory.COMBAT, 203002),
    STRING("String", Material.STRING, CollectionCategory.COMBAT, 203003),
    SPIDER_EYE("Spider Eye", Material.SPIDER_EYE, CollectionCategory.COMBAT, 203004),
    GUNPOWDER("Gunpowder", Material.GUNPOWDER, CollectionCategory.COMBAT, 203005),
    ENDER_PEARL("Ender Pearl", Material.ENDER_PEARL, CollectionCategory.COMBAT, 203006),
    GHAST_TEAR("Ghast Tear", Material.GHAST_TEAR, CollectionCategory.COMBAT, 203007),
    SLIMEBALL("Slimeball", Material.SLIME_BALL, CollectionCategory.COMBAT, 203008),
    BLAZE_ROD("Blaze Rod", Material.BLAZE_ROD, CollectionCategory.COMBAT, 203009),
    MAGMA_CREAM("Magma Cream", Material.MAGMA_CREAM, CollectionCategory.COMBAT, 203010),
    ENCHANTED_EGG("Enchanted Egg", Material.EGG, CollectionCategory.COMBAT, 203011),
    NETHER_STAR("Nether Star", Material.NETHER_STAR, CollectionCategory.COMBAT, 203012),

    // Fishing
    RAW_FISH("Raw Fish", Material.COD, CollectionCategory.FISHING, 204001),
    RAW_SALMON("Raw Salmon", Material.SALMON, CollectionCategory.FISHING, 204002),
    CLOWNFISH("Clownfish", Material.TROPICAL_FISH, CollectionCategory.FISHING, 204003),
    PUFFERFISH("Pufferfish", Material.PUFFERFISH, CollectionCategory.FISHING, 204004),
    PRISMARINE_SHARD("Prismarine Shard", Material.PRISMARINE_SHARD, CollectionCategory.FISHING, 204005),
    PRISMARINE_CRYSTALS("Prismarine Crystals", Material.PRISMARINE_CRYSTALS, CollectionCategory.FISHING, 204006),
    CLAY_BALL("Clay", Material.CLAY_BALL, CollectionCategory.FISHING, 204007),
    LILY_PAD("Lily Pad", Material.LILY_PAD, CollectionCategory.FISHING, 204008),
    SPONGE("Sponge", Material.SPONGE, CollectionCategory.FISHING, 204009),
    MAGMA_FISH("Magma Fish", Material.MAGMA_BLOCK, CollectionCategory.FISHING, 204010),

    // Foraging
    OAK_WOOD("Oak Wood", Material.OAK_LOG, CollectionCategory.FORAGING, 205001),
    SPRUCE_WOOD("Spruce Wood", Material.SPRUCE_LOG, CollectionCategory.FORAGING, 205002),
    BIRCH_WOOD("Birch Wood", Material.BIRCH_LOG, CollectionCategory.FORAGING, 205003),
    JUNGLE_WOOD("Jungle Wood", Material.JUNGLE_LOG, CollectionCategory.FORAGING, 205004),
    ACACIA_WOOD("Acacia Wood", Material.ACACIA_LOG, CollectionCategory.FORAGING, 205005),
    DARK_OAK_WOOD("Dark Oak Wood", Material.DARK_OAK_LOG, CollectionCategory.FORAGING, 205006);

    private final String displayName;
    private final Material icon;
    private final CollectionCategory category;
    private final int customModelData;

    CollectionType(String displayName, Material icon, CollectionCategory category, int customModelData) {
        this.displayName = displayName;
        this.icon = icon;
        this.category = category;
        this.customModelData = customModelData;
    }

    public String getDisplayName() { return displayName; }
    public Material getIcon() { return icon; }
    public CollectionCategory getCategory() { return category; }
    public int getCustomModelData() { return customModelData; }
    public String getId() { return name().toLowerCase(); }
}
