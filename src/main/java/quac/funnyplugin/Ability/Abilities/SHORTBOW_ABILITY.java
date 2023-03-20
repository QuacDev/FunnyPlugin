package quac.funnyplugin.Ability.Abilities;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import quac.funnyplugin.Ability.AbilityBase;
import quac.funnyplugin.Ability.AbilityUseButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SHORTBOW_ABILITY extends AbilityBase {
    private final int amount;

    public SHORTBOW_ABILITY(AbilityUseButton button, int amount) {
        super(button);
        this.name = "Shortbow";
        this.cooldownTicks = 3;
        this.description = "Instantly shoots &a" + amount + "&7 arrows!";
        this.amount = amount;
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }

        Player p = event.getPlayer();
        Location origin = p.getEyeLocation();
        Vector direction = origin.getDirection();
        origin.add(direction.clone().multiply(2));

        List<Vector> directions = new ArrayList<>();

        if(amount == 1) {
            directions.add(direction);
        } else {
            for(int i = 0; i < amount; i++) {
                Location newLoc = origin.clone();
                newLoc.setYaw(newLoc.getYaw() - (i*5-5));
                directions.add(newLoc.getDirection());
            }
        }

        for (Vector dir : directions) {
            Arrow arrow = p.getWorld().spawnArrow(origin, dir, 5f, 0f);
            arrow.setCritical(false);
            arrow.setShooter(p);
        }

        return true;
    }
}
