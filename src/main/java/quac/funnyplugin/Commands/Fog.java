package quac.funnyplugin.Commands;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class Fog extends CustomCommand {
    public Fog() {
        super();
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        float value = Float.parseFloat(args[0]);
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(2, 0));
        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, 1000f));
        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, value));
        player.sendMessage(Text.translate("&dSent rain game state to &f" + value));
    }
}
