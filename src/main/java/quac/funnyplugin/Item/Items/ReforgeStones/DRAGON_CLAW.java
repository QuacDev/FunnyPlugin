package quac.funnyplugin.Item.Items.ReforgeStones;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import quac.funnyplugin.Item.Head;
import quac.funnyplugin.Item.Modifier.Modifier;
import quac.funnyplugin.Item.Modifier.Modifiers.FABLED;
import quac.funnyplugin.Item.Rarity;
import quac.funnyplugin.Item.ReforgeStoneWithModifier;
import quac.funnyplugin.utils.Text;

import java.util.ArrayList;
import java.util.List;

public class DRAGON_CLAW extends ReforgeStoneWithModifier {
    public DRAGON_CLAW() {
        super(Head.DRAGON_CLAW);

        this.id = "DRAGON_CLAW";
        this.mat = Material.SKULL_ITEM;
        this.displayName = "Dragon Claw";
        this.baseRarity = Rarity.RARE;
    }

    @Override
    public List<String> getDescription(NBTTagCompound extraAttributes) {
        List<String> lore = new ArrayList<>();

        lore.add(Text.translate("&7Applies the &dFabled &7reforge when combined with a melee weapon."));
        lore.add(Text.translate(" "));
        lore.add(Text.translate("&7Can be applied to: &6Swords&7."));
        lore.add(Text.translate(" "));

        return lore;
    }

    @Override
    public Modifier getModifier() {
        return new FABLED();
    }
}