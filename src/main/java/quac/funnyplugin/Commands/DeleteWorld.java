package quac.funnyplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Main;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.List;

public class DeleteWorld extends CustomCommand {
    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        List<World> worlds = Bukkit.getWorlds();

        int worldIndex = Integer.parseInt(args[0]);

        if(worldIndex <= 2) {
            player.sendMessage(Text.translate("&cCannot delete default worlds!"));
            return;
        }

        if(worldIndex > worlds.size()) {
            player.sendMessage(Text.translate("&cThere is no matching world!"));
            return;
        }

        World world = worlds.get(worldIndex);
        for (Player worldPlayer : world.getPlayers()) {
            worldPlayer.teleport(worlds.get(0).getSpawnLocation());
        }

        Bukkit.unloadWorld(world, true);

        Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
            world.getWorldFolder().delete();
        }, 100);
    }
}
