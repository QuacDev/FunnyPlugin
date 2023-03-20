package quac.funnyplugin.Item.Items.Misc;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.Abilities.HEAL_ABILITY;
import quac.funnyplugin.Ability.Abilities.INSTANT_HEAL_ABILITY;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;

import java.sql.SQLException;

public class FLORID_ZOMBIE_SWORD extends ItemBase {
    public FLORID_ZOMBIE_SWORD() {
        super();

        this.baseRarity = Rarity.LEGENDARY;
        this.mat = Material.GOLD_SWORD;
        this.displayName = "Florid Zombie Sword";
        this.autoColorDisplay = true;
        this.id = "FLORID_ZOMBIE_SWORD";

        this.baseStats.put(StatType.CRITICAL_CHANCE, 100d);

        this.hasAbility = true;
        abilities.add(new INSTANT_HEAL_ABILITY(AbilityUseButton.RIGHT_CLICK, 25));
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
