// Path: /skyblock-items/src/main/java/net/sujal/items/util/ItemKeys.java
package net.sujal.items.util;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemKeys {

    public static NamespacedKey SKYBLOCK_ID;
    public static NamespacedKey RARITY;
    public static NamespacedKey REFORGE;
    public static NamespacedKey DAMAGE;
    public static NamespacedKey STRENGTH;

    /**
     * Call this in your main plugin onEnable()
     */
    public static void init(JavaPlugin plugin) {
        SKYBLOCK_ID = new NamespacedKey(plugin, "skyblock_id");
        RARITY = new NamespacedKey(plugin, "rarity");
        REFORGE = new NamespacedKey(plugin, "reforge");
        DAMAGE = new NamespacedKey(plugin, "stat_damage");
        STRENGTH = new NamespacedKey(plugin, "stat_strength");
        // Future keys like CRIT_CHANCE, INTELLIGENCE yahan add honge
    }
}
