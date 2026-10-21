package com.serilum.wanderingtradermayleave.mixin;

import com.serilum.wanderingtradermayleave.util.Util;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MerchantScreen.class, priority = 1001)
public class MerchantScreenMixin {
	@Inject(method = "init()V", at = @At(value = "TAIL"))
	protected void init(CallbackInfo ci) {
		MerchantScreen merchantScreen = (MerchantScreen)(Object)this;
		Entity merchant = Util.getLeavingMerchant(merchantScreen);
		if (merchant != null) {
			Util.addLeaveButton(merchantScreen, merchant);
		}
	}
}
