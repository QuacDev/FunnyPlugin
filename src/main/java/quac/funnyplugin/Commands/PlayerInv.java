package quac.funnyplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.Objects;

import static quac.funnyplugin.Registry.invalidArgs;

public class PlayerInv extends CustomCommand {
    public PlayerInv() {
        super();
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        // Get Online Players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if(Objects.equals(onlinePlayer.getDisplayName(), args[0])) {
                player.openInventory(onlinePlayer.getInventory());
                player.sendMessage(Text.translate("&dOpening "+"&e"+onlinePlayer.getDisplayName()+"`s &dInventory"));
            }
        }
    }
}
