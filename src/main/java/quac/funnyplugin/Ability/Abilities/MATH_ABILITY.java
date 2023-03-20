package quac.funnyplugin.Ability.Abilities;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import quac.funnyplugin.Ability.AbilityBaseWithCost;
import quac.funnyplugin.Ability.AbilityUseButton;
import quac.funnyplugin.Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MATH_ABILITY extends AbilityBaseWithCost {
    public MATH_ABILITY(AbilityUseButton button) {
        super(button);
        this.name = "WOOO";
        this.cooldownTicks = 0;
        this.description = "FunnyMath";
        this.manaCost = 0;
    }

    @Override
    public boolean use(PlayerInteractEvent event) throws SQLException {
        if(!super.use(event)) {
            return false;
        }
        Player p = event.getPlayer();

        Location origin = p.getLocation().add(0,2.0,0);
        p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 1f, 4f);

        List<RainbowPiece> pieces = new ArrayList<>();

        double height = 50;
        /*for(double y = 0; y < height; y++) {
            double radius = y/10;
            double rad = (y/20.0) * Math.PI * 2;
            Location standLocation = origin.clone().add(
                    Math.cos(rad) * radius,
                    0,
                    Math.sin(rad) * radius
            );

            ArmorStand stand = spawnStaticStand(standLocation, (int) y);
            RainbowPiece rainbowPiece = new RainbowPiece(stand, y, radius, rad);
            pieces.add(rainbowPiece);
        }*/

        for(double y = height; y > 0; y--) {
            double radius = y/10;
            double rad = (y/20.0) * Math.PI * 2;
            Location standLocation = origin.clone().add(
                    Math.cos(rad) * radius,
                    1,
                    Math.sin(rad) * radius
            );

            double finalY = y;
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                ArmorStand stand = spawnStaticStand(standLocation, (int) finalY);
                RainbowPiece rainbowPiece = new RainbowPiece(stand, finalY, radius, rad);
                pieces.add(rainbowPiece);
            },  (long)(height-y));
        }

        return true;
    }

    private static final DyeColor[] RAINBOW = new DyeColor[]{DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE, DyeColor.YELLOW, DyeColor.LIME, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE, DyeColor.GRAY};

    private ArmorStand spawnStaticStand(Location loc, int index) {
        ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.teleport(loc.clone().subtract(0,1,0));
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setMarker(true);

        DyeColor color = RAINBOW[index % RAINBOW.length];
        ItemStack wool = new ItemStack(Material.WOOL, 1);
        stand.getEquipment().setHelmet(wool);

        return stand;
    }

    private static class RainbowPiece {
        private final ArmorStand stand;
        private final double height;
        private final double radius;
        private double rad;

        private RainbowPiece(ArmorStand stand, double height, double radius, double rad) {
            this.stand = stand;
            this.height = height;
            this.radius = radius;
            this.rad = rad;
        }
    }
}
