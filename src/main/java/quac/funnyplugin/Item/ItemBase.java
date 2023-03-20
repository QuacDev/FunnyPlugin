package quac.funnyplugin.Item;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import quac.funnyplugin.Ability.AbilityBase;
import quac.funnyplugin.Custom.Stat;
import quac.funnyplugin.Custom.StatType;
import quac.funnyplugin.Item.Modifier.Modifier;
import quac.funnyplugin.Item.Modifier.ModifierRegistry;
import quac.funnyplugin.utils.Text;
import quac.funnyplugin.utils.Keys;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ItemBase {
    public String id;
    public String displayName;
    public Material mat;
    public Rarity baseRarity;
    public boolean autoColorDisplay = true;
    public boolean hasAbility = false;
    public List<AbilityBase> abilities = new ArrayList<>();

    public HashMap<StatType, Double> baseStats = new HashMap<>();

    public ItemBase() {
        this.id = "UNDEFINED_ID";
        this.displayName = "Undefined DisplayName";
        this.mat = Material.STONE;
        this.baseRarity = Rarity.COMMON;
    }


    public ItemStack getFunnyItemStack() {
        ItemStack stack = new ItemStack(this.mat);

        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(stack);

        NBTTagCompound nbtTag = new NBTTagCompound();
        NBTTagCompound extraAttributes = new NBTTagCompound();

        extraAttributes.setString(Keys.ID_KEY, this.id);
        extraAttributes.setString(Keys.UUID_KEY, String.valueOf(UUID.randomUUID()));
        extraAttributes.setInt(Keys.RARITY_UPGRADE, 0);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        extraAttributes.setString(Keys.TIMESTAMP_KEY, dtf.format(now));

        nbtTag.set(Keys.EXTRA_ATTRIBUTES, extraAttributes);

        nmsItem.setTag(nbtTag);

        ItemStack newStack = CraftItemStack.asBukkitCopy(nmsItem);

        ItemMeta m = newStack.getItemMeta();

        if(m == null) return null;

        m.setLore(getLore(extraAttributes));

        m.setDisplayName(getDisplayName(extraAttributes));

        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        m.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        m.addItemFlags(ItemFlag.HIDE_DESTROYS);
        m.addItemFlags(ItemFlag.HIDE_PLACED_ON);

        newStack.setItemMeta(m);
        return newStack;
    }


    public static HashMap<StatType, Double> getStatsOfStack(ItemStack item) {
        HashMap<StatType, Double> stats = new HashMap<>(Stat.getEmptyStats());
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if(nmsItem == null) return null;
        if(nmsItem.hasTag()) {
            NBTTagCompound tag = nmsItem.getTag();
            assert tag != null;
            if(tag.hasKey(Keys.EXTRA_ATTRIBUTES)) {
                NBTTagCompound extraAttributes = tag.getCompound(Keys.EXTRA_ATTRIBUTES);
                String id = extraAttributes.getString(Keys.ID_KEY);
                ItemBase itemBase = ItemRegistry.getFunnyItem(id);
                if(itemBase != null) {
                    HashMap<StatType, Double> a = itemBase.getStats(extraAttributes);
                    a.forEach((statType, aDouble) -> {
                        stats.merge(statType, aDouble, Double::sum);
                    });
                    if(extraAttributes.hasKey(Keys.MODIFIER)) {
                        Modifier.getModifierStats(extraAttributes).forEach((statType, aDouble) -> {
                            stats.merge(statType, aDouble, Double::sum);
                        });
                    }
                }
            }
        }

        return stats;
    }

    public void useAbility(PlayerInteractEvent event) throws SQLException {

    }

    public List<String> getLore(NBTTagCompound extraAttributes) {

        List<String> lore = new ArrayList<>(getStatsLore(extraAttributes));
        lore.addAll(getDescription(extraAttributes));
        lore.add("");
        lore.addAll(getAbilityLore(extraAttributes));
        lore.add(getRarityDisplay(extraAttributes));

        return lore;
    }

    public List<String> getDescription(NBTTagCompound extraAttributes) {
        return new ArrayList<>();
    }

    public String getRarityDisplay(NBTTagCompound extraAttributes) {
        int rarityUpgrades = extraAttributes.getInt(Keys.RARITY_UPGRADE);

        Rarity rarity =  Rarity.getRarityFromIndex(this.baseRarity.index + extraAttributes.getInt(Keys.RARITY_UPGRADE));
        if(rarityUpgrades == 0) {
            return Text.translate(rarity.display);
        } else {
            return Text.translate( rarity.color.code + "&l&kR&r " + rarity.display + " " + rarity.color.code + "&l&kR");
        }
    }

    public List<String> getStatsLore(NBTTagCompound extraAttributes) {
        List<String> lore = new ArrayList<>();
        HashMap<StatType, Double> stats = new HashMap<>();
        getStats(extraAttributes).forEach((statType, aDouble) -> {
            stats.merge(statType, aDouble, Double::sum);
        });
        Modifier.getModifierStats(extraAttributes).forEach((statType, aDouble) -> {
            stats.merge(statType, aDouble, Double::sum);
        });

        boolean hasModifier = extraAttributes.hasKey(Keys.MODIFIER);
        HashMap<StatType, Double> modifierStats = Modifier.getModifierStats(extraAttributes);

        DecimalFormat format = new DecimalFormat("0.#");

        if(stats.containsKey(StatType.BASE_MELEE_DAMAGE)) {
            String t = Text.translate("&7Damage: &c+" + format.format(stats.get(StatType.BASE_MELEE_DAMAGE)));
            if(modifierStats.containsKey(StatType.BASE_MELEE_DAMAGE) && hasModifier) {
                t += Text.translate(" &9(+" + Math.round(modifierStats.get(StatType.BASE_MELEE_DAMAGE).floatValue()) + ")");
            }
            lore.add(t);
        }
        if(stats.containsKey(StatType.STRENGTH)) {
            String t = Text.translate("&7Strength: &c+" + format.format(stats.get(StatType.STRENGTH)));
            if(modifierStats.containsKey(StatType.STRENGTH) && hasModifier) {
                t += Text.translate(" &9(+" + Math.round(modifierStats.get(StatType.STRENGTH).floatValue()) + ")");
            }
            lore.add(t);
        }
        if(stats.containsKey(StatType.CRITICAL_CHANCE)) {
            String t = Text.translate("&7Crit Chance: &c+" + format.format(stats.get(StatType.CRITICAL_CHANCE)));
            if(modifierStats.containsKey(StatType.CRITICAL_CHANCE) && hasModifier) {
                t += Text.translate(" &9(+" + Math.round(modifierStats.get(StatType.CRITICAL_CHANCE).floatValue()) + ")");
            }
            lore.add(t);
        }
        if(stats.containsKey(StatType.CRITICAL_DAMAGE)) {
            String t = Text.translate("&7Crit Damage: &c+" + format.format(stats.get(StatType.CRITICAL_DAMAGE)));
            if(modifierStats.containsKey(StatType.CRITICAL_DAMAGE) && hasModifier) {
                t += Text.translate(" &9(+" + Math.round(modifierStats.get(StatType.CRITICAL_DAMAGE).floatValue()) + ")");
            }
            lore.add(t);
        }
        if(stats.containsKey(StatType.INTELLIGENCE)) {
            String t = Text.translate("&7Intelligence: &a+" + format.format(stats.get(StatType.INTELLIGENCE)));
            if(modifierStats.containsKey(StatType.INTELLIGENCE) && hasModifier) {
                t += Text.translate(" &9(+" + Math.round(modifierStats.get(StatType.INTELLIGENCE).floatValue()) + ")");
            }
            lore.add(t);
        }
        if(stats.containsKey(StatType.MAX_HEALTH)) {
            String t = Text.translate("&7Health: &a+" + format.format(stats.get(StatType.MAX_HEALTH)));
            if(modifierStats.containsKey(StatType.MAX_HEALTH) && hasModifier) {
                t += Text.translate("& 9(+" + Math.round(modifierStats.get(StatType.MAX_HEALTH).floatValue()) + ")");
            }
            lore.add(t);
        }
        if(stats.containsKey(StatType.DEFENSE)) {
            String t = Text.translate("&7Defense: &a+" + format.format(stats.get(StatType.DEFENSE)));
            if(modifierStats.containsKey(StatType.DEFENSE) && hasModifier) {
                t += Text.translate(" &9(+" + Math.round(modifierStats.get(StatType.DEFENSE).floatValue()) + ")");
            }
            lore.add(t);
        }
        return lore;
    }

    public List<String> getAbilityLore(NBTTagCompound extraAttributes) {
        List<String> lore = new ArrayList<>();
        for (AbilityBase ability : this.abilities) {
            lore.addAll(ability.getAbilityLore());
            lore.add("");
        }
        return lore;
    }

    public static String getDisplayName(NBTTagCompound extraAttributes) {
        ItemBase item = ItemRegistry.getFunnyItem(extraAttributes.getString(Keys.ID_KEY));

        String display = "";
        if(extraAttributes.hasKey(Keys.MODIFIER)) {
            display = ModifierRegistry.getModifier(extraAttributes.getString(Keys.MODIFIER.toLowerCase())).getDisplay();
        }

        if(item.autoColorDisplay) {
            return Text.translate(Rarity.getRarityFromIndex(
                    item.baseRarity.index + extraAttributes.getInt(Keys.RARITY_UPGRADE)).color.code + display + item.displayName);
        } else {
            return Text.translate(display + item.displayName);
        }
    }

    // Modify later With enchants n stuff
    public HashMap<StatType, Double> getStats(NBTTagCompound extraAttributes) {
        return this.baseStats;
    }
}
