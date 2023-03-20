package quac.funnyplugin.events.network;

import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;

public class QuacNetManager extends NetworkManager {

    public QuacNetManager(EnumProtocolDirection enumprotocoldirection) {
        super(enumprotocoldirection);
    }
}
