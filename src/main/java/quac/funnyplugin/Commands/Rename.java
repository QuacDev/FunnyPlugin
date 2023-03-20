package quac.funnyplugin.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import quac.funnyplugin.Custom.ArgsType;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class Rename extends CustomCommand {
    public Rename() {
        super();
        this.argsType = ArgsType.Min;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        ItemStack stack = player.getItemInHand();
        if(stack.getType().equals(Material.AIR)) {
            player.sendMessage(Text.translate("&cYou need to hold an item!"));
            return;
        }
        ItemMeta meta = stack.getItemMeta();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]);
            if(i != args.length-1) {
                builder.append(" ");
            }
        }

        meta.setDisplayName(Text.translate("&r" + builder.toString()));

        stack.setItemMeta(meta);
        player.sendMessage(Text.translate("&dSet &f" + stack.getType().name() + "&d's name to &f'" + builder.toString() + "&f'&d."));
    }
}
