// Path: /skyblock-skills/src/main/java/net/sujal/skills/listener/FortuneListener.java
package net.sujal.skills.listener;

import net.sujal.api.rpg.Stat;
import net.sujal.skills.engine.StatManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class FortuneListener implements Listener {

    private final StatManager statManager;

    public FortuneListener(StatManager statManager) {
        this.statManager = statManager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        
        Block block = event.getBlock();
        
        // Anti-exploit check (We implemented this in AntiExploitListener)
        if (block.hasMetadata("player_placed")) return;

        double fortuneStat = 0.0;
        boolean isFarming = isCrop(block.getType());
        boolean isMining = isOre(block.getType());

        if (isFarming) {
            fortuneStat = statManager.getTotalStat(player, Stat.FARMING_FORTUNE);
        } else if (isMining) {
            // Assuming we use MINING_SPEED as a base for fortune or you add MINING_FORTUNE to enum
            fortuneStat = statManager.getTotalStat(player, Stat.MINING_SPEED) / 2.0; // Temporary logic
        } else {
            return; // Normal block, no fortune applied
        }

        // Calculate Multiplier (e.g., 250 Fortune = 3 drops guaranteed, 50% chance for 4th)
        int multiplier = 1 + (int) (fortuneStat / 100);
        double extraChance = fortuneStat % 100;
        
        if (Math.random() * 100 <= extraChance) {
            multiplier++;
        }

        if (multiplier > 1) {
            // Clear default drops
            event.setDropItems(false);
            
            // Spawn multiplied custom drops
            Collection<ItemStack> defaultDrops = block.getDrops(player.getInventory().getItemInMainHand());
            for (ItemStack drop : defaultDrops) {
                drop.setAmount(drop.getAmount() * multiplier);
                block.getWorld().dropItemNaturally(block.getLocation(), drop);
            }
        }
    }

    private boolean isCrop(Material mat) {
        return mat == Material.WHEAT || mat == Material.CARROTS || mat == Material.POTATOES || mat == Material.NETHER_WART;
    }

    private boolean isOre(Material mat) {
        return mat.name().endsWith("_ORE") || mat == Material.ANCIENT_DEBRIS;
    }
}
