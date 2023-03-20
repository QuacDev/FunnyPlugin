package quac.funnyplugin.Item.Items.Weapons.WitherBlades;

import quac.funnyplugin.Custom.StatType;

public class HYPERION extends NECRONS_BLADE {
    public HYPERION() {
        super();
        this.displayName = "Hyperion";
        this.id = "HYPERION";

        this.baseStats.replace(StatType.INTELLIGENCE, 350d);
    }
}
