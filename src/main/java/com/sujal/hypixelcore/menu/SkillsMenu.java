package com.sujal.hypixelcore.menu;

import com.sujal.hypixelcore.data.PlayerData;
import com.sujal.hypixelcore.data.PlayerManager;
import com.sujal.hypixelcore.enums.SkillType;
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

public class SkillsMenu {

    public static void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("Your Skills", NamedTextColor.DARK_GRAY));
        PlayerData data = PlayerManager.getPlayerData(player);

        // Fill Background
        ItemStack bg = createItem(Material.BLACK_STAINED_GLASS_PANE, " ", null);
        for (int i = 0; i < 54; i++) inv.setItem(i, bg);

        // Add Skills Dynamically from PlayerData
        inv.setItem(20, createSkillItem(Material.GOLDEN_HOE, SkillType.FARMING, data));
        inv.setItem(21, createSkillItem(Material.STONE_PICKAXE, SkillType.MINING, data));
        inv.setItem(22, createSkillItem(Material.IRON_SWORD, SkillType.COMBAT, data));
        inv.setItem(23, createSkillItem(Material.OAK_SAPLING, SkillType.FORAGING, data));
        inv.setItem(24, createSkillItem(Material.FISHING_ROD, SkillType.FISHING, data));
        inv.setItem(29, createSkillItem(Material.ENCHANTING_TABLE, SkillType.ENCHANTING, data));
        inv.setItem(30, createSkillItem(Material.BREWING_STAND, SkillType.ALCHEMY, data));

        // Back Button
        inv.setItem(49, createItem(Material.ARROW, "§aGo Back", Arrays.asList("§7To SkyBlock Menu")));

        player.openInventory(inv);
    }

    private static ItemStack createSkillItem(Material mat, SkillType skill, PlayerData data) {
        int level = data.getSkillLevel(skill);
        List<String> lore = Arrays.asList(
            "§7View your " + skill.getName() + " progression and",
            "§7rewards.",
            "",
            "§7Level: §e" + level,
            "§7Stat Bonus: §a" + skill.getStatBonus(),
            "",
            "§eClick to view!"
        );
        return createItem(mat, "§a" + skill.getName(), lore);
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
