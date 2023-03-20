package quac.funnyplugin.Ability.Abilities;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.AbilityBaseWithCost;
import quac.funnyplugin.Ability.AbilityUseButton;

import java.sql.SQLException;

public class PARLEY_ABILITY extends AbilityBaseWithCost {
    public PARLEY_ABILITY(AbilityUseButton button) {
        super(button);
        this.name = "Parley";
        this.cooldownTicks = 100;
        this.description = "Plays a Villager Sound";
        this.manaCost = 5;
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }

        Player p = event.getPlayer();

        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 2f, 2f);

        return true;
    }
}
