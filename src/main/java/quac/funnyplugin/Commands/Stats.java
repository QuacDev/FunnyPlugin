package quac.funnyplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Custom.Stat;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Stats extends CustomCommand {
    public Stats() {
        argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        Player p = (Player) sender;
        PlayerBase base = PlayerBase.getPlayerBase(p);

        List<String> lines = new ArrayList<>();
        lines.add(Text.translate("&dYour Stats {&f" + p.getDisplayName() + "&d} are:"));
        lines.addAll(Stat.getColoredStatsList(base));

        for (String line : lines) {
            p.sendMessage(line);
        }
    }
}
