package quac.funnyplugin.Commands;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class Particle extends CustomCommand {
    public Particle() {
        super();
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        Location loc = player.getLocation();

        int a = Integer.parseInt(args[0]);
        if(a>35) {
            player.sendMessage(Text.translate("&cNuh uh that dont exist"));
            return;
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            EntityPlayer nmsPlayer = ((CraftPlayer) onlinePlayer).getHandle();

            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                    EnumParticle.a(a), true, (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(),
                    0.05f, 0.05f, 0.05f, 0.01f, 1
            );

            nmsPlayer.playerConnection.sendPacket(packet);
        }
    }
}
