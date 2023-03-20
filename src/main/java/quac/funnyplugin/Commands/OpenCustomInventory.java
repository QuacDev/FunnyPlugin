package quac.funnyplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Custom.GUI.CustomInventory;
import quac.funnyplugin.Custom.GUI.Inventories.ReforgeAnvilInventory;

import java.sql.SQLException;

public class OpenCustomInventory extends CustomCommand {
    public OpenCustomInventory() {
        this.argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);

        CustomInventory customInventory = new ReforgeAnvilInventory();
        player.openInventory(customInventory.getInventory());
    }
}
