package com.skyblock.skills;

public class SkillData {

    private final SkillType type;
    private double xp;
    private int level;

    public SkillData(SkillType type, double xp, int level) {
        this.type = type;
        this.xp = xp;
        this.level = level;
    }

    public SkillType getType() { return type; }
    public double getXp() { return xp; }
    public int getLevel() { return level; }

    public void setXp(double xp) { this.xp = xp; }
    public void setLevel(int level) { this.level = level; }
}
