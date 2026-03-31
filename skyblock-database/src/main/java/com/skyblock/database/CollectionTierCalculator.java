package com.skyblock.database;

public final class CollectionTierCalculator {

    private CollectionTierCalculator() {}

    private static final long[] FARMING_TIERS = {50, 100, 250, 500, 1000, 2500, 5000, 10000, 50000};
    private static final long[] MINING_TIERS = {50, 100, 250, 500, 1000, 2500, 5000, 10000, 50000};
    private static final long[] COMBAT_TIERS = {50, 100, 250, 500, 1000, 2500, 5000, 10000, 50000};
    private static final long[] FISHING_TIERS = {20, 50, 100, 250, 500, 1000, 2500, 5000, 25000};
    private static final long[] FORAGING_TIERS = {50, 100, 250, 500, 1000, 2500, 5000, 10000, 50000};
    private static final long[] DEFAULT_TIERS = {50, 100, 250, 500, 1000, 2500, 5000, 10000, 50000};

    public static int calculateTier(String collectionType, long amount) {
        long[] tiers = getTiers(collectionType);
        int tier = 0;
        for (long threshold : tiers) {
            if (amount >= threshold) tier++;
            else break;
        }
        return tier;
    }

    public static long getAmountForTier(String collectionType, int tier) {
        long[] tiers = getTiers(collectionType);
        if (tier <= 0) return 0;
        if (tier > tiers.length) return tiers[tiers.length - 1];
        return tiers[tier - 1];
    }

    public static int getMaxTier(String collectionType) {
        return getTiers(collectionType).length;
    }

    public static long getAmountToNextTier(String collectionType, long currentAmount) {
        int currentTier = calculateTier(collectionType, currentAmount);
        long[] tiers = getTiers(collectionType);
        if (currentTier >= tiers.length) return 0;
        return tiers[currentTier] - currentAmount;
    }

    private static long[] getTiers(String collectionType) {
        String lower = collectionType.toLowerCase();
        if (lower.contains("wheat") || lower.contains("carrot") || lower.contains("potato") ||
            lower.contains("pumpkin") || lower.contains("melon") || lower.contains("mushroom") ||
            lower.contains("cocoa") || lower.contains("cactus") || lower.contains("sugar_cane") ||
            lower.contains("feather") || lower.contains("leather") || lower.contains("ink") ||
            lower.contains("egg") || lower.contains("nether_wart")) {
            return FARMING_TIERS;
        }
        if (lower.contains("cobblestone") || lower.contains("coal") || lower.contains("iron") ||
            lower.contains("gold") || lower.contains("diamond") || lower.contains("lapis") ||
            lower.contains("emerald") || lower.contains("redstone") || lower.contains("quartz") ||
            lower.contains("obsidian") || lower.contains("glowstone") || lower.contains("gravel") ||
            lower.contains("sand") || lower.contains("endstone") || lower.contains("netherrack") ||
            lower.contains("ice") || lower.contains("mithril") || lower.contains("hard_stone")) {
            return MINING_TIERS;
        }
        if (lower.contains("rotten_flesh") || lower.contains("bone") || lower.contains("string") ||
            lower.contains("spider_eye") || lower.contains("gunpowder") || lower.contains("ender_pearl") ||
            lower.contains("ghast_tear") || lower.contains("slime") || lower.contains("blaze_rod") ||
            lower.contains("magma_cream") || lower.contains("zombie") || lower.contains("skeleton") ||
            lower.contains("creeper") || lower.contains("enderman") || lower.contains("wither")) {
            return COMBAT_TIERS;
        }
        if (lower.contains("raw_fish") || lower.contains("salmon") || lower.contains("clownfish") ||
            lower.contains("pufferfish") || lower.contains("prismarine") || lower.contains("clay") ||
            lower.contains("lily_pad") || lower.contains("ink_sac") || lower.contains("sponge") ||
            lower.contains("magma_fish") || lower.contains("sea_creature")) {
            return FISHING_TIERS;
        }
        if (lower.contains("oak") || lower.contains("spruce") || lower.contains("birch") ||
            lower.contains("jungle") || lower.contains("acacia") || lower.contains("dark_oak") ||
            lower.contains("apple") || lower.contains("stick")) {
            return FORAGING_TIERS;
        }
        return DEFAULT_TIERS;
    }
}
