package quac.funnyplugin.Entity;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import quac.funnyplugin.Custom.Stat;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;

import java.util.*;


public class EntityBase {
    static HashMap<Entity, EntityBase> entityBaseHashMap = new HashMap<>();

    public Entity entity;
    public NameTagArmorStand tagArmorStand;
    public UUID uuid;
    public HashMap<StatType, Double> baseStats;
    public HashMap<StatType, Double> currentStats = new HashMap<>();

    //public int tickTask;

    public EntityBase(Entity entity) {
        this.entity = entity;
        this.tagArmorStand = new NameTagArmorStand(this.entity);
        this.uuid = entity.getUniqueId();
        this.baseStats = Stat.baseEntityStats((LivingEntity) entity);

        entityBaseHashMap.put(this.entity, this);
    }

    public static EntityBase getEntityBase(Entity entity) {
        if(entityBaseHashMap.containsKey(entity)) {
            return entityBaseHashMap.get(entity);
        }
        return new EntityBase(entity);
    }

    public void updateStats() {
        currentStats.clear();
        LivingEntity livingEntity = (LivingEntity) entity;

        EntityEquipment eq = livingEntity.getEquipment();
        List<ItemStack> stacksToCheck = new ArrayList<>();

        if(eq.getItemInHand()!=null) {
            stacksToCheck.add(eq.getItemInHand());
        }
        if(eq.getHelmet()!=null) {
            stacksToCheck.add(eq.getHelmet());
        }
        if(eq.getChestplate()!=null) {
            stacksToCheck.add(eq.getChestplate());
        }
        if(eq.getLeggings()!=null) {
            stacksToCheck.add(eq.getLeggings());
        }
        if(eq.getBoots()!=null) {
            stacksToCheck.add(eq.getBoots());
        }

        if(stacksToCheck.isEmpty()) {
            currentStats.putAll(baseStats);
        } else {
            for (ItemStack itemStack : stacksToCheck) {
                if(itemStack!=null) {
                    HashMap<StatType, Double> stats = ItemBase.getStatsOfStack(itemStack);

                    if(stats!=null) {
                        stats.forEach((statType, aDouble) -> {
                            if(currentStats.containsKey(statType)) {
                                currentStats.merge(statType, aDouble, Double::sum);
                            } else {
                                currentStats.put(statType, baseStats.get(statType) + aDouble);
                            }
                        });
                    }
                }
            }
        }

        for (StatType value : StatType.values()) {
            if(!currentStats.containsKey(value)) {
                currentStats.put(value, baseStats.get(value));
            }
        }

        livingEntity.setMaxHealth(currentStats.get(StatType.MAX_HEALTH));
        if(livingEntity.getHealth() > currentStats.get(StatType.MAX_HEALTH)) {
            livingEntity.setHealth(currentStats.get(StatType.MAX_HEALTH));
        }
    }

    public void DeleteEntityBase() {
        //entity.getServer().getScheduler().cancelTask(tickTask);
        tagArmorStand.DestroyStand();
        entityBaseHashMap.remove(this.entity, this);
    }
}
