package quac.funnyplugin.Custom.GUI.Inventories;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.MaterialData;
import quac.funnyplugin.Custom.GUI.CustomInventory;
import quac.funnyplugin.Item.*;
import quac.funnyplugin.Item.Items.ReforgeStones.RECOMBOBULATOR;
import quac.funnyplugin.Item.Modifier.Modifier;
import quac.funnyplugin.Main;
import quac.funnyplugin.utils.Keys;
import quac.funnyplugin.utils.Text;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;


public class ReforgeAnvilInventory extends CustomInventory {
    public ReforgeAnvilInventory() {
        super("REFORGE_ANVIL", "Reforge Anvil", 54);
    }

    boolean canReforge = false;
    boolean hasReforged = false;

    @Override
    public void init() {
        for(int i = 0; i < this.invSize-9; i++) {
            if(i != 29 && i != 33)
                addUnclickableItem(i, emptyGlass(DyeColor.GRAY));
        }

        for(int i = this.invSize-1; i > this.invSize-10; i--) {
            addUnclickableItem(i, emptyGlass(DyeColor.RED));
        }

        ItemStack upgradeGlass = coloredItem(Material.STAINED_GLASS_PANE, DyeColor.RED);
        ItemMeta upgradeGlassMeta = upgradeGlass.getItemMeta();
        List<String> upgradeGlassLore = new ArrayList<>();
        upgradeGlassLore.add(Text.translate("&7A weapon, armor piece, or other item you want to reforge should be placed in the slow below."));
        upgradeGlassMeta.setLore(upgradeGlassLore);
        upgradeGlassMeta.setDisplayName(Text.translate("&6Item to Upgrade"));
        upgradeGlass.setItemMeta(upgradeGlassMeta);

        ItemStack sacrificeGlass = coloredItem(Material.STAINED_GLASS_PANE, DyeColor.RED);
        ItemMeta sacrificeGlassMeta = upgradeGlass.getItemMeta();
        List<String> sacrificeGlassLore = new ArrayList<>();
        sacrificeGlassLore.add(Text.translate("&7The item you are sacrificing in order to upgrade the item on the left should be placed in the slot on this side. This can be a &aReforge Stone&7, which applies a reforge to the target item!"));
        sacrificeGlassMeta.setLore(sacrificeGlassLore);
        sacrificeGlass.setItemMeta(sacrificeGlassMeta);

        setItem(11, upgradeGlass);
        setItem(12, upgradeGlass);
        setItem(20, upgradeGlass);

        setItem(14, sacrificeGlass);
        setItem(15, sacrificeGlass);
        setItem(24, sacrificeGlass);

        ItemStack reforgeAnvil = new ItemStack(Material.ANVIL);
        ItemMeta reforgeAnvilMeta = reforgeAnvil.getItemMeta();
        reforgeAnvilMeta.setDisplayName(Text.translate("&aCombine Items"));
        List<String> reforgeAnvilLore = new ArrayList<>();
        reforgeAnvilLore.add(Text.translate("&7Combine the items in the slots to the left and right below."));
        reforgeAnvilMeta.setLore(reforgeAnvilLore);
        reforgeAnvil.setItemMeta(reforgeAnvilMeta);
        this.getInventory().setItem(22, reforgeAnvil);

        this.getInventory().setItem(13, barrier("&cReforge Item"));
    }

    public ItemStack barrier(String name) {
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta barrierMeta = barrier.getItemMeta();
        List<String> barrierLore = new ArrayList<>();
        barrierLore.add(Text.translate("&7Place a weapon, armor piece, or other item in the left slot and a &aReforge Stone&7 in the right slot to reforge!"));
        barrierMeta.setLore(barrierLore);
        barrierMeta.setDisplayName(Text.translate(name));
        barrier.setItemMeta(barrierMeta);

        return barrier;
    }

    public boolean hasItemOnLeft() {
        boolean a = true;

        if(getInventory().getItem(29) == null) {
            a = false;
        } else if(getInventory().getItem(29).getType() == null) {
            a = false;
        } else if(getInventory().getItem(29).getType().equals(Material.AIR)) {
            a = false;
        }

        return a;
    }

