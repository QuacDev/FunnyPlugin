package quac.funnyplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Worlds extends CustomCommand {
    public Worlds() {
        argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        List<String> lines = new ArrayList<>();
        lines.add("&d-------------------------");
        lines.add("&dAll available worlds: ");

        List<World> worlds = Bukkit.getWorlds();

        for (int i = 0; i < worlds.size(); i++) {
            lines.add("&d(&e" + i + "&d) &f" + worlds.get(i).getName());
        }

        lines.add("&d-------------------------");
        lines.add("");

        for (String line : lines) {
            player.sendMessage(Text.translate(line));
        }
    }
}
