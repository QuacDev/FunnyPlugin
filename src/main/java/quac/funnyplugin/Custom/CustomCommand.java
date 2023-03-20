package quac.funnyplugin.Custom;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import quac.funnyplugin.Registry;

import java.sql.SQLException;

public class CustomCommand implements CommandExecutor {
    public int argsAmount;
    public ArgsType argsType;
    public boolean playerOnly;
    public Player player;

    public CustomCommand() {
        this.argsAmount = 1;
        this.argsType = ArgsType.Exact;
        this.playerOnly = true;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        boolean a = true;

        if(playerOnly) {
            if(!(sender instanceof Player)) {
                a = false;
            }
        }

        if(argsType.equals(ArgsType.Exact)) {
            if(args.length != argsAmount) {
                a = false;
            }
        } else if(argsType.equals(ArgsType.Min)) {
            if(args.length < argsAmount) {
                a = false;
            }
        } else if(argsType.equals(ArgsType.Max)) {
            if(args.length > argsAmount) {
                a = false;
            }
        }

        if(a) {
            try {
                run(sender, command, s, args);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            Registry.invalidArgs((Player) sender, s);
        }

        return true;
    }

    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        this.player = (Player) sender;
    }
}
