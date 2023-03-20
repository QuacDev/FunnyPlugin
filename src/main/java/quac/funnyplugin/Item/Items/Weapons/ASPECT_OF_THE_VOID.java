package quac.funnyplugin.Item.Items.Weapons;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.Abilities.ETHER_TRANSMISSION_ABILITY;
import quac.funnyplugin.Ability.Abilities.SHORTBOW_ABILITY;
import quac.funnyplugin.Ability.Abilities.TELEPORT_ABILITY;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

import java.sql.SQLException;

public class ASPECT_OF_THE_VOID extends ItemBase {
    public ASPECT_OF_THE_VOID() {
        super();

        this.baseRarity = Rarity.EPIC;
        this.mat = Material.DIAMOND_SPADE;
        this.displayName = "Aspect of the Void";
        this.autoColorDisplay = true;
        this.id = "ASPECT_OF_THE_VOID";

        this.baseStats.put(StatType.CRITICAL_CHANCE, 100d);

        this.hasAbility = true;
        abilities.add(new TELEPORT_ABILITY(AbilityUseButton.RIGHT_CLICK, false, 8));
        abilities.add(new ETHER_TRANSMISSION_ABILITY(AbilityUseButton.SHIFT_RIGHT_CLICK, false, 54));
    }

    @Override
    public void useAbility(PlayerInteractEvent event) throws SQLException {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!event.getPlayer().isSneaking()) {
                abilities.get(0).use(event);
            } else {
                abilities.get(1).use(event);
            }
        }
    }
}
