package com.natamus.wanderingtradermayleave.util;

import com.natamus.collective.functions.ScreenFunctions;
import com.natamus.collective.implementations.networking.api.Dispatcher;
import com.natamus.wanderingtradermayleave.button.SmallTextButton;
import com.natamus.wanderingtradermayleave.networking.packets.ToServerMakeWanderingTraderLeavePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class Util {
    public static void addLeaveButton(MerchantScreen merchantScreen) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) {
            return;
        }

        Vec3 playerVec = localPlayer.position();

        SmallTextButton leaveButton = new SmallTextButton(
            (merchantScreen.width / 2) - 133, (merchantScreen.height / 2) - 78, 10, 10,
            Component.literal("🚷"),
            (button) -> {
                Dispatcher.sendToServer(new ToServerMakeWanderingTraderLeavePacket());

                Minecraft.getInstance().setScreen(null);
            }
        );

        // Set the tooltip separately
        leaveButton.setTooltip(Tooltip.create(Component.literal("Say thank you and make the Wandering Trader leave.")));

        // Add the button using ScreenFunctions
        ScreenFunctions.addRenderableWidget(merchantScreen, leaveButton);
    }
}
