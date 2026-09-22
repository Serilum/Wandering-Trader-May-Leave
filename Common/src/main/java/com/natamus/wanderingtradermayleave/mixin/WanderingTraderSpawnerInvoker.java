package com.natamus.wanderingtradermayleave.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTraderSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = WanderingTraderSpawner.class, priority = 1001)
	public interface WanderingTraderSpawnerInvoker {
		@Invoker("spawn")
		boolean invokeSpawn(ServerLevel serverLevel);
	}