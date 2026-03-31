package com.skyblock.skills;

import com.skyblock.database.SkillLevelCalculator;
import com.skyblock.menu.MenuManager;
import com.skyblock.menu.SkyBlockMenu;
import com.skyblock.utils.ColorUtil;
import com.skyblock.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkillDetailMenu extends SkyBlockMenu {

    private final SkillType skillType;

    public SkillDetailMenu(MenuManager menuManager, SkillType skillType) {
        super(menuManager);
        this.skillType = skillType;
    }

    @Override
    public String getTitle() {
        return "&0" + skillType.getDisplayName() + " Skill";
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
        SkillData data = profile.getSkill(skillType);
        double xp = data.getXp();
        int level = data.getLevel();
        int maxLevel = SkillLevelCalculator.getMaxLevel(skillType.getId());

        // Main skill display item at slot 13
        double progress = SkillLevelCalculator.getProgressToNextLevel(skillType.getId(), xp);
        double xpToNext = SkillLevelCalculator.getXpToNextLevel(skillType.getId(), xp);
        String progressBar = ColorUtil.progressBar(progress, 1.0, 24, '█', '░', "&a", "&8");

        List<String> mainLore = new ArrayList<>();
        mainLore.add("");
        mainLore.add("&7Level: &a" + ColorUtil.romanNumeral(level) + " &8/ &a" + ColorUtil.romanNumeral(maxLevel));
        mainLore.add("&7XP: &e" + String.format("%,.0f", xp));
        mainLore.add("");
        if (level < maxLevel) {
            mainLore.add("&7Progress to " + ColorUtil.romanNumeral(level + 1) + ":");
            mainLore.add(progressBar);
            mainLore.add("&e" + ColorUtil.formatNumber((long) xpToNext) + " XP &7to go");
        } else {
            mainLore.add("&6You have reached max level!");
            mainLore.add(progressBar + " &a100%");
        }
        mainLore.add("");

        appendSkillBonuses(mainLore, level);

        ItemStack mainItem = new ItemBuilder(skillType.getIcon())
                .name(skillType.getColoredName() + " &e" + ColorUtil.romanNumeral(level))
                .lore(mainLore.toArray(new String[0]))
                .customModelData(skillType.getCustomModelData())
                .hideFlags()
                .build();
        setItem(13, mainItem, null);

        // Level display row (slots 19-25, 28-34) showing levels
        int startLevel = Math.max(1, level - 3);
        int endLevel = Math.min(maxLevel, startLevel + 6);
        int[] levelSlots = {28, 29, 30, 31, 32, 33, 34};
        for (int i = 0; i < levelSlots.length; i++) {
            int displayLevel = startLevel + i;
            if (displayLevel > maxLevel) break;
            boolean unlocked = level >= displayLevel;
            boolean current = level == displayLevel - 1;

            Material mat = unlocked ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
            if (current) mat = Material.YELLOW_STAINED_GLASS_PANE;

            List<String> lvlLore = new ArrayList<>();
            lvlLore.add("");
            lvlLore.add((unlocked ? "&aUnlocked" : "&cLocked"));
            lvlLore.add("&7Required XP: &e" +
                ColorUtil.formatNumber(SkillLevelCalculator.getTotalXpForLevel(skillType.getId(), displayLevel)));

            ItemStack lvlItem = new ItemBuilder(mat)
                    .name((unlocked ? "&a" : "&c") + skillType.getDisplayName() + " " + ColorUtil.romanNumeral(displayLevel))
                    .lore(lvlLore.toArray(new String[0]))
                    .hideFlags()
                    .build();
            setItem(levelSlots[i], lvlItem, null);
        }

        // XP sources info at slot 22
        List<String> sourcesList = buildXpSources();
        ItemStack xpSourceItem = new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .name("&aXP Sources")
                .lore(sourcesList.toArray(new String[0]))
                .hideFlags()
                .build();
        setItem(22, xpSourceItem, null);

        // Back button
        ItemStack backItem = new ItemBuilder(Material.ARROW)
                .name("&aGo Back")
                .lore("&7Return to Skills Menu", "", "&eClick to go back!")
                .hideFlags()
                .build();
        setItem(49, backItem, event -> {
            Player p = (Player) event.getWhoClicked();
            menuManager.openMenu(p, new SkillsMenu(menuManager));
        });

        // Close
        ItemStack closeItem = new ItemBuilder(Material.BARRIER)
                .name("&cClose")
                .lore("&7Close this menu.")
                .hideFlags()
                .build();
        setItem(53, closeItem, event -> event.getWhoClicked().closeInventory());
    }

    private void appendSkillBonuses(List<String> lore, int level) {
        lore.add("&6Skill Bonuses:");
        switch (skillType) {
            case FARMING:
                lore.add("&7+" + (level * 4) + " &eFarming Fortune");
                lore.add("&7+&e" + level + "&7% Chance for double drops");
                break;
            case MINING:
                lore.add("&7+" + (level * 4) + " &eMining Fortune");
                lore.add("&7+&e" + level + "&7% Extra XP from ores");
                break;
            case COMBAT:
                lore.add("&7+" + (level * 4) + " &cCrit Damage");
                lore.add("&7+&e" + (level * 2) + "&7 Health");
                break;
            case FISHING:
                lore.add("&7+" + level + "&7% Fishing Speed");
                lore.add("&7+&e" + level + "&7% Sea Creature Chance");
                break;
            case FORAGING:
                lore.add("&7+" + (level * 4) + " &eForaging Fortune");
                lore.add("&7+&e" + level + "&7% Chance for double logs");
                break;
            case ENCHANTING:
                lore.add("&7+" + (level * 4) + " &dMagic Find");
                lore.add("&7+&e" + level + "&7% Enchanting Speed");
                break;
            case ALCHEMY:
                lore.add("&7+" + level + "&7% Potion Duration");
                lore.add("&7+&e" + level + "&7% Brew Speed");
                break;
            case TAMING:
                lore.add("&7+" + level + "&7% Pet XP");
                lore.add("&7+&e" + (level / 2) + "&7 Pet Luck");
                break;
            case CARPENTRY:
                lore.add("&7+" + level + "&7% Crafting Yield Chance");
                break;
            case RUNECRAFTING:
                lore.add("&7+" + level + "&7% Rune Power");
                break;
            default:
                lore.add("&7No bonuses yet.");
                break;
        }
    }

    private List<String> buildXpSources() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("&7Ways to earn XP:");
        list.add("");
        switch (skillType) {
            case FARMING:
                list.add("&e• Harvest crops");
                list.add("&e• Breed animals");
                list.add("&e• Complete farming minions");
                break;
            case MINING:
                list.add("&e• Mine ores and stone");
                list.add("&e• Complete mining minions");
                list.add("&e• Deep caverns mining");
                break;
            case COMBAT:
                list.add("&e• Kill mobs");
                list.add("&e• Complete dungeons");
                list.add("&e• Defeat bosses");
                break;
            case FISHING:
                list.add("&e• Fish in any water");
                list.add("&e• Catch sea creatures");
                list.add("&e• Fishing festivals");
                break;
            case FORAGING:
                list.add("&e• Chop trees");
                list.add("&e• Foraging island");
                list.add("&e• Complete foraging minions");
                break;
            case ENCHANTING:
                list.add("&e• Enchant items");
                list.add("&e• Craft enchanted books");
                list.add("&e• Use anvil");
                break;
            case ALCHEMY:
                list.add("&e• Brew potions");
                list.add("&e• Craft splash potions");
                list.add("&e• Nether wart farming");
                break;
            case TAMING:
                list.add("&e• Use pets in combat");
                list.add("&e• Pet leveling");
                list.add("&e• Pet battles");
                break;
            default:
                list.add("&e• Various activities");
                break;
        }
        return list;
    }

    private SkillManager getSkillManager() {
        return (SkillManager) menuManager.getPlugin().getServer()
                .getServicesManager()
                .load(SkillManager.class);
    }
}
