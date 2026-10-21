package com.serilum.wanderingtradermayleave.networking.packets;

import com.natamus.collective.implementations.networking.data.PacketContext;
import com.natamus.collective.implementations.networking.data.Side;
import com.serilum.wanderingtradermayleave.config.ConfigHandler;
import com.serilum.wanderingtradermayleave.util.LeaveUtil;
import com.serilum.wanderingtradermayleave.util.Reference;
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
	public static final ResourceLocation CHANNEL = new ResourceLocation(Reference.MOD_ID, "to_server_make_wandering_trader_leave");

	private final int merchantId;

	public ToServerMakeWanderingTraderLeavePacket(int merchantIdIn) {
		this.merchantId = merchantIdIn;
	}

	public static ToServerMakeWanderingTraderLeavePacket decode(FriendlyByteBuf buf) {
		int merchantIdIn = buf.readInt();

		return new ToServerMakeWanderingTraderLeavePacket(merchantIdIn);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(merchantId);
	}

	public static void handle(PacketContext<ToServerMakeWanderingTraderLeavePacket> ctx) {
		if (ctx.side().equals(Side.SERVER)) {
			ToServerMakeWanderingTraderLeavePacket packet = ctx.message();

			ServerPlayer serverPlayer = (ServerPlayer)ctx.sender();
			Level level = serverPlayer.level();
			Vec3 pVec = serverPlayer.position();

			Entity merchant = level.getEntity(packet.merchantId);
			if (merchant == null || !LeaveUtil.mayLeave(merchant)) {
				return;
			}

			if (!(merchant instanceof WanderingTrader)) {
				if (merchant.distanceTo(serverPlayer) <= 10) {
					LeaveUtil.makeLeave(serverPlayer, merchant);
				}
				return;
			}

			if (ConfigHandler.despawnTraderLlamasToo) {
				for (Entity entity : level.getEntitiesOfClass(TraderLlama.class, new AABB(pVec.x - 10, pVec.y - 10, pVec.z - 10, pVec.x + 10, pVec.y + 10, pVec.z + 10))) {
					if (entity instanceof TraderLlama traderLlama ) {
						if (traderLlama.getLeashHolder() instanceof WanderingTrader || !ConfigHandler.onlyDespawnLlamasWhenLeashed) {
							LeaveUtil.makeTraderLlamaLeave(serverPlayer, traderLlama);
						}
					}
				}
			}

			for (Entity entity : level.getEntitiesOfClass(WanderingTrader.class, new AABB(pVec.x - 10, pVec.y - 10, pVec.z - 10, pVec.x + 10, pVec.y + 10, pVec.z + 10))) {
				if (entity instanceof WanderingTrader wanderingTrader) {
					LeaveUtil.makeLeave(serverPlayer, wanderingTrader);
				}
			}
		}
	}
}
