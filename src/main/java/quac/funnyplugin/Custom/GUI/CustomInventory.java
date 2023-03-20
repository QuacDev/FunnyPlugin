package quac.funnyplugin.Custom.GUI;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import quac.funnyplugin.Main;
import quac.funnyplugin.utils.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomInventory implements InventoryHolder {
    private final Inventory inventory;
    public final String invID;
    public final String displayName;
    public final int invSize;

    public ArrayList<Integer> noClick = new ArrayList<>();

    public CustomInventory(String invID, String displayName, int invSize) {
        this.invSize = invSize;
        this.invID = invID;
        this.displayName = displayName;
        this.inventory = Main.plugin.getServer().createInventory(this, this.invSize, this.displayName);

        init();
    }

    public void init() {
        System.out.println("Initialized " + invID);
    }

    public void onClick(InventoryClickEvent event) {
        if(noClick.contains(event.getRawSlot())) {
            event.setCancelled(true);
        }
        System.out.println("Click on " + invID + " | " + event.getRawSlot());
    }

    public void onClose(InventoryCloseEvent event) {

    }

    public void addUnclickableItem(int index, ItemStack stack) {
        inventory.setItem(index, stack);
        toggleClickable(index);
    }

    public void setItem(int index, ItemStack stack) {
        inventory.setItem(index, stack);
    }

    public ItemStack noNameItem(Material mat) {
        ItemStack item = new ItemStack(mat);
        ItemMeta m = item.getItemMeta();

        m.setDisplayName(Text.translate("&7"));

        item.setItemMeta(m);
        return item;
    }

    public ItemStack noNameItem(ItemStack stack) {
        ItemMeta m = stack.getItemMeta();

        m.setDisplayName(Text.translate("&7"));

        stack.setItemMeta(m);
        return stack;
    }

    public ItemStack emptyGlass(DyeColor color) {
        return noNameItem(coloredItem(Material.STAINED_GLASS_PANE, color));
    }

    public ItemStack coloredItem(Material mat, DyeColor color) {
        MaterialData data = new MaterialData(mat);
        data.setData(color.getData());
        return data.toItemStack(1);
    }

    public boolean toggleClickable(int index) {
        if(noClick.contains(index)) {
            noClick.remove(index);
        } else {
            noClick.add(index);
        }

        return noClick.contains(index);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
