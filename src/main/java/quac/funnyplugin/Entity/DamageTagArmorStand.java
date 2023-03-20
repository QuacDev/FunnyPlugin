package quac.funnyplugin.Entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import quac.funnyplugin.Main;
import quac.funnyplugin.utils.Color;
import quac.funnyplugin.utils.Text;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DamageTagArmorStand {
    public Location loc;
    public int aliveTicks;
    public boolean isCrit;
    public double damage;
    public ArmorStand stand;
    public Entity entity;
    public double centerOfEntity;
    public DamageTagArmorStand(Location loc, int aliveTicks, boolean isCrit, double damage, Entity entity) {
        this.loc = loc;
        this.aliveTicks = aliveTicks;
        this.isCrit = isCrit;
        this.damage = damage;
        this.entity = entity;
        this.centerOfEntity = 0.5;
    }
    public void create() {
        loc = this.loc.add(0, centerOfEntity, 0);

        Random r = new Random();
        double maxOffset = 100; // (In blocks * 100)
        double randomXOffset = ThreadLocalRandom.current().nextDouble(-maxOffset, maxOffset);
        double randomYOffset = ThreadLocalRandom.current().nextDouble(-(maxOffset/4), maxOffset);
        double randomZOffset = ThreadLocalRandom.current().nextDouble(-maxOffset, maxOffset);

        loc = loc.add(randomXOffset/100, randomYOffset/100, randomZOffset/100);

        stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);

        stand.setVisible(false);
        stand.setMaximumNoDamageTicks(Integer.MAX_VALUE / 2);
        stand.setNoDamageTicks(Integer.MAX_VALUE / 2);
        stand.setGravity(false);
        stand.setCustomNameVisible(true);
        stand.setCustomName(Text.translate(Color.damageText(damage, isCrit)));
        stand.setMarker(true);


        stand.teleport(loc);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, stand::remove, 30);
    }
}
