package quac.funnyplugin.Commands;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import static quac.funnyplugin.Registry.invalidArgs;

public class DeepFog extends CustomCommand {
    public DeepFog() {
        super();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(super.onCommand(commandSender, command, s, args)) {

            float value = Float.parseFloat(args[0]);
            EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
            nmsPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(2, 0));
            nmsPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, 10f));
            nmsPlayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, value));
            player.sendMessage(Text.translate("&dSent deep rain game state to &f" + value));

            return false;
        }

        return false;
    }

}
