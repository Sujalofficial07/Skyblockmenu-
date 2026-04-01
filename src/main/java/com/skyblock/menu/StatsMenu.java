package com.skyblock.menu;

import com.skyblock.stats.StatsManager;
import com.skyblock.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StatsMenu {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "Your Stats Breakdown");

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                    .name("§7")
                    .build());
        }

        inv.setItem(10, new ItemBuilder(Material.GOLDEN_APPLE)
                .name("§cHealth")
                .lore("§7" + StatsManager.get(player.getUniqueId(),"health"))
                .build());

        inv.setItem(11, new ItemBuilder(Material.IRON_CHESTPLATE)
                .name("§aDefense")
                .lore("§7" + StatsManager.get(player.getUniqueId(),"defense"))
                .build());

        inv.setItem(12, new ItemBuilder(Material.BLAZE_POWDER)
                .name("§cStrength")
                .lore("§7" + StatsManager.get(player.getUniqueId(),"strength"))
                .build());

        inv.setItem(13, new ItemBuilder(Material.ENCHANTED_BOOK)
                .name("§bIntelligence")
                .lore("§7" + StatsManager.get(player.getUniqueId(),"intelligence"))
                .build());

        inv.setItem(14, new ItemBuilder(Material.DIAMOND)
                .name("§9Crit Chance")
                .lore("§7" + StatsManager.get(player.getUniqueId(),"crit_chance")+"%")
                .build());

        inv.setItem(15, new ItemBuilder(Material.IRON_SWORD)
                .name("§9Crit Damage")
                .lore("§7" + StatsManager.get(player.getUniqueId(),"crit_damage")+"%")
                .build());

        player.openInventory(inv);
    }
}
