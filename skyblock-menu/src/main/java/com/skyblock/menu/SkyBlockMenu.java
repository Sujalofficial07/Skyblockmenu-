package com.skyblock.menu;

import com.skyblock.utils.ColorUtil;
import com.skyblock.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class SkyBlockMenu {

    protected final MenuManager menuManager;
    protected Inventory inventory;
    protected final Map<Integer, Consumer<InventoryClickEvent>> clickHandlers = new HashMap<>();

    public SkyBlockMenu(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    public abstract String getTitle();
    public abstract int getSize();
    public abstract void buildContents(Player player);

    public void open(Player player) {
        inventory = Bukkit.createInventory(null, getSize(),
                ColorUtil.color(getTitle()));
        buildContents(player);
        player.openInventory(inventory);
    }

    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Consumer<InventoryClickEvent> handler = clickHandlers.get(event.getSlot());
        if (handler != null) {
            handler.accept(event);
        }
    }

    public void onClose(Player player) {}

    protected void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> handler) {
        inventory.setItem(slot, item);
        if (handler != null) {
            clickHandlers.put(slot, handler);
        }
    }

    protected void setItem(int slot, ItemStack item) {
        setItem(slot, item, null);
    }

    protected void fillBorder(Material material) {
        int size = getSize();
        int rows = size / 9;
        ItemStack filler = new ItemBuilder(material)
                .name(Component.empty())
                .hideFlags()
                .build();
        for (int i = 0; i < 9; i++) inventory.setItem(i, filler);
        for (int i = size - 9; i < size; i++) inventory.setItem(i, filler);
        for (int row = 1; row < rows - 1; row++) {
            inventory.setItem(row * 9, filler);
            inventory.setItem(row * 9 + 8, filler);
        }
    }

    protected void fillAll(Material material) {
        ItemStack filler = new ItemBuilder(material)
                .name(Component.empty())
                .hideFlags()
                .build();
        for (int i = 0; i < getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, filler);
            }
        }
    }

    protected void fillSlots(Material material, int... slots) {
        ItemStack filler = new ItemBuilder(material)
                .name(Component.empty())
                .hideFlags()
                .build();
        for (int slot : slots) {
            inventory.setItem(slot, filler);
        }
    }

    protected ItemStack makeGlassPane(int color) {
        Material mat;
        switch (color) {
            case 0: mat = Material.WHITE_STAINED_GLASS_PANE; break;
            case 1: mat = Material.ORANGE_STAINED_GLASS_PANE; break;
            case 2: mat = Material.MAGENTA_STAINED_GLASS_PANE; break;
            case 3: mat = Material.LIGHT_BLUE_STAINED_GLASS_PANE; break;
            case 4: mat = Material.YELLOW_STAINED_GLASS_PANE; break;
            case 5: mat = Material.LIME_STAINED_GLASS_PANE; break;
            case 6: mat = Material.PINK_STAINED_GLASS_PANE; break;
            case 7: mat = Material.GRAY_STAINED_GLASS_PANE; break;
            case 8: mat = Material.LIGHT_GRAY_STAINED_GLASS_PANE; break;
            case 9: mat = Material.CYAN_STAINED_GLASS_PANE; break;
            case 10: mat = Material.PURPLE_STAINED_GLASS_PANE; break;
            case 11: mat = Material.BLUE_STAINED_GLASS_PANE; break;
            case 12: mat = Material.BROWN_STAINED_GLASS_PANE; break;
            case 13: mat = Material.GREEN_STAINED_GLASS_PANE; break;
            case 14: mat = Material.RED_STAINED_GLASS_PANE; break;
            default: mat = Material.BLACK_STAINED_GLASS_PANE; break;
        }
        return new ItemBuilder(mat).name(Component.empty()).hideFlags().build();
    }

    public Inventory getInventory() {
        return inventory;
    }
}
