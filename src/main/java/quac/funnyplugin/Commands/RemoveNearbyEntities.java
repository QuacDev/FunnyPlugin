package quac.funnyplugin.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RemoveNearbyEntities extends CustomCommand {
    public RemoveNearbyEntities() {
        this.argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        Location loc = player.getLocation();

        List<EntityType> unKillableEntities = new ArrayList<>();
        unKillableEntities.add(EntityType.PLAYER);
        unKillableEntities.add(EntityType.ARMOR_STAND);

        Collection<Entity> nearbyEntities = loc.getWorld().getNearbyEntities(loc, 10, 10, 10);

        int count = 0;

        for (Entity nearbyEntity : nearbyEntities) {
            if(!unKillableEntities.contains(nearbyEntity.getType())) {
                nearbyEntity.remove();
                count++;
            }
        }

        player.sendMessage(Text.translate("&dRemoved &f" + count + "&d entities"));
    }
}
