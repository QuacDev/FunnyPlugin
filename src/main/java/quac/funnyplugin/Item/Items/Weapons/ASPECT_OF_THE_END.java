package quac.funnyplugin.Item.Items.Weapons;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.Abilities.SHORTBOW_ABILITY;
import quac.funnyplugin.Ability.Abilities.TELEPORT_ABILITY;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

import java.sql.SQLException;

public class ASPECT_OF_THE_END extends ItemBase {
    public ASPECT_OF_THE_END() {
        super();

        this.baseRarity = Rarity.RARE;
        this.mat = Material.DIAMOND_SWORD;
        this.displayName = "Aspect of the End";
        this.autoColorDisplay = true;
        this.id = "ASPECT_OF_THE_END";

        this.baseStats.put(StatType.CRITICAL_CHANCE, 100d);

        this.hasAbility = true;
        abilities.add(new TELEPORT_ABILITY(AbilityUseButton.RIGHT_CLICK, false, 8));
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
