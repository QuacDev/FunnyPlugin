package quac.funnyplugin;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import quac.funnyplugin.Entity.Custom.NPC;
import quac.funnyplugin.Entity.PlayerBase;
import quac.funnyplugin.database.DataBase;
import quac.funnyplugin.database.PlayerData;

import java.sql.SQLException;

public final class Main extends JavaPlugin {
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Registry.init();
        DataBase.init();

        for (Player p : getServer().getOnlinePlayers()) {
            try {
                initPlayer(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void initPlayer(Player p) throws SQLException {
        PlayerBase base = PlayerBase.getPlayerBase(p);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            CraftPlayer craftPlayer = (CraftPlayer) p;
            EntityPlayer entityPlayer = craftPlayer.getHandle();
            PlayerConnection playerConnection = entityPlayer.playerConnection;

            //Funny arrow ding
            PacketPlayOutGameStateChange packet = new PacketPlayOutGameStateChange(6, 1);
            playerConnection.sendPacket(packet);

            PlayerData playerData = null;
            try {
                playerData = DataBase.getPlayerData(base);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            base.money = playerData.getMoney();
        }, 5);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player p : getServer().getOnlinePlayers()) {
            PlayerBase base = null;
            try {
                base = PlayerBase.getPlayerBase(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            base.DestroyPlayerBase();
        }


        if(NPC.npcs.size() > 0) {
            for (NPC npc : NPC.npcs) {
                npc.removeNPC();
            }
        }
    }
}
