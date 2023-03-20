package quac.funnyplugin.Ability.Abilities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import quac.funnyplugin.Ability.AbilityBaseWithCost;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Custom.Stat;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TELEPORT_ABILITY extends AbilityBaseWithCost {
    int teleportDistance;
    boolean explosion;

    public TELEPORT_ABILITY(AbilityUseButton button, boolean explosion, int teleportDistance) {
        super(button);
        this.name = "Instant Transmission";
        this.cooldownTicks = 3;
        this.description = "Teleport &a"+ teleportDistance + " blocks&7 ahead of you and gain &a+50 &f✦ Speed &7for &a3 seconds&7.";
        this.manaCost = 50;
        this.explosion = explosion;
        this.teleportDistance = teleportDistance;

    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }

        Player p = event.getPlayer();

        Block block = p.getTargetBlock((Set<Material>) null, teleportDistance);
        Location location = block.getLocation();
        float pitch = p.getEyeLocation().getPitch();
        float yaw = p.getEyeLocation().getYaw();

        location.add(0.5,1,0.5);
        location.setYaw(yaw);
        location.setPitch(pitch);

        p.teleport(location);

        return true;
    }
}
