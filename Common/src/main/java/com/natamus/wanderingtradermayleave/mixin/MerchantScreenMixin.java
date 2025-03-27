package com.natamus.wanderingtradermayleave.mixin;

import com.natamus.wanderingtradermayleave.util.Util;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MerchantScreen.class, priority = 1001)
public class MerchantScreenMixin {
    @Inject(method = "init()V", at = @At(value = "TAIL"))
    protected void init(CallbackInfo ci) {
        Util.addLeaveButton((MerchantScreen)(Object)this);
    }
}
