package quac.funnyplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Item.ItemRegistry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiveCustomItem extends CustomCommand implements TabCompleter {
    public GiveCustomItem() {
        super();
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        Player p = (Player) sender;
        PlayerInventory inv = p.getInventory();

        inv.addItem(ItemRegistry.getFunnyItem(args[0].toUpperCase()).getFunnyItemStack());
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1) {
            List<String> options = new ArrayList<>();
            ItemRegistry.registeredItems.forEach((id, funnyItem) -> {
                options.add(id);
            });

            return options;
        } else {
            return null;
        }
    }
}
