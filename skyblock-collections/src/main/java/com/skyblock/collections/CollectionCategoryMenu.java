package com.skyblock.collections;

import com.skyblock.database.CollectionTierCalculator;
import com.skyblock.menu.MenuManager;
import com.skyblock.menu.SkyBlockMenu;
import com.skyblock.utils.ColorUtil;
import com.skyblock.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CollectionCategoryMenu extends SkyBlockMenu {

    private final CollectionCategory category;

    public CollectionCategoryMenu(MenuManager menuManager, CollectionCategory category) {
        super(menuManager);
        this.category = category;
    }

    @Override
    public String getTitle() {
        return "&0" + category.getDisplayName() + " Collections";
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
        Map<CollectionType, CollectionData> catCollections = profile.getByCategory(category);

        List<CollectionType> types = new ArrayList<>(catCollections.keySet());
        types.sort((a, b) -> a.getDisplayName().compareTo(b.getDisplayName()));

        int[] contentSlots = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
        };

        for (int i = 0; i < types.size() && i < contentSlots.length; i++) {
            CollectionType type = types.get(i);
            CollectionData data = catCollections.get(type);
            ItemStack item = buildCollectionItem(type, data);
            setItem(contentSlots[i], item, event -> {
                Player p = (Player) event.getWhoClicked();
                menuManager.openMenu(p, new CollectionDetailMenu(menuManager, type, category));
            });
        }

        // Category header
        ItemStack header = new ItemBuilder(category.getIcon())
                .name(category.getColoredName())
                .lore("&7Browse and track your", "&7" + category.getDisplayName().toLowerCase() + " collections.")
                .customModelData(category.getCustomModelData())
                .hideFlags()
                .build();
        setItem(4, header, null);

        // Back button
        ItemStack backItem = new ItemBuilder(Material.ARROW)
                .name("&aGo Back")
                .lore("&7Return to Collections Menu", "", "&eClick to go back!")
                .hideFlags()
                .build();
        setItem(49, backItem, event -> {
            Player p = (Player) event.getWhoClicked();
            menuManager.openMenu(p, new CollectionsMenu(menuManager));
        });

        // Close
        ItemStack closeItem = new ItemBuilder(Material.BARRIER)
                .name("&cClose")
                .lore("&7Close this menu.")
                .hideFlags()
                .build();
        setItem(53, closeItem, event -> event.getWhoClicked().closeInventory());
    }

    private ItemStack buildCollectionItem(CollectionType type, CollectionData data) {
        long amount = data.getAmount();
        int tier = data.getTier();
        int maxTier = CollectionTierCalculator.getMaxTier(type.getId());

        String tierDisplay = tier > 0 ? "&6Tier " + ColorUtil.romanNumeral(tier) : "&7Not Started";

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Amount: &e" + ColorUtil.formatNumber(amount));
        lore.add("&7Tier: " + tierDisplay + " &8/ &6" + ColorUtil.romanNumeral(maxTier));
        lore.add("");

        if (tier < maxTier) {
            long toNext = CollectionTierCalculator.getAmountToNextTier(type.getId(), amount);
            lore.add("&7To next tier: &e" + ColorUtil.formatNumber(toNext) + " more");
        } else {
            lore.add("&6Max Tier Reached!");
        }

        lore.add("");
        lore.add("&eClick for details!");

        Material mat = tier > 0 ? type.getIcon() : getLockedMaterial();

        return new ItemBuilder(mat)
                .name((tier > 0 ? "&e" : "&7") + type.getDisplayName())
                .lore(lore.toArray(new String[0]))
                .customModelData(type.getCustomModelData())
                .hideFlags()
                .build();
    }

    private Material getLockedMaterial() {
        return Material.GRAY_STAINED_GLASS_PANE;
    }

    private CollectionManager getCollectionManager() {
        return (CollectionManager) menuManager.getPlugin().getServer()
                .getServicesManager()
                .load(CollectionManager.class);
    }
}
