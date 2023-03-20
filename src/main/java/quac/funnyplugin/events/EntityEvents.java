package quac.funnyplugin.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import quac.funnyplugin.Entity.EntityBase;
import quac.funnyplugin.Entity.PlayerBase;

import java.sql.SQLException;

public class EntityEvents implements Listener {
    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if(!entity.getType().equals(EntityType.ARMOR_STAND) && entity.getType().isAlive()) {
            if(entity.getType().equals(EntityType.PLAYER)) return;
            EntityBase base = EntityBase.getEntityBase(entity);
            base.tagArmorStand.updateName();
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if(!entity.getType().equals(EntityType.ARMOR_STAND) && entity.getType().isAlive()) {
            if(entity.getType().equals(EntityType.PLAYER)) return;
            EntityBase base = EntityBase.getEntityBase(entity);
            base.DeleteEntityBase();
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) throws SQLException {
        Entity entity = event.getEntity();
        if(!entity.getType().equals(EntityType.ARMOR_STAND) && entity.getType().isAlive()) {
            if(entity.getType().equals(EntityType.PLAYER)) {
                Player p = (Player) event.getEntity();
                PlayerBase playerBase = PlayerBase.getPlayerBase(p);
                playerBase.takeDamageEvent(event);
            } else {
                EntityBase base = EntityBase.getEntityBase(entity);
                base.tagArmorStand.updateName();
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) throws SQLException {
        Entity entity = event.getEntity();
        if(!entity.getType().equals(EntityType.ARMOR_STAND) && entity.getType().isAlive()) {
            if(event.getDamager().getType().equals(EntityType.PLAYER)) {
                Player player = (Player) event.getDamager();
                PlayerBase playerBase = PlayerBase.getPlayerBase(player);
                playerBase.damageEntity(event);
            }
        }
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
       event.setCancelled(true);
    }
}
