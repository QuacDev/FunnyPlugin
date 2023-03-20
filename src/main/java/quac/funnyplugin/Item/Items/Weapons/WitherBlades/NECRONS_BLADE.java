package quac.funnyplugin.Item.Items.Weapons.WitherBlades;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.Abilities.WITHER_IMPACT_ABILITY;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

import java.sql.SQLException;

public class NECRONS_BLADE extends ItemBase {
    public NECRONS_BLADE() {
        super();

        this.baseRarity = Rarity.LEGENDARY;
        this.mat = Material.IRON_SWORD;
        this.displayName = "Necron's Blade (Unrefined)";
        this.autoColorDisplay = true;
        this.id = "HYPERION";

        this.baseStats.put(StatType.BASE_MELEE_DAMAGE, 260d);
        this.baseStats.put(StatType.STRENGTH, 110d);
        this.baseStats.put(StatType.INTELLIGENCE, 50d);

        this.hasAbility = true;
        abilities.add(new WITHER_IMPACT_ABILITY(AbilityUseButton.RIGHT_CLICK, true));
    }

    @Override
    public void useAbility(PlayerInteractEvent event) throws SQLException {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!event.getPlayer().isSneaking()) {
                abilities.get(0).use(event);
            }
        }
    }
}
