package quac.funnyplugin.Item;

import quac.funnyplugin.utils.Color;

public enum Rarity {
    COMMON(0, Color.WHITE, "&f&lCOMMON"),
    UNCOMMON(1, Color.LIME, "&a&lCOMMON"),
    RARE(2, Color.BLUE, "&9&lRARE"),
    EPIC(3, Color.PURPLE, "&5&lEPIC"),
    LEGENDARY(4, Color.GOLD, "&6&lLEGENDARY"),
    MYTHIC(5, Color.MAGENTA, "&d&lMYTHIC"),
    DIVINE(6, Color.AQUA, "&b&lDIVINE"),
    SPECIAL(7, Color.RED, "&c&lSPECIAL"),
    VERY_SPECIAL(8, Color.RED, "&c&lVERY SPECIAL"),
    RAINBOW(9, Color.WHITE, Color.rainbowText("RAINBOW", true));

    final String display;
    final Color color;
    final int index;

    Rarity(int index, Color color, String a) {
        this.index = index;
        this.display = a;
        this.color = color;
    }

    public static Rarity getRarityFromIndex(int a) {
        for (Rarity value : Rarity.values()) {
            if(value.index == a) {
                return value;
            }
        }
        return Rarity.COMMON;
    }
}
