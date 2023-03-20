package quac.funnyplugin.Commands;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Main;
import quac.funnyplugin.Registry;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class Biome extends CustomCommand {
    int[] around = {-2, -1, 0, 1, 2};

    public Biome() {
        super();
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        org.bukkit.block.Biome biome = org.bukkit.block.Biome.valueOf(args[0].toUpperCase());
        Block base = player.getLocation().getBlock();

        for(int x : around) {
            for(int z : around) {
                base.getWorld().setBiome(base.getX() + x, base.getZ() + z, biome);
            }
        }

        Registry.updateChunkForPlayer(player, base.getChunk());

        player.sendMessage(Text.translate("&dSet the biome around you to &f" + biome.name() + "&d!"));
    }
}
