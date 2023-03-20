package quac.funnyplugin.Item.Items.Weapons.WitherBlades;

import quac.funnyplugin.Custom.StatType;

public class ASTRAEA extends NECRONS_BLADE {
    public ASTRAEA() {
        super();
        this.displayName = "Astraea";
        this.id = "ASTRAEA";

        this.baseStats.replace(StatType.STRENGTH, 150d);
        this.baseStats.replace(StatType.BASE_MELEE_DAMAGE, 270d);
        this.baseStats.put(StatType.DEFENSE, 250d);
    }
}
