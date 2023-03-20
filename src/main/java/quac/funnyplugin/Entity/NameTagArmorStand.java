package quac.funnyplugin.Entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import quac.funnyplugin.Main;
import quac.funnyplugin.utils.Text;

import java.text.DecimalFormat;

public class NameTagArmorStand {
    public ArmorStand stand;
    public Entity entityParent;

    public int tickTask;

    public NameTagArmorStand(Entity parent) {
        this.stand = (ArmorStand) parent.getWorld().spawnEntity(parent.getLocation(), EntityType.ARMOR_STAND);
        this.entityParent = parent;

        stand.setVisible(false);
        stand.setMarker(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setSmall(true);
        stand.setCustomNameVisible(true);
        stand.setMaximumNoDamageTicks(Integer.MAX_VALUE/2);
        stand.setNoDamageTicks(Integer.MAX_VALUE/2);

        updateName();

        tickTask = Bukkit.getScheduler().runTaskTimer(Main.plugin, this::updatePos, 1, 0).getTaskId();
    }

    public void updatePos() {
        Location newLoc = entityParent.getLocation().clone();
        newLoc.add(0, 1, 0);
        stand.teleport(newLoc);
    }

    public void updateName() {
        LivingEntity livingEntity = (LivingEntity) entityParent;
        DecimalFormat format = new DecimalFormat("0.#");
        double maxHealth = ((LivingEntity) entityParent).getMaxHealth();

        stand.setCustomName(Text.translate(
                "&c" + entityParent.getName() +
                        " &a" + format.format(livingEntity.getHealth()) + "&f/&a" + format.format(maxHealth) + "&c❤"
        ));
    }

    public void DestroyStand() {
        stand.getServer().getScheduler().cancelTask(tickTask);
        stand.remove();
    }
}
