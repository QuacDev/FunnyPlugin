package quac.funnyplugin.Item.Items.Weapons;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.Abilities.PARLEY_ABILITY;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

import java.sql.SQLException;

public class ASPECT_OF_THE_JERRY extends ItemBase {
    public ASPECT_OF_THE_JERRY() {
        super();

        this.baseRarity = Rarity.COMMON;
        this.mat = Material.WOOD_SWORD;
        this.displayName = "Aspect Of The Jerry";
        this.autoColorDisplay = true;
        this.id = "ASPECT_OF_THE_JERRY";

        this.hasAbility = true;

        this.baseStats.put(StatType.BASE_MELEE_DAMAGE, 1d);

        this.abilities.add(new PARLEY_ABILITY(AbilityUseButton.RIGHT_CLICK));
    }

    @Override
    public void useAbility(PlayerInteractEvent event) throws SQLException {
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                if(!event.getPlayer().isSneaking()) {
                    abilities.get(0).use(event);
                }
                break;
        }
    }
}
