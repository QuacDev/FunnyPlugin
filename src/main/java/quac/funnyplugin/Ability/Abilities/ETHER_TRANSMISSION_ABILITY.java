package quac.funnyplugin.Ability.Abilities;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Ability.AbilityBaseWithCost;
import quac.funnyplugin.Ability.AbilityUseButton;

import java.sql.SQLException;
import java.util.Set;

public class ETHER_TRANSMISSION_ABILITY extends TELEPORT_ABILITY {
    public ETHER_TRANSMISSION_ABILITY(AbilityUseButton button, boolean explosion, int teleportDistance) {
        super(button, explosion, 57);

        this.name = "Ether Transmission";
        this.cooldownTicks = 3;
        this.description = "Teleport to your targetted block up to &a"+ teleportDistance + " blocks &7away.";
        this.manaCost = 0;
        this.explosion = explosion;
        this.teleportDistance = teleportDistance;
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }

        Player player = event.getPlayer();
        World world = player.getWorld();
        Location loc = player.getLocation();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            EntityPlayer nmsPlayer = ((CraftPlayer) onlinePlayer).getHandle();

            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                    EnumParticle.PORTAL, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                    0.2f, 0.2f, 0.2f, 0.01f, 60
            );

            nmsPlayer.playerConnection.sendPacket(packet);
        }
        return true;
    }
}
