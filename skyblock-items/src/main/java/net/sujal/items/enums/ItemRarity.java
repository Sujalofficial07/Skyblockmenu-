// Path: /skyblock-items/src/main/java/net/sujal/items/enums/ItemRarity.java
package net.sujal.items.enums;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum ItemRarity {
    COMMON("COMMON", NamedTextColor.WHITE),
    UNCOMMON("UNCOMMON", NamedTextColor.GREEN),
    RARE("RARE", NamedTextColor.BLUE),
    EPIC("EPIC", NamedTextColor.DARK_PURPLE),
    LEGENDARY("LEGENDARY", NamedTextColor.GOLD),
    MYTHIC("MYTHIC", NamedTextColor.LIGHT_PURPLE),
    DIVINE("DIVINE", NamedTextColor.AQUA),
    SPECIAL("SPECIAL", NamedTextColor.RED);

    private final String name;
    private final TextColor color;

    ItemRarity(String name, TextColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public TextColor getColor() {
        return color;
    }
}
