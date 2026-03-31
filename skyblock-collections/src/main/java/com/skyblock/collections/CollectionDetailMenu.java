package com.skyblock.collections;

import com.skyblock.database.CollectionTierCalculator;
import com.skyblock.menu.MenuManager;
import com.skyblock.menu.SkyBlockMenu;
import com.skyblock.utils.ColorUtil;
import com.skyblock.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CollectionDetailMenu extends SkyBlockMenu {

    private final CollectionType collectionType;
    private final CollectionCategory category;

    public CollectionDetailMenu(MenuManager menuManager, CollectionType collectionType, CollectionCategory category) {
        super(menuManager);
        this.collectionType = collectionType;
        this.category = category;
    }

    @Override
    public String getTitle() {
        return "&0" + collectionType.getDisplayName() + " Collection";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void buildContents(Player player) {
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, makeGlassPane(15));
        }

        CollectionManager cm = getCollectionManager();
        PlayerCollectionProfile profile = cm.getProfile(player.getUniqueId());
        CollectionData data = profile.getCollection(collectionType);
        long amount = data.getAmount();
        int tier = data.getTier();
        int maxTier = CollectionTierCalculator.getMaxTier(collectionType.getId());

        // Main collection icon at center top
        List<String> mainLore = new ArrayList<>();
        mainLore.add("");
        mainLore.add("&7Total Collected: &e" + ColorUtil.formatNumber(amount));
        mainLore.add("&7Current Tier: &6" + (tier > 0 ? ColorUtil.romanNumeral(tier) : "None") + " &7/ &6" + ColorUtil.romanNumeral(maxTier));
        mainLore.add("");
        if (tier < maxTier) {
            long toNext = CollectionTierCalculator.getAmountToNextTier(collectionType.getId(), amount);
            mainLore.add("&7Collect &e" + ColorUtil.formatNumber(toNext) + " more &7to reach Tier " + ColorUtil.romanNumeral(tier + 1));
        } else {
            mainLore.add("&6You have reached max tier!");
        }

        ItemStack mainItem = new ItemBuilder(collectionType.getIcon())
                .name("&e" + collectionType.getDisplayName() + " Collection")
                .lore(mainLore.toArray(new String[0]))
                .customModelData(collectionType.getCustomModelData())
                .hideFlags()
                .build();
        setItem(13, mainItem, null);

        // Tier display slots
        int[] tierSlots = {28, 29, 30, 31, 32, 33, 34, 37, 38};
        for (int t = 1; t <= maxTier && t <= tierSlots.length; t++) {
            boolean unlocked = tier >= t;
            long required = CollectionTierCalculator.getAmountForTier(collectionType.getId(), t);
            Material mat = unlocked ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;

            List<String> tierLore = new ArrayList<>();
            tierLore.add("");
            tierLore.add(unlocked ? "&aUnlocked!" : "&cLocked");
            tierLore.add("&7Required: &e" + ColorUtil.formatNumber(required) + " " + collectionType.getDisplayName());
            tierLore.add("");
            tierLore.addAll(getTierReward(collectionType, t));

            ItemStack tierItem = new ItemBuilder(mat)
                    .name((unlocked ? "&a" : "&c") + "Tier " + ColorUtil.romanNumeral(t))
                    .lore(tierLore.toArray(new String[0]))
                    .hideFlags()
                    .build();
            setItem(tierSlots[t - 1], tierItem, null);
        }

        // Progress bar display item
        double progress = maxTier > 0 ? (double) tier / maxTier : 0;
        String progressBar = ColorUtil.progressBar(progress, 1.0, 20, '█', '░', "&6", "&8");
        ItemStack progressItem = new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .name("&6Collection Progress")
                .lore(
                    "",
                    progressBar,
                    "&6Tier " + ColorUtil.romanNumeral(tier) + " &7/ &6Tier " + ColorUtil.romanNumeral(maxTier),
                    ""
                )
                .hideFlags()
                .build();
        setItem(22, progressItem, null);

        // Back button
        ItemStack backItem = new ItemBuilder(Material.ARROW)
                .name("&aGo Back")
                .lore("&7Return to " + category.getDisplayName() + " Collections", "", "&eClick to go back!")
                .hideFlags()
                .build();
        setItem(49, backItem, event -> {
            Player p = (Player) event.getWhoClicked();
            menuManager.openMenu(p, new CollectionCategoryMenu(menuManager, category));
        });

        // Close
        ItemStack closeItem = new ItemBuilder(Material.BARRIER)
                .name("&cClose")
                .lore("&7Close this menu.")
                .hideFlags()
                .build();
        setItem(53, closeItem, event -> event.getWhoClicked().closeInventory());
    }

    private List<String> getTierReward(CollectionType type, int tier) {
        List<String> rewards = new ArrayList<>();
        rewards.add("&7Reward:");
        switch (type) {
            case WHEAT:
                switch (tier) {
                    case 1 -> rewards.add("&e• Enchanted Bread Recipe");
                    case 2 -> rewards.add("&e• Hay Bale Minion I");
                    case 3 -> rewards.add("&e• Farm Suit Helmet Recipe");
                    case 4 -> rewards.add("&e• Enchanted Hay Bale Recipe");
                    case 5 -> rewards.add("&e• Wheat Minion I");
                    case 6 -> rewards.add("&e• +1 Farming Fortune");
                    case 7 -> rewards.add("&e• Farmer's Orb Recipe");
                    case 8 -> rewards.add("&e• +2 Farming Fortune");
                    case 9 -> rewards.add("&e• Tier IX Reward");
                    default -> rewards.add("&e• Unknown Reward");
                }
                break;
            case COAL:
                switch (tier) {
                    case 1 -> rewards.add("&e• Coal Minion I");
                    case 2 -> rewards.add("&e• Enchanted Coal Recipe");
                    case 3 -> rewards.add("&e• +1 Mining Fortune");
                    case 4 -> rewards.add("&e• Coal Minion II");
                    case 5 -> rewards.add("&e• +2 Mining Fortune");
                    case 6 -> rewards.add("&e• Coal Minion III");
                    case 7 -> rewards.add("&e• Tier VII Reward");
                    case 8 -> rewards.add("&e• Tier VIII Reward");
                    case 9 -> rewards.add("&e• Tier IX Reward");
                    default -> rewards.add("&e• Unknown Reward");
                }
                break;
            case DIAMOND:
                switch (tier) {
                    case 1 -> rewards.add("&e• Diamond Minion I");
                    case 2 -> rewards.add("&e• Enchanted Diamond Recipe");
                    case 3 -> rewards.add("&e• Diamond Spreading Recipe");
                    case 4 -> rewards.add("&e• +2 Mining Fortune");
                    case 5 -> rewards.add("&e• Promising Pickaxe Recipe");
                    case 6 -> rewards.add("&e• Diamond Minion V");
                    case 7 -> rewards.add("&e• Tier VII Reward");
                    case 8 -> rewards.add("&e• Tier VIII Reward");
                    case 9 -> rewards.add("&e• Tier IX Reward");
                    default -> rewards.add("&e• Unknown Reward");
                }
                break;
            case ROTTEN_FLESH:
                switch (tier) {
                    case 1 -> rewards.add("&e• Zombie Minion I");
                    case 2 -> rewards.add("&e• Enchanted Rotten Flesh Recipe");
                    case 3 -> rewards.add("&e• +1 Combat XP");
                    case 4 -> rewards.add("&e• Zombie Minion V");
                    case 5 -> rewards.add("&e• Tier V Reward");
                    case 6 -> rewards.add("&e• Tier VI Reward");
                    case 7 -> rewards.add("&e• Tier VII Reward");
                    case 8 -> rewards.add("&e• Tier VIII Reward");
                    case 9 -> rewards.add("&e• Tier IX Reward");
                    default -> rewards.add("&e• Unknown Reward");
                }
                break;
            default:
                rewards.add("&e• " + type.getDisplayName() + " Tier " + tier + " Reward");
                break;
        }
        return rewards;
    }

    private CollectionManager getCollectionManager() {
        return (CollectionManager) menuManager.getPlugin().getServer()
                .getServicesManager()
                .load(CollectionManager.class);
    }
}
