package com.sujal.hypixelcore.data;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static final Map<Player, PlayerData> playerDataMap = new HashMap<>();

    public static void loadPlayer(Player player) {
        // Future Database GET logic yahan aayega
        playerDataMap.put(player, new PlayerData(player));
    }

    public static void unloadPlayer(Player player) {
        // Future Database SAVE logic yahan aayega
        playerDataMap.remove(player);
    }

    public static PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player);
    }
}
