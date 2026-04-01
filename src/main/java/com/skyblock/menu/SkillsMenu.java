package com.skyblock.menu;

import com.skyblock.skills.SkillManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SkillsMenu {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null,54,"Skills");

        inv.setItem(10,new org.bukkit.inventory.ItemStack(Material.DIAMOND_SWORD));
        inv.setItem(11,new org.bukkit.inventory.ItemStack(Material.IRON_PICKAXE));
        inv.setItem(12,new org.bukkit.inventory.ItemStack(Material.WHEAT));
        inv.setItem(13,new org.bukkit.inventory.ItemStack(Material.FISHING_ROD));
        inv.setItem(14,new org.bukkit.inventory.ItemStack(Material.OAK_LOG));
        inv.setItem(15,new org.bukkit.inventory.ItemStack(Material.ENCHANTED_BOOK));
        inv.setItem(16,new org.bukkit.inventory.ItemStack(Material.BREWING_STAND));
        inv.setItem(19,new org.bukkit.inventory.ItemStack(Material.BONE));

        player.openInventory(inv);
    }
}
