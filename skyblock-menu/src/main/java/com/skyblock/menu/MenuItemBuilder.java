package com.skyblock.menu;

import com.skyblock.utils.ColorUtil;
import com.skyblock.utils.ItemBuilder;
import com.skyblock.utils.NBTUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MenuItemBuilder {

    private final Plugin plugin;

    public MenuItemBuilder(Plugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack buildSkyBlockMenuItem() {
        ItemStack item = new ItemBuilder(Material.NETHER_STAR)
                .name("&aSkyBlock Menu &7(Right Click)")
                .lore(
                    "&7Opens the SkyBlock menu",
                    "&7to manage your profile,",
                    "&7skills, collections, and more.",
                    "",
                    "&eRight-click to open!"
                )
                .customModelData(99999)
                .hideFlags()
                .build();
        NBTUtil.setBoolean(plugin, item, "skyblock_menu", true);
        return item;
    }

    public ItemStack buildBackButton(String lore) {
        return new ItemBuilder(Material.ARROW)
                .name("&aGo Back")
                .lore("&7" + lore, "", "&eClick to go back!")
                .customModelData(99998)
                .hideFlags()
                .build();
    }

    public ItemStack buildCloseButton() {
        return new ItemBuilder(Material.BARRIER)
                .name("&cClose Menu")
                .lore("&7Click to close this menu.")
                .customModelData(99997)
                .hideFlags()
                .build();
    }

    public ItemStack buildFillerPane() {
        return new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
                .name(Component.empty())
                .hideFlags()
                .build();
    }

    public ItemStack buildInfoItem(Material material, String name, List<String> lore) {
        String[] loreArr = lore.toArray(new String[0]);
        return new ItemBuilder(material)
                .name(name)
                .lore(loreArr)
                .hideFlags()
                .build();
    }
}
