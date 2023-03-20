package quac.funnyplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class TravelWorld extends CustomCommand {
    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        int index = Integer.parseInt(args[0]);
        org.bukkit.World world = Bukkit.getWorlds().get(index);
        if(world == null) {
            player.sendMessage(Text.translate("&cThere is no matching world!"));
            return;
        }

        Location spawnLoc = world.getSpawnLocation();
        if(world.getEnvironment() == org.bukkit.World.Environment.THE_END) {
            spawnLoc.setY(100);
        }

        player.teleport(spawnLoc);
        player.sendMessage(Text.translate("&dSent you to &f" + world.getName() + "&e!"));
    }
}
