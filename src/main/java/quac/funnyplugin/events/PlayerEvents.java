package quac.funnyplugin.events;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import quac.funnyplugin.Custom.GUI.CustomInventory;
import quac.funnyplugin.Custom.GUI.Inventories.KaiBlockMenu;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.ItemRegistry;
import quac.funnyplugin.Main;
import quac.funnyplugin.utils.Keys;
import quac.funnyplugin.utils.Text;

import java.sql.SQLException;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) throws SQLException {
        Player p = event.getPlayer();
        ItemStack i = event.getItem();
        if(i == null) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(i);
        if(nmsItem == null) return;
        if(!nmsItem.hasTag()) return;
        NBTTagCompound tag = nmsItem.getTag();
        assert tag != null;
        if(!tag.hasKey(Keys.EXTRA_ATTRIBUTES)) return;
        NBTTagCompound extraAttributes = tag.getCompound(Keys.EXTRA_ATTRIBUTES);
        String id = extraAttributes.getString(Keys.ID_KEY);
        ItemBase itemBase = ItemRegistry.getFunnyItem(id);
        if(itemBase == null) return;

        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                itemBase.useAbility(event);
                break;
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws SQLException {
        Player p = event.getEntity();
        PlayerBase playerBase = PlayerBase.getPlayerBase(p);
        playerBase.die();
        p.setNoDamageTicks(30);
    }

    @EventHandler
    public void onFoodLoss(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }


    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        int durationTicks = 25;
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 9, true, false));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        System.out.println("Player Joined: " + event.getPlayer().getUniqueId());
        Player p = event.getPlayer();
        Main.initPlayer(p);

        int durationTicks = 25;
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, durationTicks, 9, true, false));

    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) throws SQLException {
        Player p = event.getPlayer();
        PlayerBase base = PlayerBase.getPlayerBase(p);
        base.DestroyPlayerBase();

        event.setQuitMessage(Text.translate("&e" +event.getPlayer().getDisplayName() + " was a bozo"));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setFormat(Text.translate("&e" + event.getPlayer().getDisplayName() + "&f: " + event.getMessage()));

        if(event.getPlayer().isOp()){
            event.setFormat(Text.translate("&f[&cADMIN&f] ") + event.getFormat());
        }
        if(event.getPlayer().getName().equals("Unknownexe")){
            event.setFormat(Text.translate("&f[&6GOD&f] ") + event.getFormat());
        }
        if(event.getPlayer().getName().equals("Chilli_xd")){
            event.setFormat(Text.translate("&f[&bGAY &aLORD&f] ") + event.getFormat());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if(event.getCursor() != null) {
            ItemStack stack = event.getCurrentItem();
            net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
            if(nmsStack != null) {
                if(nmsStack.hasTag()) {
                    NBTTagCompound tag = nmsStack.getTag();
                    if(tag.hasKey(Keys.EXTRA_ATTRIBUTES)) {
                        NBTTagCompound extraAttributes = tag.getCompound(Keys.EXTRA_ATTRIBUTES);
                        if(extraAttributes.hasKey(Keys.ID_KEY)) {
                            String id = extraAttributes.getString(Keys.ID_KEY);
                            if(id.equalsIgnoreCase("SKYBLOCK_MENU")) {
                                event.setCancelled(true);
                                try {
                                    event.getWhoClicked().openInventory(
                                            new KaiBlockMenu(
                                                    PlayerBase.getPlayerBase((Player) event.getWhoClicked()))
                                                    .getInventory()
                                    );
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            }
        }
        if(inventory.getHolder() instanceof CustomInventory) {
            CustomInventory customInventory = (CustomInventory) inventory.getHolder();
            customInventory.onClick(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if(inventory.getHolder() instanceof CustomInventory) {
            CustomInventory customInventory = (CustomInventory) inventory.getHolder();
            customInventory.onClose(event);
        }
    }
}

