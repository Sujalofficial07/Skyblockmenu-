package com.skyblock.stats;

import java.util.HashMap;
import java.util.UUID;

public class StatsManager {

    private static final HashMap<UUID, HashMap<String, Double>> stats = new HashMap<>();

    public static void init(UUID uuid){
        stats.putIfAbsent(uuid,new HashMap<>());
        set(uuid,"health",100);
        set(uuid,"defense",0);
        set(uuid,"strength",0);
        set(uuid,"speed",100);
        set(uuid,"crit_chance",30);
        set(uuid,"crit_damage",50);
        set(uuid,"intelligence",100);
        set(uuid,"ferocity",0);
        set(uuid,"attack_speed",0);
        set(uuid,"true_defense",0);

        set(uuid,"mining_speed",0);
        set(uuid,"mining_fortune",0);
        set(uuid,"farming_fortune",0);
        set(uuid,"foraging_fortune",0);

        set(uuid,"magic_find",0);
        set(uuid,"pet_luck",0);
        set(uuid,"fishing_speed",0);
    }

    public static void set(UUID uuid,String stat,double value){
        stats.putIfAbsent(uuid,new HashMap<>());
        stats.get(uuid).put(stat,value);
    }

    public static double get(UUID uuid,String stat){
        return stats.getOrDefault(uuid,new HashMap<>()).getOrDefault(stat,0.0);
    }
}
