package quac.funnyplugin.events.network;


import net.minecraft.server.v1_8_R3.*;

public class QuacNetHandler extends PlayerConnection {

    public QuacNetHandler(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
        super(minecraftserver, networkmanager, entityplayer);
    }

    @Override
    public void sendPacket(Packet packet) {

    }
}
