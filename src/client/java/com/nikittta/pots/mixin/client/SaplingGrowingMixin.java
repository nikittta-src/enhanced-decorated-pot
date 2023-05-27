package com.nikittta.pots.mixin.client;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SaplingBlock.class)
public class SaplingGrowingMixin {

    @Inject(at=@At("HEAD"), method="canGrow", cancellable = true)
    public void canGrow(World world, Random random, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir){
        if (world.getBlockState(pos.down()).getBlock() instanceof EnhancedDecoratedPotBlock){
            cir.setReturnValue(false);
        }
    }

}
