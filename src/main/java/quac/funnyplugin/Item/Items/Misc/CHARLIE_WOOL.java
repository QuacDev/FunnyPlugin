package quac.funnyplugin.Item.Items.Misc;

import org.bukkit.Material;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;
import quac.funnyplugin.utils.Color;

public class CHARLIE_WOOL extends ItemBase {
    public CHARLIE_WOOL() {
        super();

        this.id = "CHARLIE_WOOL";
        this.mat = Material.WOOL;
        this.displayName = Color.rainbowText("Charlie Wool", false);
        this.baseRarity = Rarity.LEGENDARY;
        this.autoColorDisplay = false;
    }
}
