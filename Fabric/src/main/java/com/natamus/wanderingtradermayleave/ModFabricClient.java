package com.natamus.wanderingtradermayleave;

import net.fabricmc.api.ClientModInitializer;
import com.natamus.wanderingtradermayleave.util.Reference;
import com.natamus.collective.check.ShouldLoadCheck;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		ModCommon.registerPackets();

		registerEvents();
	}
	
	private void registerEvents() {

	}
}
