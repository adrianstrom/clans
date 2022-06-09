package com.minenorge.utils;

import org.bukkit.ChatColor;

public class Utils {
    private static String serverName = Utils.chat("&2[Klan]&r ");
    
    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String success(String s) {
        return ChatColor.translateAlternateColorCodes('&', serverName + "&a" + s);
    }
    
    public static String fail(String s) {
        return ChatColor.translateAlternateColorCodes('&', serverName + "&c" + s);
    }
}