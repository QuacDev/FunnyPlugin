package quac.funnyplugin.Entity.Custom;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import quac.funnyplugin.events.network.QuacNetHandler;
import quac.funnyplugin.events.network.QuacNetManager;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class NPC extends EntityPlayer {
    public static Set<NPC> npcs = new HashSet<>();

    public NPC(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
    }

    public static void create(Location location, String name, String[] skin) {
        UUID uuid = UUID.randomUUID();

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile profile = new GameProfile(uuid, name);

        NPC npc = new NPC(nmsServer, nmsWorld, profile, new PlayerInteractManager(nmsWorld.getWorld().getHandle()));
        npc.playerConnection = new QuacNetHandler(nmsServer, new QuacNetManager(EnumProtocolDirection.CLIENTBOUND), npc);
        npcs.add(npc);

        npc.setPosRot(location);
        npc.getBukkitEntity().setNoDamageTicks(0);
        npc.setSkin(skin);

        Bukkit.getOnlinePlayers().forEach(player -> {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(
                    new PacketPlayOutPlayerInfo(
                            PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                            npc
                    )
            );
        });

        nmsWorld.addEntity(npc);

        NPC.showAll(npc);
    }

    private void setSkin(String[] skin) {
        String texture = skin[0];
        String signature = skin[1];
        this.getProfile().getProperties().put("textures", new Property("textures", texture, signature));
    }

    private void setPosRot(Location location) {
        this.setPosition(location.getX(), location.getY(), location.getZ());

        float yaw = location.getYaw();
        float pitch = location.getPitch();

        this.setYawPitch(yaw, pitch);
        this.lastYaw = yaw;
        this.lastPitch = pitch;
    }

    private static void showAll(EntityPlayer npc) {
        PacketPlayOutPlayerInfo playerInfoAdd = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc);
        PacketPlayOutNamedEntitySpawn namedEntitySpawn = new PacketPlayOutNamedEntitySpawn(npc);
        PacketPlayOutEntityHeadRotation headRotation = new PacketPlayOutEntityHeadRotation(npc, (byte) Math.floor(npc.getHeadRotation() * 256f / 360f));
        PacketPlayOutPlayerInfo playerInfoRemove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc);

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(playerInfoAdd);
            connection.sendPacket(namedEntitySpawn);
            connection.sendPacket(headRotation);
            connection.sendPacket(playerInfoRemove);
        }
    }


    @Override
    public void doTick() {
        super.doTick();
        //moveWithFallDamage();
    }

    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        boolean damaged = super.damageEntity(damagesource, f);
        if(damaged) {
            if(this.hurtTicks < 0) {
                this.hurtTicks = 0;
                //Bukkit.getScheduler().runTask(Main.getPlugin(Main.class), () -> NPC.this.hurtTicks = true);
            }
        }
        return damaged;
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        npcs.remove(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            PacketPlayOutPlayerInfo playerInfoRemove = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this);
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(playerInfoRemove);
        }
    }

   /* private void moveWithFallDamage() {
        double y = locY;
        move(new Vec3D(0,0,0));
        doCheckFallDamage(locY - y, onGround);
    }*/

    public void removeNPC() {
        npcs.remove(this);
        this.removeNPC();
    }

    public static String[] getSkin(Player player, String name) {
        try {
            JsonParser JsonParser = new JsonParser();
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = JsonParser.parse(reader).getAsJsonObject().get("id").getAsString();
            URL url1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + "?unsigned=false");
            InputStreamReader reader1 = new InputStreamReader(url1.openStream());
            JsonObject property = JsonParser.parse(reader1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[] {texture, signature};
        } catch (Exception e) {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            GameProfile profile = p.getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            return new String[] {texture, signature};
        }
    }
}
