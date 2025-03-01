package com.natamus.wanderingtradermayleave.forge.events;

import com.natamus.wanderingtradermayleave.cmds.CommandWanderingTrader;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeCommandRegisterEvent {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent e) {
    	CommandWanderingTrader.register(e.getDispatcher());
    }
}
