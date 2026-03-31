package com.skyblock.menu;

import com.skyblock.utils.ColorUtil;
import com.skyblock.utils.ItemBuilder;
import com.skyblock.utils.NBTUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MainSkyBlockMenu extends SkyBlockMenu {

    public MainSkyBlockMenu(MenuManager menuManager) {
        super(menuManager);
    }

    @Override
    public String getTitle() {
        return "&0SkyBlock Menu";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void buildContents(Player player) {
        // Fill background with black glass panes
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, makeGlassPane(15));
        }

        // ---- Skills Button (slot 10) ----
        ItemStack skillsItem = new ItemBuilder(Material.DIAMOND_SWORD)
                .name("&aYour Skills")
                .lore(
                    "&7View all of your SkyBlock",
                    "&7skills and their levels.",
                    "",
                    "&eClick to view!"
                )
                .customModelData(100001)
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), skillsItem, "menu_type", "skills");
        setItem(10, skillsItem, event -> {
            Player p = (Player) event.getWhoClicked();
    //        menuManager.openMenu(p, new com.skyblock.skills.SkillsMenu(menuManager));
        });

        // ---- Collections Button (slot 11) ----
        ItemStack collectionsItem = new ItemBuilder(Material.PAINTING)
                .name("&6Collections")
                .lore(
                    "&7View all of your SkyBlock",
                    "&7collections and their tiers.",
                    "",
                    "&eClick to view!"
                )
                .customModelData(100002)
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), collectionsItem, "menu_type", "collections");
        setItem(11, collectionsItem, event -> {
            Player p = (Player) event.getWhoClicked();
     //       menuManager.openMenu(p, new com.skyblock.collections.CollectionsMenu(menuManager));
        });

        // ---- Profile Button (slot 13) ----
        ItemStack profileItem = new ItemBuilder(Material.PLAYER_HEAD)
                .name("&aYour SkyBlock Profile")
                .lore(
                    "&7View your SkyBlock profile",
                    "&7stats and information.",
                    "",
                    "&eClick to view!"
                )
                .customModelData(100003)
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), profileItem, "menu_type", "profile");
        setItem(13, profileItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cProfile menu coming soon!"));
        });

        // ---- Trades Button (slot 14) ----
        ItemStack tradesItem = new ItemBuilder(Material.EMERALD)
                .name("&aTrades")
                .lore(
                    "&7Trade with other players",
                    "&7or browse the Auction House.",
                    "",
                    "&eClick to view!"
                )
                .customModelData(100004)
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), tradesItem, "menu_type", "trades");
        setItem(14, tradesItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cTrades menu coming soon!"));
        });

        // ---- Pets Button (slot 15) ----
        ItemStack petsItem = new ItemBuilder(Material.BONE)
                .name("&aPets")
                .lore(
                    "&7Manage and view your",
                    "&7collection of pets.",
                    "",
                    "&eClick to view!"
                )
                .customModelData(100005)
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), petsItem, "menu_type", "pets");
        setItem(15, petsItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cPets menu coming soon!"));
        });

        // ---- Fast Travel Button (slot 31) ----
        ItemStack fastTravelItem = new ItemBuilder(Material.COMPASS)
                .name("&aFast Travel")
                .lore(
                    "&7Quickly travel to important",
                    "&7locations on your island.",
                    "",
                    "&eClick to open!"
                )
                .customModelData(100006)
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), fastTravelItem, "menu_type", "fast_travel");
        setItem(31, fastTravelItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cFast Travel menu coming soon!"));
        });

        // ---- Settings Button (slot 35) ----
        ItemStack settingsItem = new ItemBuilder(Material.COMPARATOR)
                .name("&aSkyBlock Settings")
                .lore(
                    "&7Customize your SkyBlock",
                    "&7experience and preferences.",
                    "",
                    "&eClick to open!"
                )
                .customModelData(100007)
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), settingsItem, "menu_type", "settings");
        setItem(35, settingsItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cSettings menu coming soon!"));
        });

        // ---- Accessories / Talisman Bag (slot 28) ----
        ItemStack accessoriesItem = new ItemBuilder(Material.LEATHER)
                .name("&5Accessory Bag")
                .lore(
                    "&7Manage your talismans",
                    "&7and accessories.",
                    "",
                    "&eClick to open!"
                )
                .customModelData(100008)
                .hideFlags()
                .build();
        setItem(28, accessoriesItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cAccessory Bag coming soon!"));
        });

        // ---- Crafting Table (slot 30) ----
        ItemStack craftingItem = new ItemBuilder(Material.CRAFTING_TABLE)
                .name("&aCraft Item")
                .lore(
                    "&7Open a crafting table.",
                    "",
                    "&eClick to craft!"
                )
                .customModelData(100009)
                .hideFlags()
                .build();
        setItem(30, craftingItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.openWorkbench(null, true);
        });

        // ---- Wardrobe (slot 32) ----
        ItemStack wardrobeItem = new ItemBuilder(Material.CHEST)
                .name("&eWardrobe")
                .lore(
                    "&7Switch between your",
                    "&7armor sets quickly.",
                    "",
                    "&eClick to open!"
                )
                .customModelData(100010)
                .hideFlags()
                .build();
        setItem(32, wardrobeItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cWardrobe coming soon!"));
        });

        // ---- Quests (slot 33) ----
        ItemStack questsItem = new ItemBuilder(Material.BOOK)
                .name("&eQuests")
                .lore(
                    "&7View and track your",
                    "&7active quests.",
                    "",
                    "&eClick to open!"
                )
                .customModelData(100011)
                .hideFlags()
                .build();
        setItem(33, questsItem, event -> {
            Player p = (Player) event.getWhoClicked();
            p.sendMessage(ColorUtil.color("&cQuests coming soon!"));
        });

        // ---- Wiki / ? (slot 4 top center) ----
        ItemStack wikiItem = new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .name("&eAbout SkyBlock")
                .lore(
                    "&7Learn about SkyBlock",
                    "&7features and mechanics.",
                    "",
                    "&bwiki.hypixel.net"
                )
                .customModelData(100012)
                .hideFlags()
                .build();
        setItem(4, wikiItem, null);
    }
}
