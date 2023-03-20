package quac.funnyplugin.Item.Modifier;

import quac.funnyplugin.Item.ItemBase;
import quac.funnyplugin.Item.Modifier.Modifiers.FABLED;

import java.util.HashMap;

public class ModifierRegistry {
    public static HashMap<String, Modifier> registeredModifiers = new HashMap<>();

    static void registerModifier(Modifier a) {
        registeredModifiers.put(a.getId(), a);
    }

    public static Modifier getModifier(String id) {
        return registeredModifiers.get(id);
    }

    public static void register() {
        System.out.println("Registering modifiers...");

        registerModifier(new FABLED());

        System.out.println("Registered (" + registeredModifiers.size() + ") modifiers.");
    }
}
