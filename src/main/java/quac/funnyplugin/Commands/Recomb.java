package quac.funnyplugin.Commands;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.ItemRegistry;
import quac.funnyplugin.Item.Reforge;
import quac.funnyplugin.utils.Keys;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class Recomb extends CustomCommand {
    public Recomb() {
        super();
        this.argsAmount = 0;
    }

    @Override
    public void run(CommandSender sender, Command command, String s, String[] args) throws SQLException {
        super.run(sender, command, s, args);
        Player p = (Player) sender;
        PlayerInventory inv = p.getInventory();
        ItemStack stack = inv.getItemInHand();
        if(stack.getType().equals(Material.AIR)) {
            p.sendMessage(Text.translate("&cPlease hold an Item!"));
            return;
        }

        inv.setItemInHand(Reforge.Recomb(stack));
    }
}
