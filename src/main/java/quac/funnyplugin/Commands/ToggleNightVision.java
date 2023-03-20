package quac.funnyplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class ToggleNightVision extends CustomCommand {
    public ToggleNightVision() {
        this.argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(Text.translate("&dRemoving &fNight Vision&d!"));
        }
        else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE/2, 9, true, false));
            player.sendMessage(Text.translate("&dAdding &fNight Vision&d!"));
        }
    }
}
