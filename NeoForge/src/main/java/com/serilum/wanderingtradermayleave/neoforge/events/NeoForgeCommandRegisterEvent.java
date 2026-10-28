package com.serilum.wanderingtradermayleave.neoforge.events;

import com.serilum.wanderingtradermayleave.cmds.CommandWanderingTrader;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class NeoForgeCommandRegisterEvent {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent e) {
		CommandWanderingTrader.register(e.getDispatcher());
	}
}
