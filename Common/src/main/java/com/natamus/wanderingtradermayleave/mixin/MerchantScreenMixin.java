package com.natamus.wanderingtradermayleave.mixin;

import com.natamus.wanderingtradermayleave.util.Util;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MerchantScreen.class, priority = 1001)
public class MerchantScreenMixin {
    @Inject(method = "init()V", at = @At(value = "TAIL"))
    protected void init(CallbackInfo ci) {
        MerchantScreen merchantScreen = (MerchantScreen)(Object)this;
		if (merchantScreen.getTitle().getContents() instanceof TranslatableContents contents) {
            if (contents.getKey().equals("entity.minecraft.wandering_trader")) {
                Util.addLeaveButton(merchantScreen);
            }
        }
    }
}
