package com.skyblock.stats;

import java.util.HashMap;
import java.util.UUID;

public class StatsManager {

    private static final HashMap<UUID, HashMap<String, Double>> stats = new HashMap<>();

    public static void init(UUID uuid){
        stats.putIfAbsent(uuid,new HashMap<>());

        set(uuid,"health",1719);
        set(uuid,"defense",1208);
        set(uuid,"strength",459);
        set(uuid,"speed",155);
        set(uuid,"crit_chance",118);
        set(uuid,"crit_damage",483);
        set(uuid,"intelligence",385);
        set(uuid,"attack_speed",5);
        set(uuid,"true_defense",6);
        set(uuid,"ferocity",0);

        set(uuid,"mining_speed",451);
        set(uuid,"mining_fortune",253);
        set(uuid,"farming_fortune",76);
        set(uuid,"foraging_fortune",72);

        set(uuid,"magic_find",32);
        set(uuid,"pet_luck",40);
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
