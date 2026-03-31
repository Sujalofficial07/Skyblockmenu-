package com.skyblock.collections;

public class CollectionData {

    private final CollectionType type;
    private long amount;
    private int tier;

    public CollectionData(CollectionType type, long amount, int tier) {
        this.type = type;
        this.amount = amount;
        this.tier = tier;
    }

    public CollectionType getType() { return type; }
    public long getAmount() { return amount; }
    public int getTier() { return tier; }
    public void setAmount(long amount) { this.amount = amount; }
    public void setTier(int tier) { this.tier = tier; }
}
