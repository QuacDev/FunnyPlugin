package quac.funnyplugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Main;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class Loop extends CustomCommand {
    public Loop() {
        this.argsAmount = 3;
        this.argsType = ArgsType.Min;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        int amount = Integer.parseInt(args[0]);
        int delay = Integer.parseInt(args[1]);
        StringBuilder builder = new StringBuilder();
        for(int i = 2; i < args.length; i++) {
            builder.append(args[i]);
            if(i != args.length-1) {
                builder.append(" ");
            }
        }

        final String newCommand = builder.toString();
        if(amount > 50) {
            player.sendMessage(Text.translate("&cCannot loop more than 50 times!"));
            return;
        }

        String fixedCommand = newCommand;
        if(fixedCommand.startsWith("/")) {
            fixedCommand = fixedCommand.replaceFirst("/", "");
        }

        if(fixedCommand.startsWith("loop")) {
            player.sendMessage(Text.translate("&cCannot loop this command!"));
            return;
        }

        player.sendMessage(Text.translate("&dLooping &f'" + fixedCommand + "' &e" + amount + "&dx with a delay of &e" + delay + "&d ticks."));

        for (int i = 0; i < amount; i++) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                String tempCommand = newCommand;
                if(tempCommand.startsWith("/")) {
                    tempCommand = tempCommand.replaceFirst("/", "");
                }
                Bukkit.dispatchCommand(player, tempCommand);
            }, (long) i * delay);
        }
    }
}
