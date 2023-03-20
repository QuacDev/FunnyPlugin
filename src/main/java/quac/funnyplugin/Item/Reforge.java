package quac.funnyplugin.Item;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import quac.funnyplugin.Item.Modifier.Modifier;
import quac.funnyplugin.utils.Keys;
import quac.funnyplugin.utils.Text;

public class Reforge {
    public static ItemStack ReforgeModifier(ItemStack stack, Modifier modifier) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(stack);

        if(!nmsItem.hasTag()) {
            return stack;
        }
        NBTTagCompound tag = nmsItem.getTag();

        if(tag==null) {
            return stack;
        }

        NBTTagCompound attributes = tag.getCompound(Keys.EXTRA_ATTRIBUTES);
        String id = attributes.getString(Keys.ID_KEY);
        attributes.setString(Keys.MODIFIER, modifier.getId());

        return getStackWithAttributes(nmsItem, tag, attributes, id);
    }

    public static ItemStack Recomb(ItemStack stack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(stack);

        if(!nmsItem.hasTag()) {
            return stack;
        }

        NBTTagCompound tag = nmsItem.getTag();
        assert tag != null;
        NBTTagCompound attributes = tag.getCompound(Keys.EXTRA_ATTRIBUTES);
        String id = attributes.getString(Keys.ID_KEY);

        //p.sendMessage("Item has " + attributes.getInt(Keys.RARITY_UPGRADE) + " upgrades");
        attributes.setInt(Keys.RARITY_UPGRADE, attributes.getInt(Keys.RARITY_UPGRADE)+1);

        return getStackWithAttributes(nmsItem, tag, attributes, id);
    }

    private static ItemStack getStackWithAttributes(net.minecraft.server.v1_8_R3.ItemStack nmsItem, NBTTagCompound tag, NBTTagCompound attributes, String id) {
        tag.set(Keys.EXTRA_ATTRIBUTES, attributes);

        nmsItem.setTag(tag);

        ItemStack newStack = CraftItemStack.asBukkitCopy(nmsItem);
        ItemMeta m = newStack.getItemMeta();
        assert m != null;
        m.setLore(ItemRegistry.getFunnyItem(id).getLore(attributes));
        m.setDisplayName(ItemBase.getDisplayName(attributes));
        newStack.setItemMeta(m);
        return newStack;
    }
}
