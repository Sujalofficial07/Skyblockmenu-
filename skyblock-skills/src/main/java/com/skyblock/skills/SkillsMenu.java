package com.skyblock.skills;

import com.skyblock.database.SkillLevelCalculator;
import com.skyblock.menu.MainSkyBlockMenu;
import com.skyblock.menu.MenuManager;
import com.skyblock.menu.SkyBlockMenu;
import com.skyblock.utils.ColorUtil;
import com.skyblock.utils.ItemBuilder;
import com.skyblock.utils.NBTUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillsMenu extends SkyBlockMenu {

    public SkillsMenu(MenuManager menuManager) {
        super(menuManager);
    }

    @Override
    public String getTitle() {
        return "&0Skills";
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void buildContents(Player player) {
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, makeGlassPane(15));
        }

        SkillManager skillManager = getSkillManager();
        PlayerSkillProfile profile = skillManager.getProfile(player.getUniqueId());

        SkillType[] mainSkills = {
            SkillType.FARMING, SkillType.MINING, SkillType.COMBAT,
            SkillType.FISHING, SkillType.FORAGING, SkillType.ENCHANTING,
            SkillType.ALCHEMY, SkillType.TAMING
        };

        int[] slots = {10, 11, 12, 13, 14, 15, 16, 20};

        for (int i = 0; i < mainSkills.length; i++) {
            SkillType type = mainSkills[i];
            SkillData data = profile.getSkill(type);
            ItemStack item = buildSkillItem(type, data);
            final SkillType finalType = type;
            setItem(slots[i], item, event -> {
                Player p = (Player) event.getWhoClicked();
                menuManager.openMenu(p, new SkillDetailMenu(menuManager, finalType));
            });
        }

        // Carpentry and Runecrafting in separate row
        SkillData carpentryData = profile.getSkill(SkillType.CARPENTRY);
        ItemStack carpentryItem = buildSkillItem(SkillType.CARPENTRY, carpentryData);
        setItem(22, carpentryItem, event -> {
            Player p = (Player) event.getWhoClicked();
            menuManager.openMenu(p, new SkillDetailMenu(menuManager, SkillType.CARPENTRY));
        });

        SkillData runecraftingData = profile.getSkill(SkillType.RUNECRAFTING);
        ItemStack runecraftingItem = buildSkillItem(SkillType.RUNECRAFTING, runecraftingData);
        setItem(23, runecraftingItem, event -> {
            Player p = (Player) event.getWhoClicked();
            menuManager.openMenu(p, new SkillDetailMenu(menuManager, SkillType.RUNECRAFTING));
        });

        // Average Skill Level display
        double avg = profile.getAverageSkillLevel();
        ItemStack avgItem = new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .name("&aAverage Skill Level: &e" + String.format("%.2f", avg))
                .lore(
                    "&7Your average skill level",
                    "&7across all main skills.",
                    "",
                    "&7This affects your",
                    "&7overall progress!"
                )
                .hideFlags()
                .build();
        setItem(4, avgItem, null);

        // Back button
        ItemStack backItem = new ItemBuilder(Material.ARROW)
                .name("&aGo Back")
                .lore("&7Return to SkyBlock Menu", "", "&eClick to go back!")
                .hideFlags()
                .build();
        setItem(49, backItem, event -> {
            Player p = (Player) event.getWhoClicked();
            menuManager.openMenu(p, new MainSkyBlockMenu(menuManager));
        });

        // Close button
        ItemStack closeItem = new ItemBuilder(Material.BARRIER)
                .name("&cClose")
                .lore("&7Close this menu.")
                .hideFlags()
                .build();
        setItem(53, closeItem, event -> event.getWhoClicked().closeInventory());
    }

    private ItemStack buildSkillItem(SkillType type, SkillData data) {
        int level = data.getLevel();
        double xp = data.getXp();
        int maxLevel = SkillLevelCalculator.getMaxLevel(type.getId());
        double progress = SkillLevelCalculator.getProgressToNextLevel(type.getId(), xp);
        double xpToNext = SkillLevelCalculator.getXpToNextLevel(type.getId(), xp);

        String progressBar = ColorUtil.progressBar(progress, 1.0, 20, '█', '░', "&a", "&8");

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Level: &a" + ColorUtil.romanNumeral(level) + " &8/ &a" + ColorUtil.romanNumeral(maxLevel));
        lore.add("");

        if (level >= maxLevel) {
            lore.add("&6Max Level Reached!");
            lore.add(progressBar + " &a100%");
        } else {
            lore.add(progressBar + " &e" + String.format("%.1f%%", progress * 100));
            lore.add("");
            lore.add("&7XP: &e" + ColorUtil.formatNumber((long) xp));
            lore.add("&7Next Level: &e" + ColorUtil.formatNumber((long) xpToNext) + " XP");
        }

        lore.add("");
        lore.add("&eClick for more info!");

        String[] loreArr = lore.toArray(new String[0]);
        ItemStack item = new ItemBuilder(type.getIcon())
                .name(type.getColoredName() + " &e" + ColorUtil.romanNumeral(level))
                .lore(loreArr)
                .customModelData(type.getCustomModelData())
                .hideFlags()
                .build();
        NBTUtil.setString(menuManager.getPlugin(), item, "skill_type", type.getId());
        return item;
    }

    private SkillManager getSkillManager() {
        return (SkillManager) menuManager.getPlugin().getServer()
                .getServicesManager()
                .load(SkillManager.class);
    }
}
