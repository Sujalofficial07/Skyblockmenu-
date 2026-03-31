package com.skyblock.skills;

import org.bukkit.Material;

public enum SkillType {

    FARMING("Farming", Material.GOLDEN_HOE, "&6Farming", 100101),
    MINING("Mining", Material.STONE_PICKAXE, "&9Mining", 100102),
    COMBAT("Combat", Material.STONE_SWORD, "&cCombat", 100103),
    FISHING("Fishing", Material.FISHING_ROD, "&3Fishing", 100104),
    FORAGING("Foraging", Material.OAK_LOG, "&2Foraging", 100105),
    ENCHANTING("Enchanting", Material.ENCHANTING_TABLE, "&5Enchanting", 100106),
    ALCHEMY("Alchemy", Material.BREWING_STAND, "&dAlchemy", 100107),
    TAMING("Taming", Material.LEAD, "&aTaming", 100108),
    CARPENTRY("Carpentry", Material.CRAFTING_TABLE, "&6Carpentry", 100109),
    RUNECRAFTING("Runecrafting", Material.MAGMA_CREAM, "&dRunecrafting", 100110);

    private final String displayName;
    private final Material icon;
    private final String coloredName;
    private final int customModelData;

    SkillType(String displayName, Material icon, String coloredName, int customModelData) {
        this.displayName = displayName;
        this.icon = icon;
        this.coloredName = coloredName;
        this.customModelData = customModelData;
    }

    public String getDisplayName() { return displayName; }
    public Material getIcon() { return icon; }
    public String getColoredName() { return coloredName; }
    public int getCustomModelData() { return customModelData; }
    public String getId() { return name().toLowerCase(); }
}
