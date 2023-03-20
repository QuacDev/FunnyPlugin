package quac.funnyplugin.Item;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import quac.funnyplugin.utils.Color;
import quac.funnyplugin.utils.Keys;
import quac.funnyplugin.utils.Text;

public class ReforgeStone extends ItemBase {
    public Head look;

    public ReforgeStone(Head look) {
        super();

        this.id = "REFORGE_STONE";
        this.mat = Material.SKULL_ITEM;
        this.displayName = "REFORGE STONE";
        this.baseRarity = Rarity.LEGENDARY;
        this.look = look;
    }

    @Override
    public ItemStack getFunnyItemStack() {
        ItemStack oldStack = super.getFunnyItemStack();
        ItemStack stack = look.createSkull();

        net.minecraft.server.v1_8_R3.ItemStack oldNmsStack = CraftItemStack.asNMSCopy(oldStack);
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound nbtTag = oldNmsStack.getTag();
        NBTTagCompound extraAttributes = nbtTag.getCompound(Keys.EXTRA_ATTRIBUTES);

        NBTTagCompound newTag = nmsStack.getTag();
        newTag.set(Keys.EXTRA_ATTRIBUTES, extraAttributes);
        nmsStack.setTag(newTag);

        ItemStack newStack = CraftItemStack.asBukkitCopy(nmsStack);

        ItemMeta oldMeta = oldStack.getItemMeta();
        ItemMeta meta = newStack.getItemMeta();

        meta.setLore(oldMeta.getLore());
        meta.setDisplayName(oldMeta.getDisplayName());

        newStack.setItemMeta(meta);

        return newStack;
    }
}
