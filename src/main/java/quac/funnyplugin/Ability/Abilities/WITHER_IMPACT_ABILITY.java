package quac.funnyplugin.Ability.Abilities;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import quac.funnyplugin.Ability.AbilityUseButton;

import java.sql.SQLException;

public class WITHER_IMPACT_ABILITY extends TELEPORT_ABILITY {
    public WITHER_IMPACT_ABILITY(AbilityUseButton button, boolean explosion) {

        super(button, explosion, 10);
        this.name = "Wither Impact";
        this.description = "&7Teleport &e10 &7blocks ahead of you. Then implode, dealing &c10,000&7 damage to nearby enemies. Also applies the wither shield scroll ability, reducing damage taken and granting an absorption shield for &e5&7 seconds.";
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }

        Player player = event.getPlayer();
        World world = player.getWorld();
        Location loc = player.getLocation();

        if (explosion) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                EntityPlayer nmsPlayer = ((CraftPlayer) onlinePlayer).getHandle();

                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                        EnumParticle.a(1), true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                        0.05f, 0.05f, 0.05f, 5, 5
                );

                onlinePlayer.playSound(player.getLocation(), Sound.EXPLODE, 1f, 2f);

                nmsPlayer.playerConnection.sendPacket(packet);
            }
        }

        for (Entity nearbyEntity : world.getNearbyEntities(loc, 5, 5, 5)) {
            if(nearbyEntity.isValid()) {
                if(nearbyEntity instanceof LivingEntity) {
                    if(nearbyEntity != player) {
                        LivingEntity livingEntity = (LivingEntity) nearbyEntity;
                        livingEntity.damage(10000, player);
                    }
                }
            }
        }

        return true;
    }
}
