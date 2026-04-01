package com.skyblock.menu;

import com.skyblock.utils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainMenu {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "SkyBlock Menu");

        ItemStack profile = new ItemStack(Material.PLAYER_HEAD);
        NBTUtil.setString(profile,"menu","profile");

        ItemStack skills = new ItemStack(Material.DIAMOND_SWORD);
        NBTUtil.setString(skills,"menu","skills");

        ItemStack collections = new ItemStack(Material.CHEST);
        NBTUtil.setString(collections,"menu","collections");

        ItemStack stats = new ItemStack(Material.NETHER_STAR);
        NBTUtil.setString(stats,"menu","stats");

        inv.setItem(13, profile);
        inv.setItem(20, skills);
        inv.setItem(22, collections);
        inv.setItem(24, stats);

        player.openInventory(inv);
    }
}
