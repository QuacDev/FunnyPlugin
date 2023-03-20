package quac.funnyplugin.Ability.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.AbilityBaseWithCost;
import quac.funnyplugin.Ability.AbilityUseButton;

import java.sql.SQLException;

public class SHADOW_FURY_ABILITY extends AbilityBaseWithCost {

    public SHADOW_FURY_ABILITY(AbilityUseButton button, int healAmount) {
        super(button);
        this.name = "Shadow Fury";
        this.cooldownTicks = 100;
        this.description = "Rapidly teleports you to up to 5 enemies within 12 blocks, rooting each of them and allowing you to hit them.";
        this.manaCost = 0;
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }
        Player player = event.getPlayer();



        return true;
    }
}