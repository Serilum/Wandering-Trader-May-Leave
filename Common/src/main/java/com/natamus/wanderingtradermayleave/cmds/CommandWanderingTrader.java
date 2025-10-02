package com.natamus.wanderingtradermayleave.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.collective.functions.MessageFunctions;
import com.natamus.wanderingtradermayleave.mixin.WanderingTraderSpawnerInvoker;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;

public class CommandWanderingTrader {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("wandering-trader").requires((iCommandSender) -> { return iCommandSender.hasPermission(2) && iCommandSender.isPlayer(); })
			.then(Commands.literal("spawn")
			.executes((command) -> {
				CommandSourceStack source = command.getSource();
				Player player = source.getPlayer();
				Level level = player.level();

				if (level.isClientSide()) {
					return 0;
				}

                //noinspection ConstantValue
                for (int triesLeft = 20; triesLeft > 0; triesLeft--) {
					WanderingTraderSpawner wanderingTraderSpawner = new WanderingTraderSpawner((ServerLevelData)level.getLevelData());
					if (((WanderingTraderSpawnerInvoker)wanderingTraderSpawner).invokeSpawn((ServerLevel)level)) {
						MessageFunctions.sendMessage(player, "Wandering trader spawn succesful!", ChatFormatting.DARK_GREEN);
						return 1;
					}
				}

				MessageFunctions.sendMessage(player, "Wandering trader spawn failed, try again.", ChatFormatting.RED);
				return 1;
			}))
		);
    }
}
