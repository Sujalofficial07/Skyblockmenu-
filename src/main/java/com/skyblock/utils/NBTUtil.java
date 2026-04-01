package com.skyblock.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import com.skyblock.SkyBlockPlugin;

public class NBTUtil {

    public static void setString(ItemStack item, String key, String value) {
        var meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(SkyBlockPlugin.getInstance(), key), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    public static String getString(ItemStack item, String key) {
        var meta = item.getItemMeta();
        return meta.getPersistentDataContainer()
                .get(new NamespacedKey(SkyBlockPlugin.getInstance(), key), PersistentDataType.STRING);
    }
}
