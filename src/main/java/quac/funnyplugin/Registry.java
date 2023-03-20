package quac.funnyplugin;

import com.google.common.reflect.ClassPath;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import quac.funnyplugin.Custom.CustomCommand;
import quac.funnyplugin.Item.ItemRegistry;
import quac.funnyplugin.Item.Modifier.ModifierRegistry;
import quac.funnyplugin.events.EntityEvents;
import quac.funnyplugin.events.PlayerEvents;
import quac.funnyplugin.utils.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static org.bukkit.Bukkit.getServer;

public class Registry {
    public JavaPlugin plugin;

    public Registry(JavaPlugin plugin) {
        this.plugin = plugin;

        init();
    }

    public static void init() {
        getServer().getPluginManager().registerEvents(new PlayerEvents(), Main.plugin);
        getServer().getPluginManager().registerEvents(new EntityEvents(), Main.plugin);

        ItemRegistry.register();
        ModifierRegistry.register();

        try {
            List<ClassPath.ClassInfo> customCommands = new ArrayList<>(ClassesFromFolder("quac.funnyplugin.Commands", Main.plugin));

            System.out.println("Found " + customCommands.size() + " commands to register: " + customCommands.toString());

            RegisterCustomCommandsFromlist(customCommands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static void invalidArgs(Player player, String commandName) {
        player.sendMessage(Text.translate("&cInvalid Arguments!"));
        player.sendMessage(Text.translate("&cUse: '" + Bukkit.getPluginCommand(commandName).getUsage() + "'."));
    }

    static void registerCommand(String command, BiConsumer<Player, String[]> executor) {
        Main.plugin.getCommand(command).setExecutor((sender, command1, label, args) -> {
            executor.accept((Player) sender, args);
            return true;
        });
    }

    public static void updateChunkForPlayer(Player player, Chunk chunk) {
        EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        nmsPlayer.chunkCoordIntPairQueue.add(new ChunkCoordIntPair(chunk.getX(), chunk.getZ()));
    }


    public static void RegisterCustomCommandsFromlist(final List<ClassPath.ClassInfo> list) {
        System.out.println("Loading command handlers...");
        System.out.println("---------------------------------");

        for (final ClassPath.ClassInfo c : list) {
            try {
                CustomCommand customCommand = (CustomCommand) c.load().getConstructors()[0].newInstance();

                Main.plugin.getServer().getPluginCommand(c.getSimpleName().toLowerCase()).setExecutor(customCommand);
                System.out.println("LOADED COMMAND: " + c.getSimpleName());
            } catch (Exception e) {
                System.out.println("Error whilst loading command: " + c.getSimpleName());
                e.printStackTrace();
            }
        }

        System.out.println("---------------------------------");
    }

    public static List<ClassPath.ClassInfo> ClassesFromFolder(final String path, Plugin pl){
        final List<ClassPath.ClassInfo> toReturn = new ArrayList<>();
        try {
            for (final ClassPath.ClassInfo classInfo : ClassPath.from(pl.getClass().getClassLoader()).getTopLevelClassesRecursive(path)) {
                if(classInfo==null)continue;
                try{
                    toReturn.add(classInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}
