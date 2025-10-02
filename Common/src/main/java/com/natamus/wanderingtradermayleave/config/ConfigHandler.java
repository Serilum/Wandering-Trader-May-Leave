package com.natamus.wanderingtradermayleave.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.wanderingtradermayleave.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean despawnTraderLlamasToo = true;
	@Entry public static boolean onlyDespawnLlamasWhenLeashed = true;
	@Entry public static boolean showDespawnParticles = true;

	public static void initConfig() {
		configMetaData.put("despawnTraderLlamasToo", Arrays.asList(
			"If the trader llamas should be despawned alongside the wandering trader when pressing the button."
		));
		configMetaData.put("onlyDespawnLlamasWhenLeashed", Arrays.asList(
			"Whether the trader llamas should only be despawned if they are leashed to the wandering trader."
		));
		configMetaData.put("showDespawnParticles", Arrays.asList(
			"If particles should be shown when despawning the wandering trader and/or trader llamas."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}