package com.sujal.hypixelcore.menu;

import com.sujal.hypixelcore.data.PlayerData;
import com.sujal.hypixelcore.data.PlayerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SkyblockMenu {

    // Naya Method: Player ki hotbar mein permanently Nether Star lagane ke liye
    public static void giveMenuStar(Player player, Plugin plugin) {
        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = star.getItemMeta();
        
        if (meta != null) {
            meta.displayName(Component.text("§aSkyBlock Menu §7(Right Click)").decoration(TextDecoration.ITALIC, false));
            
            // Yahan hum ek invisible tag laga rahe hain taaki isko normal Nether Star se alag pehchan sakein
            NamespacedKey key = new NamespacedKey(plugin, "skyblock_menu_star");
            meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
            
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            star.setItemMeta(meta);
        }
        
        // 9th Slot (Index 8) mein item lock kar dena
        player.getInventory().setItem(8, star);
    }

    // Pehle wala openMenu function (Pura Waisa hi)
    public static void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("SkyBlock Menu", NamedTextColor.DARK_GRAY));

        ItemStack bg = createItem(Material.BLACK_STAINED_GLASS_PANE, " ", null);
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, bg);
        }

        inv.setItem(13, createItem(Material.DIAMOND_SWORD, "§aYour Skills", Arrays.asList("§7View your skill progression and", "§7rewards.", "", "§eClick to view!")));
        inv.setItem(19, createItem(Material.PAINTING, "§aCollections", Arrays.asList("§7View all of the items available", "§7in SkyBlock.", "", "§eClick to view!")));
        inv.setItem(20, createItem(Material.BOOK, "§aRecipe Book", Arrays.asList("§7Through your adventure, you will", "§7unlock recipes.", "", "§eClick to view!")));
        inv.setItem(21, createItem(Material.EMERALD, "§aTrades", Arrays.asList("§7View your available trades.", "", "§eClick to view!")));
        inv.setItem(22, createItem(Material.WRITABLE_BOOK, "§aQuest Log", Arrays.asList("§7View your active quests.", "", "§eClick to view!")));
        inv.setItem(23, createItem(Material.CLOCK, "§aCalendar and Events", Arrays.asList("§7View the SkyBlock calendar, upcoming", "§7events, and more.", "", "§eClick to view!")));
        inv.setItem(24, createItem(Material.ENDER_CHEST, "§aStorage", Arrays.asList("§7Store your items in your Ender", "§7Chest and Backpacks.", "", "§eClick to open!")));
        inv.setItem(25, createItem(Material.BONE, "§aPets", Arrays.asList("§7View and manage your Pets.", "", "§eClick to view!")));
        inv.setItem(30, createItem(Material.LEATHER_CHESTPLATE, "§aWardrobe", Arrays.asList("§7Store your armor sets and quickly", "§7swap between them.", "", "§eClick to open!")));
        inv.setItem(31, createItem(Material.GOLD_BLOCK, "§aPersonal Bank", Arrays.asList("§7Deposit or withdraw coins from", "§7your bank account.", "", "§eClick to access!")));
        inv.setItem(32, createItem(Material.MAP, "§aFast Travel", Arrays.asList("§7Teleport to specific locations", "§7around SkyBlock.", "", "§eClick to view!")));
        inv.setItem(48, createItem(Material.NAME_TAG, "§aProfile Management", Arrays.asList("§7Manage your SkyBlock profiles.", "", "§eClick to view!")));
        
        // Dynamic Player Stats
        PlayerData data = PlayerManager.getPlayerData(player);
        int sbLevel = data != null ? data.getSkyblockLevel() : 0;
        
        List<String> statsLore = Arrays.asList(
            "§7View your active effects, core",
            "§7stats, and more.",
            "",
            "§c❤ Health §f" + (100 + (sbLevel * 5)) + " HP",
            "§a❈ Defense §f0",
            "§c❁ Strength §f" + (sbLevel / 5),
            "§f✦ Speed §f100",
            "§9☣ Crit Chance §f30%",
            "§9☠ Crit Damage §f50%",
            "§b✎ Intelligence §f100",
            "",
            "§eClick to view!"
        );
        inv.setItem(49, createItem(Material.NETHER_STAR, "§bSkyBlock Level " + sbLevel, statsLore));
        inv.setItem(50, createItem(Material.REDSTONE_TORCH, "§aSettings", Arrays.asList("§7Toggle different settings.", "", "§eClick to view!")));

        player.openInventory(inv);
    }

    private static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));
            if (lore != null) {
                meta.lore(lore.stream()
                        .map(l -> Component.text(l).decoration(TextDecoration.ITALIC, false))
                        .collect(Collectors.toList()));
            }
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        }
        return item;
    }
}
