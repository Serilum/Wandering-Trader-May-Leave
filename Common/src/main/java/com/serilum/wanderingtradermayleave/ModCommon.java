package com.serilum.wanderingtradermayleave;

import com.serilum.wanderingtradermayleave.config.ConfigHandler;
import com.serilum.wanderingtradermayleave.networking.PacketRegistration;

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