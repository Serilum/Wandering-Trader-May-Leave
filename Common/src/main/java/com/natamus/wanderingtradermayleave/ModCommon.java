package com.natamus.wanderingtradermayleave;

import com.natamus.wanderingtradermayleave.config.ConfigHandler;
import com.natamus.wanderingtradermayleave.networking.PacketRegistration;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();

		registerPackets();

		load();
	}

	private static void load() {
		
	}

	public static void registerPackets() {
		new PacketRegistration().init();
	}
}