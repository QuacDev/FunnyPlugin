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


public class GameState extends CustomCommand {
    public GameState() {
        super();
        this.argsAmount = 2;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        byte reason = Byte.parseByte(args[0]);
        float value = Float.parseFloat(args[1]);
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(reason,value));
        player.sendMessage(Text.translate("&dSent game state to &f" + reason + "&d and &e" + value));
    }
}
