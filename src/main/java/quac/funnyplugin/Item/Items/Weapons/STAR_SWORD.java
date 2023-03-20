package quac.funnyplugin.Item.Items.Weapons;

import org.bukkit.Material;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

public class STAR_SWORD extends ItemBase {
    public STAR_SWORD() {
        super();

        this.baseRarity = Rarity.SPECIAL;
        this.mat = Material.GOLD_SWORD;
        this.displayName = "Sword of the Stars";
        this.autoColorDisplay = true;
        this.id = "STAR_SWORD";

        this.baseStats.put(StatType.BASE_MELEE_DAMAGE, 99999d);
        this.baseStats.put(StatType.CRITICAL_CHANCE, 100d);
        this.baseStats.put(StatType.MAX_HEALTH, 100d);
    }
}
