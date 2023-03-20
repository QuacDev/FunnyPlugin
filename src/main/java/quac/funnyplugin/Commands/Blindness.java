package quac.funnyplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class Blindness extends CustomCommand {
    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        int durationTicks = Integer.parseInt(args[0]);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 9, true, false));
        player.sendMessage(Text.translate("&dAdding &fBlindness&d!"));
    }
}
