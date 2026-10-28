package com.serilum.wanderingtradermayleave.util;

import com.natamus.collective.functions.ScreenFunctions;
import com.natamus.collective.implementations.networking.api.Dispatcher;
import com.serilum.wanderingtradermayleave.button.SmallTextButton;
import com.serilum.wanderingtradermayleave.networking.packets.ToServerMakeWanderingTraderLeavePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Util {
	public static Entity getLeavingMerchant(MerchantScreen merchantScreen) {
		LocalPlayer localPlayer = Minecraft.getInstance().player;
		if (localPlayer == null) {
			return null;
		}

		String titleText = merchantScreen.getTitle().getString();
		Vec3 pVec = localPlayer.position();

		Entity closestMerchant = null;
		double closestDistance = Double.MAX_VALUE;

		for (Entity entity : localPlayer.level().getEntities(localPlayer, new AABB(pVec.x - 10, pVec.y - 10, pVec.z - 10, pVec.x + 10, pVec.y + 10, pVec.z + 10))) {
			if (!LeaveUtil.mayLeave(entity)) {
				continue;
			}

			if (!titleText.contains(entity.getName().getString())) {
				continue;
			}

			double distance = entity.distanceToSqr(localPlayer);
			if (distance < closestDistance) {
				closestMerchant = entity;
				closestDistance = distance;
			}
		}

		return closestMerchant;
	}

	public static void addLeaveButton(MerchantScreen merchantScreen, Entity merchant) {
		LocalPlayer localPlayer = Minecraft.getInstance().player;
		if (localPlayer == null) {
			return;
		}

		Vec3 playerVec = localPlayer.position();
		int merchantId = merchant.getId();

		SmallTextButton leaveButton = new SmallTextButton(
			(merchantScreen.width / 2) - 133, (merchantScreen.height / 2) - 78, 10, 10,
			Component.literal("🚷"),
			(button) -> {
				Dispatcher.sendToServer(new ToServerMakeWanderingTraderLeavePacket(merchantId));

				Minecraft.getInstance().setScreen(null);
			}
		);

		// Set the tooltip separately
		leaveButton.setTooltip(Tooltip.create(Component.literal("Say thank you and make the Wandering Trader leave.")));

		// Add the button using ScreenFunctions
		ScreenFunctions.addRenderableWidget(merchantScreen, leaveButton);
	}
}
