package com.natamus.wanderingtradermayleave.networking;

import com.natamus.collective.implementations.networking.api.Network;
import com.natamus.wanderingtradermayleave.networking.packets.ToClientShowPoofParticlesPacket;
import com.natamus.wanderingtradermayleave.networking.packets.ToServerMakeWanderingTraderLeavePacket;

public class PacketRegistration {

    public void init() {
        initClientPackets();
        initServerPackets();
    }

    private void initClientPackets() {
        Network.registerPacket(ToClientShowPoofParticlesPacket.CHANNEL, ToClientShowPoofParticlesPacket.class, ToClientShowPoofParticlesPacket::encode, ToClientShowPoofParticlesPacket::decode, ToClientShowPoofParticlesPacket::handle);
    }

    private void initServerPackets() {
        Network.registerPacket(ToServerMakeWanderingTraderLeavePacket.CHANNEL, ToServerMakeWanderingTraderLeavePacket.class, ToServerMakeWanderingTraderLeavePacket::encode, ToServerMakeWanderingTraderLeavePacket::decode, ToServerMakeWanderingTraderLeavePacket::handle);
    }
}
