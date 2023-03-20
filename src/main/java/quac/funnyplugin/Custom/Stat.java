package quac.funnyplugin.Custom;

import org.bukkit.entity.LivingEntity;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.utils.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Stat {
    StatType type;
    double value;

    public Stat(StatType type, double value) {
        this.type=type;
        this.value=value;
    }

    public static HashMap<StatType, Double> basePlayerStats() {
        HashMap<StatType, Double> stats = new HashMap<>();
        stats.put(StatType.MAX_HEALTH, 100d);
        stats.put(StatType.DEFENSE, 0d);
        stats.put(StatType.STRENGTH, 0d);
        stats.put(StatType.INTELLIGENCE, 100d);
        stats.put(StatType.CRITICAL_DAMAGE, 50d);
        stats.put(StatType.CRITICAL_CHANCE, 30d);
        stats.put(StatType.BASE_MELEE_DAMAGE, 0d);

        return stats;
    }

    public static HashMap<StatType, Double> getEmptyStats() {
        HashMap<StatType, Double> stats = new HashMap<>();
        for (StatType value : StatType.values()) {
            stats.put(value, 0d);
        }

        return stats;
    }

    public static HashMap<StatType, Double> baseEntityStats(LivingEntity et) {
        HashMap<StatType, Double> stats = getEmptyStats();

        stats.replace(StatType.MAX_HEALTH, et.getMaxHealth());
        stats.replace(StatType.DEFENSE, 5d);

        return stats;
    }

    public static List<String> getColoredStatsList(PlayerBase base) {
        List<String> lines = new ArrayList<>();
        HashMap<StatType, Double> stats = base.currentStats;

        lines.add(Text.translate("&c❤ Health &f" + (int) Math.floor(base.p.getHealth())));
        lines.add(Text.translate("&a❈ Defense &f" + (int) Math.floor(stats.get(StatType.DEFENSE))));
        lines.add(Text.translate("&f✦ Speed &f100"));
        lines.add(Text.translate("&c❁ Strength &f" + (int) Math.floor(stats.get(StatType.STRENGTH))));
        lines.add(Text.translate("&b✎ Intelligence &f" + (int) Math.floor(stats.get(StatType.INTELLIGENCE))));
        lines.add(Text.translate("&9☣ Crit Chance &f" + (int) Math.floor(stats.get(StatType.CRITICAL_CHANCE))));
        lines.add(Text.translate("&9☠ Crit Damage &f" + (int) Math.floor(stats.get(StatType.CRITICAL_DAMAGE))));
        lines.add(Text.translate("&c❁ Base Damage &f" + (int) Math.floor(stats.get(StatType.BASE_MELEE_DAMAGE))));

        return lines;
    }
}