    public boolean hasItemOnRight() {
        boolean a = true;

        if(getInventory().getItem(33) == null) {
            a = false;
        } else if(getInventory().getItem(33).getType() == null) {
            a = false;
        } else if(getInventory().getItem(33).getType().equals(Material.AIR)) {
            a = false;
        }

        return a;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.getRawSlot() != 22) {
            if (!hasReforged) {
                Bukkit.getScheduler().runTaskLater(Main.plugin, this::UpdateAnvil, 1);
            }
        }

        if(noClick.contains(event.getRawSlot()) && event.getRawSlot() != 22) {
            event.setCancelled(true);
        } else if(canReforge && event.getRawSlot() == 22) {
            event.setCancelled(true);
            Reforge();
        } else if(hasReforged && event.getRawSlot() == 13) {
            event.setCancelled(false);
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                hasReforged = false;
                addUnclickableItem(13, barrier("Reforge Item"));
            }, 1);
        }

    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        super.onClose(event);

        if(hasItemOnLeft()) {
            event.getPlayer().getInventory().addItem(getInventory().getItem(29));
        }

        if(hasItemOnRight()) {
            event.getPlayer().getInventory().addItem(getInventory().getItem(33));
        }
    }

    public void Reforge() {
        if(noClick.contains(13)) {
            noClick.remove((Object) 13);
        }
        getInventory().setItem(29, null);
        getInventory().setItem(33, null);
        canReforge = false;
        hasReforged = true;
    }

    public void UpdateAnvil() {
        System.out.println("ItemLeft: " + hasItemOnLeft() + " | ItemRight: " + hasItemOnRight());

        if (hasItemOnLeft() && hasItemOnRight()) {
            ItemStack itemOnLeft = getInventory().getItem(29);
            ItemStack itemOnRight = getInventory().getItem(33);
            net.minecraft.server.v1_8_R3.ItemStack nmsLeft = CraftItemStack.asNMSCopy(itemOnLeft);
            net.minecraft.server.v1_8_R3.ItemStack nmsRight = CraftItemStack.asNMSCopy(itemOnRight);
            if (nmsLeft.hasTag() && nmsRight.hasTag()) {
                NBTTagCompound leftTag = nmsLeft.getTag();
                NBTTagCompound rightTag = nmsRight.getTag();
                if (leftTag.hasKey(Keys.EXTRA_ATTRIBUTES) && rightTag.hasKey(Keys.EXTRA_ATTRIBUTES)) {
                    NBTTagCompound leftAttributes = leftTag.getCompound(Keys.EXTRA_ATTRIBUTES);
                    NBTTagCompound rightAttributes = rightTag.getCompound(Keys.EXTRA_ATTRIBUTES);

                    ItemBase leftItemBase = ItemRegistry.getFunnyItem(leftAttributes.getString(Keys.ID_KEY));
                    ItemBase rightItemBase = ItemRegistry.getFunnyItem(rightAttributes.getString(Keys.ID_KEY));
                    if (rightItemBase instanceof ReforgeStone) {
                        ReforgeStone reforgeStone = (ReforgeStone) rightItemBase;
                        if (reforgeStone instanceof RECOMBOBULATOR) {
                            if(leftAttributes.getInt(Keys.RARITY_UPGRADE) == 0) {
                                canReforge = true;

                                ItemStack result = Reforge.Recomb(itemOnLeft);

                                getInventory().setItem(13, result);
                            } else {
                                canReforge = false;
                                //Change barrier name
                            }
                        } else if(reforgeStone instanceof ReforgeStoneWithModifier) {
                            ReforgeStoneWithModifier reforgeStoneWithModifier = (ReforgeStoneWithModifier) reforgeStone;
                            //System.out.println("right item = reforgestonewithmodifier: " + reforgeStoneWithModifier.id);

                            canReforge = true;

                            ItemStack result = Reforge.ReforgeModifier(itemOnLeft, reforgeStoneWithModifier.getModifier());

                            getInventory().setItem(13, result);
                        } else {
                            canReforge = false;
                            //Change barrier name
                        }
                    } else {
                        canReforge = false;
                        //Change barrier name
                    }
                }
            } else {
                canReforge = false;
                //Change barrier name
            }
        } else {
            canReforge = false;
        }

        if(!canReforge) {
            this.getInventory().setItem(13, barrier("&cReforge Item"));
        }
    }
}
