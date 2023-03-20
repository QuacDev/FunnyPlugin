package quac.funnyplugin.Item.Items.Misc;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.Abilities.MATH_ABILITY;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

import java.sql.SQLException;

public class MATH_WAND extends ItemBase {
    public MATH_WAND() {
        super();

        this.baseRarity = Rarity.EPIC;
        this.mat = Material.BLAZE_ROD;
        this.displayName = "Funny Stick";
        this.autoColorDisplay = true;
        this.id = "MATH_WAND";

        this.hasAbility = true;
        abilities.add(new MATH_ABILITY(AbilityUseButton.LEFT_CLICK));
    }

    @Override
    public void useAbility(PlayerInteractEvent event) throws SQLException {
        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                if(!event.getPlayer().isSneaking()) {
                    abilities.get(0).use(event);
                }
                break;
        }
    }
}
