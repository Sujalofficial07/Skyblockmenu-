package com.skyblock.menu;

import com.skyblock.stats.StatsManager;
import com.skyblock.utils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StatsMenu {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "Your Equipment and Stats");

        ItemStack combat = createItem(Material.DIAMOND_SWORD, "§cCombat Stats",
                "§7Gives you a better chance at fighting strong monsters,",
                "",
                "§c❤ Health §f" + StatsManager.get(player.getUniqueId(),"health"),
                "§a❈ Defense §f" + StatsManager.get(player.getUniqueId(),"defense"),
                "§c❁ Strength §f" + StatsManager.get(player.getUniqueId(),"strength"),
                "§b✎ Intelligence §f" + StatsManager.get(player.getUniqueId(),"intelligence"),
                "§9☣ Crit Chance §f" + StatsManager.get(player.getUniqueId(),"crit_chance")+"%",
                "§9☠ Crit Damage §f" + StatsManager.get(player.getUniqueId(),"crit_damage")+"%",
                "§e⚔ Bonus Attack Speed §f" + StatsManager.get(player.getUniqueId(),"attack_speed")+"%",
                "§f❂ True Defense §f" + StatsManager.get(player.getUniqueId(),"true_defense"),
                "§c⫽ Ferocity §f" + StatsManager.get(player.getUniqueId(),"ferocity"),
                "",
                "§eClick for details!"
        );

        ItemStack gathering = createItem(Material.IRON_PICKAXE, "§aGathering Stats",
                "§7Lets you collect and harvest better items",
                "",
                "§6Mining Speed §f" + StatsManager.get(player.getUniqueId(),"mining_speed"),
                "§6Mining Fortune §f" + StatsManager.get(player.getUniqueId(),"mining_fortune"),
                "§6Farming Fortune §f" + StatsManager.get(player.getUniqueId(),"farming_fortune"),
                "§6Foraging Fortune §f" + StatsManager.get(player.getUniqueId(),"foraging_fortune"),
                "",
                "§eClick for details!"
        );

        ItemStack misc = createItem(Material.CLOCK, "§dMisc Stats",
                "§7Augments various aspects of your gameplay!",
                "",
                "§f✦ Speed §f" + StatsManager.get(player.getUniqueId(),"speed"),
                "§b✯ Magic Find §f" + StatsManager.get(player.getUniqueId(),"magic_find"),
                "§d♣ Pet Luck §f" + StatsManager.get(player.getUniqueId(),"pet_luck"),
                "§3☂ Fishing Speed §f" + StatsManager.get(player.getUniqueId(),"fishing_speed"),
                "",
                "§eClick for details!"
        );

        inv.setItem(20, combat);
        inv.setItem(22, gathering);
        inv.setItem(24, misc);

        player.openInventory(inv);
    }

    private static ItemStack createItem(Material mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
