package com.skyblock.menu;

import com.skyblock.utils.ItemBuilder;
import com.skyblock.utils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MainMenu {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "SkyBlock Menu");

        for (int i = 0; i < 54; i++) {
            inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                    .name("§7")
                    .build());
        }

        inv.setItem(13, createItem(Material.PLAYER_HEAD, "profile"));
        inv.setItem(20, createItem(Material.DIAMOND_SWORD, "skills"));
        inv.setItem(21, new ItemBuilder(Material.PAPER).name("§aCollections").build());
        inv.setItem(22, new ItemBuilder(Material.BOOK).name("§aRecipe Book").build());
        inv.setItem(23, new ItemBuilder(Material.GRASS_BLOCK).name("§aSkyBlock Levels").build());
        inv.setItem(24, createItem(Material.CLOCK, "stats"));
        inv.setItem(25, new ItemBuilder(Material.CHEST).name("§aEnder Chest").build());

        inv.setItem(29, new ItemBuilder(Material.CAKE).name("§aProfile").build());
        inv.setItem(30, new ItemBuilder(Material.BONE).name("§aPets").build());
        inv.setItem(31, new ItemBuilder(Material.CRAFTING_TABLE).name("§aCrafting").build());
        inv.setItem(32, new ItemBuilder(Material.LEATHER_CHESTPLATE).name("§aWardrobe").build());
        inv.setItem(33, new ItemBuilder(Material.PLAYER_HEAD).name("§aEquipment").build());

        inv.setItem(48, new ItemBuilder(Material.PLAYER_HEAD).name("§aClose").build());

        player.openInventory(inv);
    }

    private static org.bukkit.inventory.ItemStack createItem(Material mat, String id) {
        org.bukkit.inventory.ItemStack item = new ItemBuilder(mat).name("§a" + id).build();
        NBTUtil.setString(item, "menu", id);
        return item;
    }
}
