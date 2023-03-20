package quac.funnyplugin.Commands;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Entity.PlayerBase;

import java.sql.SQLException;

public class Vanish extends CustomCommand {
    public Vanish() {
        this.argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        PlayerBase playerBase = PlayerBase.getPlayerBase(player);
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();

        if(!playerBase.vanished) {
            playerBase.vanished = true;

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(!onlinePlayer.isOp()) {
                    onlinePlayer.hidePlayer(player);
                }
            }
        } else {
            playerBase.vanished = false;

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(player);
            }
        }
    }
}
