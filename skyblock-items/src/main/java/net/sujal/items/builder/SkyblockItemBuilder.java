// Path: /skyblock-items/src/main/java/net/sujal/items/builder/SkyblockItemBuilder.java
package net.sujal.items.builder;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.sujal.items.enums.ItemRarity;
import net.sujal.items.util.ItemKeys;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SkyblockItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta meta;
    private final String skyblockId;
    
    private Component displayName;
    private ItemRarity rarity = ItemRarity.COMMON;
    private final List<Component> lore = new ArrayList<>();
    
    // Stats
    private double damage = 0;
    private double strength = 0;

    public SkyblockItemBuilder(Material material, String skyblockId) {
        this.itemStack = new ItemStack(material);
        this.meta = itemStack.getItemMeta();
        this.skyblockId = skyblockId;
        
        // Hide vanilla attributes like "When in main hand: +7 Attack Damage"
        this.meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        this.meta.setUnbreakable(true);
    }

    public SkyblockItemBuilder setDisplayName(String name) {
        this.displayName = Component.text(name).decoration(TextDecoration.ITALIC, false);
        return this;
    }

    public SkyblockItemBuilder setRarity(ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public SkyblockItemBuilder setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public SkyblockItemBuilder setStrength(double strength) {
        this.strength = strength;
        return this;
    }

    public SkyblockItemBuilder addLoreLine(Component line) {
        this.lore.add(line.decoration(TextDecoration.ITALIC, false));
        return this;
    }

    public ItemStack build() {
        // 1. Inject PDC Data (NBT)
        meta.getPersistentDataContainer().set(ItemKeys.SKYBLOCK_ID, PersistentDataType.STRING, skyblockId);
        meta.getPersistentDataContainer().set(ItemKeys.RARITY, PersistentDataType.STRING, rarity.name());
        
        if (damage > 0) {
            meta.getPersistentDataContainer().set(ItemKeys.DAMAGE, PersistentDataType.DOUBLE, damage);
        }
        if (strength > 0) {
            meta.getPersistentDataContainer().set(ItemKeys.STRENGTH, PersistentDataType.DOUBLE, strength);
        }

        // 2. Format Display Name
        Component finalName = displayName != null ? displayName.color(rarity.getColor()) 
                : Component.text(itemStack.getType().name()).color(rarity.getColor()).decoration(TextDecoration.ITALIC, false);
        meta.displayName(finalName);

        // 3. Format Lore (Stats on top, custom lore, rarity on bottom)
        List<Component> finalLore = new ArrayList<>();
        
        if (damage > 0) {
            finalLore.add(Component.text("Damage: ", NamedTextColor.GRAY)
                    .append(Component.text("+" + damage, NamedTextColor.RED))
                    .decoration(TextDecoration.ITALIC, false));
        }
        if (strength > 0) {
            finalLore.add(Component.text("Strength: ", NamedTextColor.GRAY)
                    .append(Component.text("+" + strength, NamedTextColor.RED))
                    .decoration(TextDecoration.ITALIC, false));
        }
        
        if (damage > 0 || strength > 0) {
            finalLore.add(Component.empty());
        }

        finalLore.addAll(this.lore);
        
        finalLore.add(Component.empty());
        finalLore.add(Component.text(rarity.getName(), rarity.getColor(), TextDecoration.BOLD)
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(finalLore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
