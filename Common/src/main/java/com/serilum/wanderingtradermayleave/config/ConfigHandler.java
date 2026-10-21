package com.serilum.wanderingtradermayleave.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.wanderingtradermayleave.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean despawnTraderLlamasToo = true;
	@Entry public static boolean onlyDespawnLlamasWhenLeashed = true;
	@Entry public static boolean dropLeashesWhenLlamasDespawn = true;
	@Entry public static boolean showDespawnParticles = true;
	@Entry public static boolean drinkInvisibilityPotionToLeave = true;
	@Entry(registry = "entity_type", filter = "mobs") public static String extraEntityTypesThatMayLeave = "rats:plague_doctor";

	public static void initConfig() {
		configMetaData.put("despawnTraderLlamasToo", Arrays.asList(
			"If the trader llamas should be despawned alongside the wandering trader when pressing the button."
		));
		configMetaData.put("onlyDespawnLlamasWhenLeashed", Arrays.asList(
			"Whether the trader llamas should only be despawned if they are leashed to the wandering trader."
		));
		configMetaData.put("dropLeashesWhenLlamasDespawn", Arrays.asList(
			"If the leads of despawned trader llamas should drop on the ground as an item."
		));
		configMetaData.put("showDespawnParticles", Arrays.asList(
			"If particles should be shown when despawning the wandering trader and/or trader llamas."
		));
		configMetaData.put("drinkInvisibilityPotionToLeave", Arrays.asList(
			"If the wandering trader should drink an invisibility potion before leaving, like it does at night, instead of disappearing in a puff of particles."
		));
		configMetaData.put("extraEntityTypesThatMayLeave", Arrays.asList(
			"A comma-separated list of entity ids of modded merchants that also get the leave button. Pressing it makes that merchant leave. Only works for merchants that use the vanilla trade screen. Use '<modid>:*' to include every entity of a mod."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}