package quac.funnyplugin.Item.Items.Armor;

import org.bukkit.Material;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

public class UNKNOWN_HELMET extends ItemBase {
    public UNKNOWN_HELMET() {
        super();

        this.baseRarity = Rarity.VERY_SPECIAL;
        this.mat = Material.DIAMOND_HELMET;
        this.displayName = "Unknown Helmet";
        this.autoColorDisplay = true;
        this.id = "UNKNOWN_HELMET";

        this.baseStats.put(StatType.DEFENSE, 1000d);
        this.baseStats.put(StatType.MAX_HEALTH, 10000d);
    }
}
