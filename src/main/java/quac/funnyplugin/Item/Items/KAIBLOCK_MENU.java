package quac.funnyplugin.Item.Items;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import quac.funnyplugin.Custom.GUI.Inventories.KaiBlockMenu;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Rarity;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KAIBLOCK_MENU extends ItemBase {
    public KAIBLOCK_MENU() {
        super();

        this.baseRarity = Rarity.LEGENDARY;
        this.mat = Material.NETHER_STAR;
        this.displayName = Text.translate("&aKaiBlock Menu &7(Click)");
        this.autoColorDisplay = false;
        this.id = "SKYBLOCK_MENU";

        this.hasAbility = true;
    }

    @Override
    public void useAbility(PlayerInteractEvent event) throws SQLException {
        PlayerBase playerBase = PlayerBase.getPlayerBase(event.getPlayer());
        System.out.println("aa " + playerBase);
        event.getPlayer().openInventory(new KaiBlockMenu(playerBase).getInventory());
    }

    @Override
    public List<String> getLore(NBTTagCompound extraAttributes) {
        List<String> lore = new ArrayList<>();

        lore.add(Text.translate("&7View all of your KaiBlock progress, for now stats only!"));
        lore.add(Text.translate(""));
        lore.add(Text.translate("&eClick to open!"));

        return lore;
    }
}
