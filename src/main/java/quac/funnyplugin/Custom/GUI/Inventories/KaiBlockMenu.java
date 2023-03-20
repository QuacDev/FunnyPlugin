package quac.funnyplugin.Custom.GUI.Inventories;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import quac.funnyplugin.Custom.GUI.CustomInventory;
import quac.funnyplugin.Custom.Stat;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.Item.Head;
import quac.funnyplugin.utils.Text;

import java.util.ArrayList;
import java.util.List;

public class KaiBlockMenu extends CustomInventory {
    public KaiBlockMenu(PlayerBase playerBase) {
        super("kaiblock_menu", "Profile", 54);

        for(int i = 0; i < 54; i++) {
            setItem(i, emptyGlass(DyeColor.GRAY));
        }

        ItemStack playerInfoItem = Head.playerSkull(playerBase.p);
        ItemMeta meta = playerInfoItem.getItemMeta();
        meta.setDisplayName(Text.translate("&aYour KaiBlock Profile"));
        List<String> lore = new ArrayList<>();
        lore.add(Text.translate("&7View your stats!"));
        lore.add("");
        lore.addAll(Stat.getColoredStatsList(playerBase));
        meta.setLore(lore);
        playerInfoItem.setItemMeta(meta);
        setItem(22, playerInfoItem);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public Inventory getInventory() {
        return super.getInventory();
    }
}
