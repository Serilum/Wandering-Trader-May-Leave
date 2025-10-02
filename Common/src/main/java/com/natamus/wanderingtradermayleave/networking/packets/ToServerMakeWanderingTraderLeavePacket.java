package com.natamus.wanderingtradermayleave.networking.packets;

import com.natamus.collective.implementations.networking.api.Dispatcher;
import com.natamus.collective.implementations.networking.data.PacketContext;
import com.natamus.collective.implementations.networking.data.Side;
import com.natamus.wanderingtradermayleave.config.ConfigHandler;
import com.natamus.wanderingtradermayleave.util.Reference;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ToServerMakeWanderingTraderLeavePacket {
    public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "to_server_make_wandering_trader_leave");

    public ToServerMakeWanderingTraderLeavePacket() {
    }

    public static ToServerMakeWanderingTraderLeavePacket decode(FriendlyByteBuf buf) {
        return new ToServerMakeWanderingTraderLeavePacket();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static void handle(PacketContext<ToServerMakeWanderingTraderLeavePacket> ctx) {
        if (ctx.side().equals(Side.SERVER)) {
            ToServerMakeWanderingTraderLeavePacket packet = ctx.message();

            ServerPlayer serverPlayer = (ServerPlayer)ctx.sender();
            Level level = serverPlayer.level();
            Vec3 pVec = serverPlayer.position();

            if (ConfigHandler.despawnTraderLlamasToo) {
                for (Entity entity : level.getEntitiesOfClass(TraderLlama.class, new AABB(pVec.x - 10, pVec.y - 10, pVec.z - 10, pVec.x + 10, pVec.y + 10, pVec.z + 10))) {
                    if (entity instanceof TraderLlama traderLlama ) {
                        if (traderLlama.getLeashHolder() instanceof WanderingTrader || !ConfigHandler.onlyDespawnLlamasWhenLeashed) {
                            traderLlama.removeLeash();
                            traderLlama.remove(Entity.RemovalReason.DISCARDED);

                            if (ConfigHandler.showDespawnParticles) {
                                Dispatcher.sendToClient(new ToClientShowPoofParticlesPacket(traderLlama.getX(), traderLlama.getY(), traderLlama.getZ()), serverPlayer);
                            }
                        }
                    }
                }
            }

            for (Entity entity : level.getEntitiesOfClass(WanderingTrader.class, new AABB(pVec.x - 10, pVec.y - 10, pVec.z - 10, pVec.x + 10, pVec.y + 10, pVec.z + 10))) {
                if (entity instanceof WanderingTrader wanderingTrader) {
                    wanderingTrader.setTradingPlayer(null);

                    wanderingTrader.setDespawnDelay(1);

                    if (ConfigHandler.showDespawnParticles) {
                        Dispatcher.sendToClient(new ToClientShowPoofParticlesPacket(wanderingTrader.getX(), wanderingTrader.getY(), wanderingTrader.getZ()), serverPlayer);
                    }
                }
            }
        }
    }
}
