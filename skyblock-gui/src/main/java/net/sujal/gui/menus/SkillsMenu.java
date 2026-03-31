// Path: /skyblock-gui/src/main/java/net/sujal/gui/menus/SkillsMenu.java
package net.sujal.gui.menus;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.api.profile.SkyblockProfile;
import net.sujal.api.rpg.Skill;
import net.sujal.gui.SkyblockMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SkillsMenu implements SkyblockMenu {

    private final Inventory inventory;
    private final SkyblockProfile profile;

    public SkillsMenu(SkyblockProfile profile) {
        this.profile = profile;
        // 54 slots (6 rows) chest
        this.inventory = Bukkit.createInventory(this, 54, Component.text("Your Skills"));
        setupMenu();
    }

    private void setupMenu() {
        // Fill background with glass panes
        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        if (fillerMeta != null) {
            fillerMeta.displayName(Component.text(" "));
            filler.setItemMeta(fillerMeta);
        }
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, filler);
        }

        // FIXED: Using the API method directly without importing the data implementation
        double combatXp = profile.getSkillXp(Skill.COMBAT);
        int combatLevel = calculateLevel(combatXp);
        
        ItemStack combatIcon = new ItemStack(Material.STONE_SWORD);
        ItemMeta combatMeta = combatIcon.getItemMeta();
        if (combatMeta != null) {
            combatMeta.displayName(Component.text("Combat Skill", NamedTextColor.RED));
            
            List<Component> combatLore = new ArrayList<>();
            combatLore.add(Component.text("Level: " + combatLevel, NamedTextColor.GRAY));
            combatLore.add(Component.text("Total XP: " + String.format("%.1f", combatXp), NamedTextColor.GRAY));
            combatLore.add(Component.empty());
            combatLore.add(Component.text("Level Up Rewards:", NamedTextColor.GOLD));
            combatLore.add(Component.text("+1% Crit Chance", NamedTextColor.GRAY));
            combatMeta.lore(combatLore);
            combatIcon.setItemMeta(combatMeta);
        }

        // Place Combat icon in the middle of the chest
        inventory.setItem(20, combatIcon);
    }

    // Mathematical formula for level calculation (Similar to Hypixel)
    private int calculateLevel(double xp) {
        int level = 0;
        double xpRequired = 50; // Level 1 needs 50 XP
        while (xp >= xpRequired && level < 60) {
            xp -= xpRequired;
            level++;
            xpRequired *= 1.2; // Next level needs 20% more XP
        }
        return level;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void handleClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        
        // Example interaction
        if (event.getRawSlot() == 20) {
            player.sendMessage(Component.text("You clicked the Combat Skill!", NamedTextColor.GREEN));
        }
    }
}
