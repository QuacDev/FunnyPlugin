package quac.funnyplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class SpawnMob extends CustomCommand {
    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        String mobName = args[0];
        for (EntityType value : EntityType.values()) {
            if(value.name().equalsIgnoreCase(mobName)) {
                player.getLocation().getWorld().spawnEntity(player.getLocation(), value);
                player.sendMessage(Text.translate("&dSpawned &e1 &f" + value.name() + "&d."));
                return;
            }
        }
    }
}
