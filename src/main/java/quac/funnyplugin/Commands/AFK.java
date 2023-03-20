package quac.funnyplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;


public class AFK extends CustomCommand {
    public AFK() {
        super();
        this.argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(Text.translate("&e" + player.getDisplayName()+ " &dwent AFK"));
        }
    }
}
