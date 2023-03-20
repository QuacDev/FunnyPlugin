package quac.funnyplugin.utils;

import org.bukkit.ChatColor;

public class Text {
    public static String translate(String a) {
        return ChatColor.translateAlternateColorCodes('&', a);
    }
}
