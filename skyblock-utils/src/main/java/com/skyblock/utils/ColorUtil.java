package com.skyblock.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public final class ColorUtil {

    private static final MiniMessage MM = MiniMessage.miniMessage();

    private ColorUtil() {}

    public static Component color(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }

    public static Component mini(String text) {
        return MM.deserialize(text);
    }

    public static List<Component> colorLore(List<String> lore) {
        List<Component> result = new ArrayList<>();
        for (String line : lore) {
            result.add(color(line));
        }
        return result;
    }

    public static String progressBar(double current, double max, int bars, char filledChar, char emptyChar,
                                     String filledColor, String emptyColor) {
        int filled = (int) Math.round((current / max) * bars);
        filled = Math.min(filled, bars);
        StringBuilder sb = new StringBuilder();
        sb.append(filledColor);
        for (int i = 0; i < filled; i++) sb.append(filledChar);
        sb.append(emptyColor);
        for (int i = filled; i < bars; i++) sb.append(emptyChar);
        return sb.toString();
    }

    public static String formatNumber(long number) {
        if (number >= 1_000_000_000L) return String.format("%.1fB", number / 1_000_000_000.0);
        if (number >= 1_000_000L) return String.format("%.1fM", number / 1_000_000.0);
        if (number >= 1_000L) return String.format("%.1fk", number / 1_000.0);
        return String.valueOf(number);
    }

    public static String romanNumeral(int number) {
        if (number <= 0) return "0";
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return thousands[number / 1000] +
               hundreds[(number % 1000) / 100] +
               tens[(number % 100) / 10] +
               ones[number % 10];
    }
}
