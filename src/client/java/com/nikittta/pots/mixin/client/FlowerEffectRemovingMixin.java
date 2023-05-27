package com.nikittta.pots.mixin.client;

import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowerBlock.class)
public class FlowerEffectRemovingMixin {

    @Inject(at=@At("HEAD"), method="getEffectInStew", cancellable = true)
    public void getEffect(CallbackInfoReturnable<StatusEffect> cir){
        cir.setReturnValue(
                null
        );
    }

}
