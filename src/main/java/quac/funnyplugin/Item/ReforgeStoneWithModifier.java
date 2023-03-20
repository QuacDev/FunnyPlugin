package quac.funnyplugin.Item;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import quac.funnyplugin.Item.Modifier.Modifier;
import quac.funnyplugin.Item.Modifier.Modifiers.FABLED;
import quac.funnyplugin.utils.Keys;

public class ReforgeStoneWithModifier extends ReforgeStone {
    public Head look;
    Modifier modifier;

    public ReforgeStoneWithModifier(Head look) {
        super(look);

        this.id = "REFORGE_STONE_MODIFIER";
        this.mat = Material.SKULL_ITEM;
        this.displayName = "REFORGE STONE MODIFIER";
        this.baseRarity = Rarity.LEGENDARY;
        this.look = look;
        this.modifier = new FABLED();
    }

    public Modifier getModifier() {
        return modifier;
    }
}
