package quac.funnyplugin.Item.Items.Armor;

import org.bukkit.Material;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

public class QUAC_HELMET extends ItemBase {
    public QUAC_HELMET() {
        super();

        this.baseRarity = Rarity.SPECIAL;
        this.mat = Material.GOLD_HELMET;
        this.displayName = "Quac Helmet";
        this.autoColorDisplay = true;
        this.id = "QUAC_HELMET";

        this.baseStats.put(StatType.INTELLIGENCE, 99999d);
    }
}
