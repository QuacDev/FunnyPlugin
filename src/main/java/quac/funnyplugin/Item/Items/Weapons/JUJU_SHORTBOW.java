package quac.funnyplugin.Item.Items.Weapons;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.Abilities.SHORTBOW_ABILITY;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

import java.sql.SQLException;

public class JUJU_SHORTBOW extends ItemBase {
    public JUJU_SHORTBOW() {
        super();

        this.baseRarity = Rarity.EPIC;
        this.mat = Material.BOW;
        this.displayName = "Juju Shortbow";
        this.autoColorDisplay = true;
        this.id = "JUJU_SHORTBOW";

        this.baseStats.put(StatType.CRITICAL_CHANCE, 100d);

        this.hasAbility = true;
        abilities.add(new SHORTBOW_ABILITY(AbilityUseButton.LEFT_CLICK, 1));
    }

    @Override
    public void useAbility(PlayerInteractEvent event) throws SQLException {
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            if (!event.getPlayer().isSneaking()) {
                abilities.get(0).use(event);
            }
        }
    }
}
