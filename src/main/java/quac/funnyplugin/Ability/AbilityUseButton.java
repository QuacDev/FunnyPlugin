package quac.funnyplugin.Ability;

public enum AbilityUseButton {
    LEFT_CLICK("LEFT_CLICK", "&e&lLEFT CLICK"),
    SHIFT_LEFT_CLICK("SHIFT_LEFT_CLICK", "&e&lSHIFT LEFT CLICK"),
    RIGHT_CLICK("RIGHT_CLICK", "&e&lRIGHT CLICK"),
    SHIFT_RIGHT_CLICK("SHIFT_RIGHT_CLICK", "&e&lSHIFT RIGHT CLICK");

    final String button;
    final String display;
    AbilityUseButton(String button, String Display) {
        this.button = button;
        this.display = Display;
    }
}
