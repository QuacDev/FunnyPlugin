package quac.funnyplugin.Item;

import quac.funnyplugin.Item.Items.Armor.UNKNOWN_HELMET;
import quac.funnyplugin.Item.Items.Armor.QUAC_HELMET;
import quac.funnyplugin.Item.Items.Misc.CHARLIE_WOOL;
import quac.funnyplugin.Item.Items.Misc.FUNNY_STONE;
import quac.funnyplugin.Item.Items.Misc.FLORID_ZOMBIE_SWORD;
import quac.funnyplugin.Item.Items.ReforgeStones.DRAGON_CLAW;
import quac.funnyplugin.Item.Items.ReforgeStones.RECOMBOBULATOR;
import quac.funnyplugin.Item.Items.KAIBLOCK_MENU;
import quac.funnyplugin.Item.Items.Weapons.*;
import quac.funnyplugin.Item.Items.Weapons.WitherBlades.*;

import java.util.HashMap;

public class ItemRegistry {
    public static HashMap<String, ItemBase> registeredItems = new HashMap<>();

    static void registerFunnyItem(ItemBase a) {
        registeredItems.put(a.id, a);
    }

    public static ItemBase getFunnyItem(String id) {
        return registeredItems.get(id);
    }

    public static void register() {
        System.out.println("Registering all custom items...");

        registerFunnyItem(new KAIBLOCK_MENU());

        registerFunnyItem(new FUNNY_STONE());
        registerFunnyItem(new CHARLIE_WOOL());
        registerFunnyItem(new FLORID_ZOMBIE_SWORD());

        registerFunnyItem(new ASPECT_OF_THE_JERRY());
        registerFunnyItem(new STAR_SWORD());
        registerFunnyItem(new JUJU_SHORTBOW());
        registerFunnyItem(new TERMINATOR());

        registerFunnyItem(new NECRONS_BLADE());
        registerFunnyItem(new HYPERION());
        registerFunnyItem(new ASTRAEA());
        registerFunnyItem(new VALKYRIE());
        registerFunnyItem(new SCYLLA());

        registerFunnyItem(new QUAC_HELMET());
        registerFunnyItem(new UNKNOWN_HELMET());

        registerFunnyItem(new ASPECT_OF_THE_END());
        registerFunnyItem(new ASPECT_OF_THE_VOID());


        registerFunnyItem(new RECOMBOBULATOR());
        registerFunnyItem(new DRAGON_CLAW());
        registerFunnyItem(new ReforgeStone(Head.EMPTY));
        //registerFunnyItem(new MATH_WAND());

        System.out.println("Registered (" + registeredItems.size() + ") custom items.");
    }
}
