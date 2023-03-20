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

public class TERMINATOR extends ItemBase {
    public TERMINATOR() {
        super();

        this.baseRarity = Rarity.LEGENDARY;
        this.mat = Material.BOW;
        this.displayName = "Terminator";
        this.autoColorDisplay = true;
        this.id = "TERMINATOR";

        this.baseStats.put(StatType.BASE_MELEE_DAMAGE, 310d);
        this.baseStats.put(StatType.CRITICAL_DAMAGE, 250d);
        this.baseStats.put(StatType.STRENGTH, 50d);
        this.baseStats.put(StatType.CRITICAL_CHANCE, 40d);


        this.hasAbility = true;
        abilities.add(new SHORTBOW_ABILITY(AbilityUseButton.LEFT_CLICK, 3));
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
