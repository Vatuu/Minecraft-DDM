package net.arikia.ddm.utils;

import org.bukkit.ChatColor;

public final class TextUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
