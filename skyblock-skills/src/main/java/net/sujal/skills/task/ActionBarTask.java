// Path: /skyblock-skills/src/main/java/net/sujal/skills/task/ActionBarTask.java
package net.sujal.skills.task;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.sujal.api.rpg.Stat;
import net.sujal.skills.engine.ManaManager;
import net.sujal.skills.engine.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarTask extends BukkitRunnable {

    private final StatManager statManager;
    private final ManaManager manaManager;

    public ActionBarTask(JavaPlugin plugin, StatManager statManager, ManaManager manaManager) {
        this.statManager = statManager;
        this.manaManager = manaManager;
        // Run every 10 ticks (0.5 seconds)
        this.runTaskTimer(plugin, 20L, 10L);
        
        // Also start a slower task for Mana Regen (every 20 ticks / 1 second)
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    manaManager.regenerateMana(player);
                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            double maxHealth = statManager.getTotalStat(player, Stat.HEALTH);
            double currentHealth = player.getHealth();
            double defense = statManager.getTotalStat(player, Stat.DEFENSE);
            double currentMana = manaManager.getCurrentMana(player);
            double maxMana = manaManager.getMaxMana(player);

            // Format: ❤ 100/100  ❈ 50  ✎ 100/100
            Component actionBar = Component.text(Stat.HEALTH.getSymbol() + " " + (int)currentHealth + "/" + (int)maxHealth + "  ", NamedTextColor.RED)
                    .append(Component.text(Stat.DEFENSE.getSymbol() + " " + (int)defense + "  ", NamedTextColor.GREEN))
                    .append(Component.text(Stat.INTELLIGENCE.getSymbol() + " " + (int)currentMana + "/" + (int)maxMana, NamedTextColor.AQUA));

            player.sendActionBar(actionBar);
        }
    }
}
