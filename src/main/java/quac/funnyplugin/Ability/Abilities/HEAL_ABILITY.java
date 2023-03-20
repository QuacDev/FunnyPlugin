package quac.funnyplugin.Ability.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.AbilityBaseWithCost;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Entity.PlayerBase;

import java.sql.SQLException;

public class HEAL_ABILITY extends AbilityBaseWithCost {

    int healAmount;

    public HEAL_ABILITY(AbilityUseButton button, int healAmount) {
        super(button);
        this.name = "Heal Ability";
        this.cooldownTicks = 100;
        this.description = "Heals you for "+ healAmount + " Health!";
        this.manaCost = 0;
        this.healAmount = healAmount;
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }
        Player player = event.getPlayer();

        double newHealth = player.getHealth()+healAmount;
        if(newHealth > player.getMaxHealth()) newHealth = player.getMaxHealth();

        player.setHealth(newHealth);



        return true;
    }
}