package quac.funnyplugin.Item.Items.Weapons.WitherBlades;

import quac.funnyplugin.Custom.StatType;

public class SCYLLA extends NECRONS_BLADE {
    public SCYLLA() {
        super();
        this.displayName = "Scylla";
        this.id = "SCYLLA";

        this.baseStats.replace(StatType.INTELLIGENCE, 60d);
        this.baseStats.replace(StatType.STRENGTH, 150d);
        this.baseStats.replace(StatType.BASE_MELEE_DAMAGE, 270d);
        this.baseStats.put(StatType.CRITICAL_CHANCE, 12d);
        this.baseStats.put(StatType.CRITICAL_DAMAGE, 35d);
    }
}
