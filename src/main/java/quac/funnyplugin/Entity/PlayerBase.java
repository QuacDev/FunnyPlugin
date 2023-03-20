package quac.funnyplugin.Entity;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import quac.funnyplugin.Ability.AbilityBase;
import quac.funnyplugin.Ability.AbilityBaseWithCost;
import quac.funnyplugin.Custom.Stat;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Items.KAIBLOCK_MENU;
import quac.funnyplugin.Main;
import quac.funnyplugin.Scoreboard.CustomScoreboard;
import quac.funnyplugin.utils.Text;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlayerBase {
    public static HashMap<UUID, PlayerBase> playerBaseHashMap = new HashMap<>();

    public int tickTask;
    public int middleTextTask;
    public int tick;

    public HashMap<StatType, Double> baseStats = Stat.basePlayerStats();
    public HashMap<StatType, Double> currentStats = new HashMap<>();

    public HashMap<String, Integer> cooldowns = new HashMap<>();

    public double mana;
    public BigDecimal money;

    public boolean vanished;

    public Player p;
    public UUID uuid;

    private String middleText = "     ";

    public PlayerBase(Player p) {
        this.p = p;
        this.uuid=p.getUniqueId();
        playerBaseHashMap.put(this.uuid, this);

        tickTask = p.getServer().getScheduler().runTaskTimer(Main.plugin, () -> {
            try {
                tick();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, 1, 1).getTaskId();

        p.setMaxHealth(baseStats.get(StatType.MAX_HEALTH));
        p.setHealthScaled(true);
        p.setHealthScale(40d);
        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(30);

        updateStats();

        regenMana(Integer.MAX_VALUE);

        p.getInventory().setItem(8, new KAIBLOCK_MENU().getFunnyItemStack());

        System.out.println("Created Player base for " + uuid);
    }

    public void tick() throws SQLException {
        updateStats();

        double manaRegenPercent = 2;
        double manaRegenDecimal = manaRegenPercent/100;
        double maxMana = currentStats.get(StatType.INTELLIGENCE);
        int regenAmount = (int) (manaRegenDecimal*maxMana);

        tick++;
        if(tick > 20) {
            tick = 0;
        }
        if(tick == 20) {
            regenMana(regenAmount);
        }

        if(mana > maxMana) {
            mana = maxMana;
        }

        if(!cooldowns.isEmpty()) {
            for (String key : cooldowns.keySet()) {
                cooldowns.replace(key, cooldowns.get(key) - 1);
                if (cooldowns.get(key) <= 0) {
                    cooldowns.remove(key);
                }
            }
        }

        EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();

        String message =
                "&c" + (int) Math.floor(p.getHealth()) + "/" + (int) Math.floor(currentStats.get(StatType.MAX_HEALTH)) + "❤" +
                middleText  +
                "&b" + (int) Math.floor(mana) + "/" + (int) Math.floor(currentStats.get(StatType.INTELLIGENCE)) + "✎";

        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutChat(new ChatComponentText(Text.translate(message)), (byte)2));

        CustomScoreboard customScoreboard = new CustomScoreboard(p, "         &e&lKAIBLOCK         ");

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        customScoreboard.addLine("&7" + dateFormat.format(LocalDateTime.now()));
        customScoreboard.addLine("");
        customScoreboard.addLine("&fPurse: &6" + String.format("%,.1f", this.money));
        customScoreboard.addLine(" ");
        customScoreboard.addLine("&epoteta.nl");
        customScoreboard.finish();
        customScoreboard.display();



        if(vanished) {
            updateMiddleText("&e&l*VANISHED*", 2);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                EntityPlayer onlineNMSPlayer = ((CraftPlayer) onlinePlayer).getHandle();
                PlayerConnection playerConnection = onlineNMSPlayer.playerConnection;
            }
        }
    }

    public void updateStats() {
        currentStats.clear();

        EntityEquipment eq = p.getEquipment();
        List<ItemStack> stacksToCheck = new ArrayList<>();

        List<String> noHandStats = new ArrayList<>();
        noHandStats.add("HELMET");
        noHandStats.add("CHESTPLATE");
        noHandStats.add("LEGGINGS");
        noHandStats.add("BOOTS");
        noHandStats.add("BOOTS");

        if(eq.getItemInHand()!=null) {
            ItemStack itemHand = eq.getItemInHand();

            boolean addStats = true;
            for (String noHandStat : noHandStats) {
                String itemType = itemHand.getType().name();
                if(itemType.contains(noHandStat)) {
                    addStats=false;
                }
            }
            if(addStats) {
                stacksToCheck.add(eq.getItemInHand());
            }
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
        }

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

        for (StatType value : StatType.values()) {
            if(!currentStats.containsKey(value)) {
                currentStats.put(value, baseStats.get(value));
            }
        }

        if(currentStats.get(StatType.CRITICAL_CHANCE) > 100d) {
            currentStats.replace(StatType.CRITICAL_CHANCE, 100d);
        }

        p.setMaxHealth(currentStats.get(StatType.MAX_HEALTH));
        if(p.getHealth() > currentStats.get(StatType.MAX_HEALTH)) {
            p.setHealth(currentStats.get(StatType.MAX_HEALTH));
        }
    }

    public void updateMiddleText(String s, int ticks) {
        p.getServer().getScheduler().cancelTask(middleTextTask);
        middleText = "     " + s + "     ";
        middleTextTask = p.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                middleText = "     ";
            }
        }, ticks);
    }

    public void DestroyPlayerBase() {
        p.getServer().getScheduler().cancelTask(middleTextTask);
        p.getServer().getScheduler().cancelTask(tickTask);
        playerBaseHashMap.remove(this.uuid);
    }

    public void useAbility(AbilityBase base) {
        if(base.cooldownTicks > 0) {
            //System.out.println("Added cooldown for '" + base.name + "' for (" + base.cooldownTicks + ") ticks");
            cooldowns.put(base.name, base.cooldownTicks);
        }
    }

    public void useMana(AbilityBaseWithCost base) {
        mana -= base.manaCost;
        updateMiddleText("&b-" + base.manaCost + " Mana (&6" + base.name + "&b)", 30);
    }

    public void takeDamageByEntity(EntityBase entityBase) {
        //HashMap<StatType, Double> entityStats = entityBase.calcStats();
    }

    public void takeDamageEvent(EntityDamageEvent event) {
        if(!event.getEntityType().equals(EntityType.PLAYER)) return;
        double dmg = event.getDamage();
        if(p.getHealth() - dmg <= 0) {
            event.setCancelled(true);
            die();
        }
    }

    public void regenMana(int amount) {
        double maxMana = currentStats.get(StatType.INTELLIGENCE);
        if(mana+amount > maxMana) {
            mana = maxMana;
        } else {
            mana+=amount;
        }
    }

    public void damageEntity(EntityDamageByEntityEvent event) throws SQLException {
        event.setCancelled(true);

        Entity entity = event.getEntity();

        HashMap<StatType, Double> enemyStats;

        if(entity.getType().equals(EntityType.PLAYER)) {
            Player enemy = (Player) entity;
            PlayerBase enemyPlayer = PlayerBase.getPlayerBase(enemy);
            enemyPlayer.updateStats();
            enemyStats = enemyPlayer.currentStats;
        } else {
            EntityBase entityBase = EntityBase.getEntityBase(entity);
            entityBase.updateStats();
            enemyStats = entityBase.currentStats;
        }

        HashMap<StatType, Double> playerStats = currentStats;

        double entityDefense = enemyStats.get(StatType.DEFENSE);
        double damageReduction = entityDefense / (entityDefense+100);
        double damageReductionMultiplier = (100-damageReduction)/100;

        double playerBaseMeleeDamage = playerStats.get(StatType.BASE_MELEE_DAMAGE);
        double playerStrength = playerStats.get(StatType.STRENGTH);
        double playerCritDMG = playerStats.get(StatType.CRITICAL_DAMAGE);
        double playerCritChance = playerStats.get(StatType.CRITICAL_CHANCE);

        double initialDamage = (5 + playerBaseMeleeDamage) * (1 + (playerStrength/100));
        double combatLVLBonus = 25*0.04; // Implement Combat LvL later
        double weaponBonus = 0; // Armor that has +X% more dmg is weaponDMG / also just weapon ability dmg bonus like reaper falc
        double armorBonus = 0; // Stuff like tara armor
        double damageMultiplier = 1 + combatLVLBonus + weaponBonus;

        boolean isCrit;
        if(playerCritChance >= 100) {
            isCrit = true;
        } else {
            Random r = new Random();
            double randomValue = 1 + (100 - 1) * r.nextDouble();
            isCrit = randomValue <= playerCritChance;
        }

        System.out.println("InitialDMG: " + initialDamage + " | DMGMultiplier: " + damageMultiplier + " | ArmorBonus" + (1+armorBonus) + " | IsCrit: " + isCrit + " | CritDMG: " + (isCrit?(1+(playerCritDMG/100)) : 1) + " | DMGReduct: " + damageReductionMultiplier);
        double finalDamage = initialDamage * damageMultiplier * (1 + armorBonus) * (isCrit?(1+(playerCritDMG/100)) : 1) * damageReductionMultiplier;
        System.out.println("FinalDMG: " + finalDamage);

        event.setDamage(finalDamage);
        event.setCancelled(false);

        DamageTagArmorStand damageTagArmorStand = new DamageTagArmorStand(entity.getLocation(), 30, isCrit, finalDamage, entity);
        damageTagArmorStand.create();

        if(!entity.isDead()) {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setNoDamageTicks(0);
            if(!entity.getType().equals(EntityType.PLAYER)) {
                EntityBase entityBase = EntityBase.getEntityBase(entity);
                entityBase.tagArmorStand.updateName();
            }
        }
    }

    public void die() {
        p.teleport(p.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        p.setNoDamageTicks(30);
        p.setHealth(p.getMaxHealth());

        p.sendMessage(Text.translate("&cYou died and gained " + String.format("%,.1f", money) + " coins!"));
        money = money.multiply(BigDecimal.valueOf(2d));
    }

    public static PlayerBase getPlayerBase(Player p) throws SQLException {
        if(playerBaseHashMap.containsKey(p.getUniqueId())) {
            return playerBaseHashMap.get(p.getUniqueId());
        }
        return new PlayerBase(p);
    }
}
