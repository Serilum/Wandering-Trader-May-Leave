package com.natamus.wanderingtradermayleave.forge.events;

import com.natamus.wanderingtradermayleave.cmds.CommandWanderingTrader;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeCommandRegisterEvent {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeCommandRegisterEvent.class);

		RegisterCommandsEvent.BUS.addListener(ForgeCommandRegisterEvent::registerCommands);
	}

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent e) {
    	CommandWanderingTrader.register(e.getDispatcher());
    }
}
