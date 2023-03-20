package quac.funnyplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class TravelEnv extends CustomCommand {
    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        int index = Integer.parseInt(args[0]);
        World.Environment environment = World.Environment.values()[index];
        World world = Bukkit.getWorlds().stream()
                .filter(w -> w.getEnvironment() == environment)
                .findFirst().orElse(null);
        if(world == null) {
            player.sendMessage(Text.translate("&cThere is no matching world!"));
            return;
        }

        Location spawnLoc = world.getSpawnLocation();
        if(environment == World.Environment.THE_END) {
            spawnLoc.setY(100);
        }

        player.teleport(spawnLoc);
        player.sendMessage(Text.translate("&dSent you to &f" + environment.name() + "&e!"));
    }
}
