package com.skyblock.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CollectionsMenu {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null,54,"Collections");

        inv.setItem(10,new org.bukkit.inventory.ItemStack(Material.WHEAT));
        inv.setItem(11,new org.bukkit.inventory.ItemStack(Material.COBBLESTONE));
        inv.setItem(12,new org.bukkit.inventory.ItemStack(Material.ROTTEN_FLESH));
        inv.setItem(13,new org.bukkit.inventory.ItemStack(Material.COD));
        inv.setItem(14,new org.bukkit.inventory.ItemStack(Material.OAK_LOG));

        player.openInventory(inv);
    }
}
