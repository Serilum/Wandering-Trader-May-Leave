package com.serilum.wanderingtradermayleave.util;

import com.natamus.collective.functions.ConfigFunctions;
import com.natamus.collective.functions.TaskFunctions;
import com.natamus.collective.implementations.networking.api.Dispatcher;
import com.serilum.wanderingtradermayleave.config.ConfigHandler;
import com.serilum.wanderingtradermayleave.networking.packets.ToClientShowPoofParticlesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.TraderLlama;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.Merchant;

public class LeaveUtil {
	public static final int drinkDurationTicks = 40;

	public static boolean mayLeave(Entity entity) {
		if (entity instanceof WanderingTrader) {
			return true;
		}

		String entityId = EntityType.getKey(entity.getType()).toString();
		return ConfigFunctions.isInIdList(ConfigHandler.extraEntityTypesThatMayLeave, entityId);
	}

	public static void makeLeave(ServerPlayer serverPlayer, Entity entity) {
		if (entity instanceof Merchant merchant) {
			merchant.setTradingPlayer(null);
		}

		if (ConfigHandler.drinkInvisibilityPotionToLeave && entity instanceof LivingEntity livingEntity) {
			drinkInvisibilityPotionAndLeave(serverPlayer, livingEntity);
			return;
		}

		if (entity instanceof WanderingTrader wanderingTrader) {
			wanderingTrader.setDespawnDelay(1);
		}
		else {
			entity.discard();
		}

		showPoofParticles(serverPlayer, entity);
	}

	public static void makeTraderLlamaLeave(ServerPlayer serverPlayer, TraderLlama traderLlama) {
		if (ConfigHandler.drinkInvisibilityPotionToLeave) {
			TaskFunctions.enqueueCollectiveServerTask(serverPlayer.level().getServer(), () -> {
				removeTraderLlama(traderLlama);
			}, drinkDurationTicks);
			return;
		}

		removeTraderLlama(traderLlama);
		showPoofParticles(serverPlayer, traderLlama);
	}

	private static void drinkInvisibilityPotionAndLeave(ServerPlayer serverPlayer, LivingEntity livingEntity) {
		livingEntity.setItemSlot(EquipmentSlot.MAINHAND, PotionContents.createItemStack(Items.POTION, Potions.INVISIBILITY));
		livingEntity.startUsingItem(InteractionHand.MAIN_HAND);

		TaskFunctions.enqueueCollectiveServerTask(serverPlayer.level().getServer(), () -> {
			if (!livingEntity.isAlive()) {
				return;
			}

			livingEntity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			livingEntity.playSound(SoundEvents.WANDERING_TRADER_DISAPPEARED, 1.0F, 1.0F);
			livingEntity.discard();
		}, drinkDurationTicks);
	}

	private static void removeTraderLlama(TraderLlama traderLlama) {
		if (!traderLlama.isAlive()) {
			return;
		}

		if (ConfigHandler.dropLeashesWhenLlamasDespawn) {
			traderLlama.dropLeash();
		}
		else {
			traderLlama.removeLeash();
		}

		traderLlama.remove(Entity.RemovalReason.DISCARDED);
	}

	private static void showPoofParticles(ServerPlayer serverPlayer, Entity entity) {
		if (ConfigHandler.showDespawnParticles) {
			Dispatcher.sendToClient(new ToClientShowPoofParticlesPacket(entity.getX(), entity.getY(), entity.getZ()), serverPlayer);
		}
	}
}
