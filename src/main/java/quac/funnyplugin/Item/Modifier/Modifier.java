package quac.funnyplugin.Item.Modifier;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.ItemRegistry;
import quac.funnyplugin.utils.Keys;

import java.util.HashMap;

public class Modifier {
    HashMap<StatType, Double> stats = new HashMap<>();
    double rarityMultiplier = 0.2;
    String id = "";
    String display = "";

    public Modifier() {}

    public String getDisplay() {
        return display;
    }

    public String getId() {
        return id;
    }

    public HashMap<StatType, Double> getStats() {
        return stats;
    }

    public double getRarityMultiplier() {
        return rarityMultiplier;
    }

    public static HashMap<StatType, Double> getModifierStats(NBTTagCompound extraAttributes) {
        String id = extraAttributes.getString(Keys.ID_KEY);

        HashMap<StatType, Double> newStats = new HashMap<>();
        int rarityUpgrades = 0;
        if(extraAttributes.hasKey(Keys.RARITY_UPGRADE)) {
            rarityUpgrades = extraAttributes.getInt(Keys.RARITY_UPGRADE);
        }
        Modifier modifier = ModifierRegistry.getModifier(extraAttributes.getString(Keys.MODIFIER));
        if(modifier!=null) {
            int multiplier;
            if(rarityUpgrades>0) {
                multiplier = Math.round((float) (modifier.getRarityMultiplier() + 1) * rarityUpgrades);
            } else {
                multiplier = Math.round((float) (modifier.getRarityMultiplier() + 1));
            }

            modifier.getStats().forEach((statType, aDouble) -> {
                newStats.put(statType, aDouble * multiplier);
            });

            return newStats;
        } else {
            return new HashMap<>();
        }

    }
}
