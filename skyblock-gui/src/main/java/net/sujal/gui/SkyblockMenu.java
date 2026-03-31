// Path: /skyblock-gui/src/main/java/net/sujal/gui/SkyblockMenu.java
package net.sujal.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface SkyblockMenu extends InventoryHolder {
    void handleClick(InventoryClickEvent event);
}
