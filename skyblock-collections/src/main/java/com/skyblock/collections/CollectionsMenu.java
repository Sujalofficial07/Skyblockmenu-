package com.skyblock.collections;

import com.skyblock.menu.MainSkyBlockMenu;
import com.skyblock.menu.MenuManager;
import com.skyblock.menu.SkyBlockMenu;
import com.skyblock.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CollectionsMenu extends SkyBlockMenu {

    public CollectionsMenu(MenuManager menuManager) {
        super(menuManager);
    }

    @Override
    public String getTitle() {
        return "&0Collections";
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

        CollectionCategory[] categories = CollectionCategory.values();
        int[] slots = {20, 22, 24, 29, 33};

        for (int i = 0; i < categories.length; i++) {
            CollectionCategory cat = categories[i];
            CollectionManager cm = getCollectionManager();
            PlayerCollectionProfile profile = cm.getProfile(player.getUniqueId());
            int unlockedCount = 0;
            int totalCount = 0;
            for (CollectionType type : CollectionType.values()) {
                if (type.getCategory() == cat) {
                    totalCount++;
                    CollectionData data = profile.getCollection(type);
                    if (data.getTier() > 0) unlockedCount++;
                }
            }

            ItemStack catItem = new ItemBuilder(cat.getIcon())
                    .name(cat.getColoredName())
                    .lore(
                        "",
                        "&7Collections: &e" + unlockedCount + " &7/ &e" + totalCount,
                        "",
                        "&eClick to view!"
                    )
                    .customModelData(cat.getCustomModelData())
                    .hideFlags()
                    .build();

            final CollectionCategory finalCat = cat;
            setItem(slots[i], catItem, event -> {
                Player p = (Player) event.getWhoClicked();
                menuManager.openMenu(p, new CollectionCategoryMenu(menuManager, finalCat));
            });
        }

        // Title display
        ItemStack titleItem = new ItemBuilder(Material.PAINTING)
                .name("&6Collections")
                .lore(
                    "&7View all items you have",
                    "&7collected on SkyBlock.",
                    "",
                    "&7Each collection has tiers",
                    "&7that unlock rewards!"
                )
                .hideFlags()
                .build();
        setItem(4, titleItem, null);

        // Back button
        ItemStack backItem = new ItemBuilder(Material.ARROW)
                .name("&aGo Back")
                .lore("&7Return to SkyBlock Menu", "", "&eClick to go back!")
                .hideFlags()
                .build();
        setItem(49, backItem, event -> {
            Player p = (Player) event.getWhoClicked();
            menuManager.openMenu(p, new MainSkyBlockMenu(menuManager));
        });

        // Close
        ItemStack closeItem = new ItemBuilder(Material.BARRIER)
                .name("&cClose")
                .lore("&7Close this menu.")
                .hideFlags()
                .build();
        setItem(53, closeItem, event -> event.getWhoClicked().closeInventory());
    }

    private CollectionManager getCollectionManager() {
        return (CollectionManager) menuManager.getPlugin().getServer()
                .getServicesManager()
                .load(CollectionManager.class);
    }
}
