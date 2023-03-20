package quac.funnyplugin.Item.Items.ReforgeStones;

import org.bukkit.Material;
import quac.funnyplugin.Item.Head;
import quac.funnyplugin.Item.Rarity;
import quac.funnyplugin.Item.ReforgeStone;

public class RECOMBOBULATOR extends ReforgeStone {
    public RECOMBOBULATOR() {
        super(Head.RECOMBOBULATOR);

        this.id = "RECOMBOBULATOR_3000";
        this.mat = Material.SKULL_ITEM;
        this.displayName = "Recombobulator 3000";
        this.baseRarity = Rarity.LEGENDARY;
    }
}
