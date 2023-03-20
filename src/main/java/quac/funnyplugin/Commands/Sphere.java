package quac.funnyplugin.Commands;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Main;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class Sphere extends CustomCommand {
    public Sphere() {
        this.argsType = ArgsType.Min;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        int ticks = Integer.parseInt(args[0]);
        AtomicInteger currentTicks = new AtomicInteger();

        int detail;
        int radiusMultiplier;

        EnumParticle particle = null;

        if(args.length >= 2) {
            for (EnumParticle value : EnumParticle.values()) {
                if(value.name().equalsIgnoreCase(args[1])) {
                    particle = value;
                }
            }
            if (particle==null) {
                particle = EnumParticle.SMOKE_NORMAL;
            }
        } else {
            particle = EnumParticle.SMOKE_NORMAL;
        }

        if(args.length >= 3) {
            detail = Integer.parseInt(args[2]);
        } else {
            detail = 10;
        }

        if(args.length == 4) {
            radiusMultiplier = Integer.parseInt(args[3]);
        } else {
            radiusMultiplier = 1;
        }

        EnumParticle finalParticle = particle;
        Bukkit.getScheduler().runTaskTimer(Main.plugin, () -> {
            currentTicks.getAndIncrement();
            if(currentTicks.intValue() >= ticks) {
                return;
            }

            Location center = player.getLocation().add(0, 1, 0);

            for(double i = 0; i <= Math.PI; i += Math.PI / detail) {
                double radius = Math.sin(i) * radiusMultiplier;
                double y = Math.cos(i) * radiusMultiplier;

                for(double a = 0; a < Math.PI * 2; a += Math.PI / detail) {
                    double x = Math.cos(a) * radius;
                    double z = Math.sin(a) * radius;

                    Location loc = center.clone().add(x,y,z);

                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        EntityPlayer nmsPlayer = ((CraftPlayer) onlinePlayer).getHandle();
                        PlayerConnection connection = nmsPlayer.playerConnection;

                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                finalParticle, true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                                0.05f, 0.05f, 0.05f, 0.01f, 1
                        );

                        connection.sendPacket(packet);
                    }
                }
            }


        }, 1, 0);
    }
}
