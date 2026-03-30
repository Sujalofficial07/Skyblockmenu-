package com.sujal.hypixelcore.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SkyblockMenu {

    public static void openMenu(Player player) {
        // 54 slots = 6 rows
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("SkyBlock Menu", NamedTextColor.DARK_GRAY));

        // Fill background with black glass panes
        ItemStack bg = createItem(Material.BLACK_STAINED_GLASS_PANE, " ", null);
        for (int i = 0; i < 54; i++) {
            inv.setItem(i, bg);
        }

        // Add core menu items based on actual Hypixel layout
        inv.setItem(13, createItem(Material.DIAMOND_SWORD, "§aYour Skills", Arrays.asList("§7View your skill progression and", "§7rewards.")));
        inv.setItem(19, createItem(Material.PAINTING, "§aCollections", Arrays.asList("§7View your collections and", "§7recipes.")));
        inv.setItem(20, createItem(Material.KNOWLEDGE_BOOK, "§aRecipe Book", Arrays.asList("§7View your crafting recipes.")));
        inv.setItem(21, createItem(Material.EMERALD, "§aTrades", Arrays.asList("§7View your available trades.")));
        inv.setItem(22, createItem(Material.WRITABLE_BOOK, "§aQuest Log", Arrays.asList("§7View your active quests.")));
        inv.setItem(23, createItem(Material.CLOCK, "§aCalendar and Events", Arrays.asList("§7View the SkyBlock calendar.")));
        inv.setItem(24, createItem(Material.ENDER_CHEST, "§aStorage", Arrays.asList("§7Access your Ender Chest and", "§7backpacks.")));
        inv.setItem(25, createItem(Material.BONE, "§aPets", Arrays.asList("§7Manage your pets.")));
        
        inv.setItem(30, createItem(Material.LEATHER_CHESTPLATE, "§aWardrobe", Arrays.asList("§7Store and swap your armor sets.")));
        inv.setItem(31, createItem(Material.GOLD_BLOCK, "§aPersonal Bank", Arrays.asList("§7Access your bank account.")));
        inv.setItem(32, createItem(Material.MAP, "§aFast Travel", Arrays.asList("§7Teleport to different islands.")));

        inv.setItem(48, createItem(Material.NAME_TAG, "§aProfile Management", Arrays.asList("§7Manage your SkyBlock profiles.")));
        
        // Stats Item (Slot 49 - Nether Star)
        List<String> statsLore = Arrays.asList(
            "§c❤ Health: §a100",
            "§a❈ Defense: §a0",
            "§c❁ Strength: §a0",
            "§f✦ Speed: §a100",
            "§9☣ Crit Chance: §a30%",
            "§9☠ Crit Damage: §a50%",
            "§b✎ Intelligence: §a100"
        );
        inv.setItem(49, createItem(Material.NETHER_STAR, "§bSkyBlock Level", statsLore));
        
        inv.setItem(50, createItem(Material.REDSTONE_TORCH, "§aSettings", Arrays.asList("§7Change your SkyBlock settings.")));

        // Open inventory for the player
        player.openInventory(inv);
    }

    private static ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));
            if (lore != null) {
                meta.lore(lore.stream().map(l -> Component.text(l).decoration(TextDecoration.ITALIC, false)).collect(Collectors.toList()));
            }
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
            item.setItemMeta(meta);
        }
        return item;
    }
}
