package com.skyblock.collections;

import org.bukkit.Material;

public enum CollectionCategory {
    FARMING("Farming", Material.GOLDEN_HOE, "&6Farming Collections", 200001),
    MINING("Mining", Material.STONE_PICKAXE, "&9Mining Collections", 200002),
    COMBAT("Combat", Material.STONE_SWORD, "&cCombat Collections", 200003),
    FISHING("Fishing", Material.FISHING_ROD, "&3Fishing Collections", 200004),
    FORAGING("Foraging", Material.OAK_LOG, "&2Foraging Collections", 200005);

    private final String displayName;
    private final Material icon;
    private final String coloredName;
    private final int customModelData;

    CollectionCategory(String displayName, Material icon, String coloredName, int customModelData) {
        this.displayName = displayName;
        this.icon = icon;
        this.coloredName = coloredName;
        this.customModelData = customModelData;
    }

    public String getDisplayName() { return displayName; }
    public Material getIcon() { return icon; }
    public String getColoredName() { return coloredName; }
    public int getCustomModelData() { return customModelData; }
}
