package quac.funnyplugin.Item.Items.Weapons.WitherBlades;

import quac.funnyplugin.Custom.StatType;

public class VALKYRIE extends NECRONS_BLADE {
    public VALKYRIE() {
        super();
        this.displayName = "Valkyrie";
        this.id = "VALKYRIE";

        this.baseStats.replace(StatType.INTELLIGENCE, 60d);
        this.baseStats.replace(StatType.STRENGTH, 145d);
        this.baseStats.replace(StatType.BASE_MELEE_DAMAGE, 270d);
    }
}
