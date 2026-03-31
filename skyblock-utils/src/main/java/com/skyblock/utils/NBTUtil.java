package com.skyblock.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public final class NBTUtil {

    private NBTUtil() {}

    public static void setString(Plugin plugin, ItemStack item, String key, String value) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(plugin, key);
        meta.getPersistentDataContainer().set(nk, PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    public static Optional<String> getString(Plugin plugin, ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return Optional.empty();
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(plugin, key);
        String val = meta.getPersistentDataContainer().get(nk, PersistentDataType.STRING);
        return Optional.ofNullable(val);
    }

    public static void setBoolean(Plugin plugin, ItemStack item, String key, boolean value) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(plugin, key);
        meta.getPersistentDataContainer().set(nk, PersistentDataType.BOOLEAN, value);
        item.setItemMeta(meta);
    }

    public static boolean getBoolean(Plugin plugin, ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(plugin, key);
        Boolean val = meta.getPersistentDataContainer().get(nk, PersistentDataType.BOOLEAN);
        return val != null && val;
    }

    public static void setInt(Plugin plugin, ItemStack item, String key, int value) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(plugin, key);
        meta.getPersistentDataContainer().set(nk, PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
    }

    public static int getInt(Plugin plugin, ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return 0;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(plugin, key);
        Integer val = meta.getPersistentDataContainer().get(nk, PersistentDataType.INTEGER);
        return val == null ? 0 : val;
    }

    public static boolean hasKey(Plugin plugin, ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        NamespacedKey nk = new NamespacedKey(plugin, key);
        return meta.getPersistentDataContainer().has(nk);
    }

    public static void copyTag(Plugin plugin, ItemStack source, ItemStack target, String key) {
        getString(plugin, source, key).ifPresent(val -> setString(plugin, target, key, val));
    }

    public static PersistentDataContainer getContainer(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        return item.getItemMeta().getPersistentDataContainer();
    }
}
