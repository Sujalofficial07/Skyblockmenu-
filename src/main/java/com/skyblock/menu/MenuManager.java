package com.skyblock.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.skyblock.utils.NBTUtil;

public class MenuManager {

    public static void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "SkyBlock Menu");

        ItemStack skills = new ItemStack(Material.DIAMOND_SWORD);
        NBTUtil.setString(skills, "menu", "skills");

        ItemStack collections = new ItemStack(Material.CHEST);
        NBTUtil.setString(collections, "menu", "collections");

        ItemStack stats = new ItemStack(Material.NETHER_STAR);
        NBTUtil.setString(stats, "menu", "stats");

        inv.setItem(20, skills);
        inv.setItem(22, collections);
        inv.setItem(24, stats);

        player.openInventory(inv);
    }
}
