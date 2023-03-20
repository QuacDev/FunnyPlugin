package quac.funnyplugin.Item.Modifier.Modifiers;

import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.Modifier.Modifier;

import java.util.HashMap;

public class FABLED extends Modifier {
    @Override
    public String getDisplay() {
        return "Fabled ";
    }

    @Override
    public String getId() {
        return "fabled";
    }

    @Override
    public HashMap<StatType, Double> getStats() {
        HashMap<StatType, Double> stats = new HashMap<>();

        stats.put(StatType.STRENGTH, 30d);
        stats.put(StatType.CRITICAL_DAMAGE, 15d);

        return stats;
    }

    @Override
    public double getRarityMultiplier() {
        return 0.2;
    }
}
