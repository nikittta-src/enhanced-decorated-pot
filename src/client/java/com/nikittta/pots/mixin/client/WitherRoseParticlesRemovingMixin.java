package com.nikittta.pots.mixin.client;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WitherRoseBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherRoseBlock.class)
public class WitherRoseParticlesRemovingMixin {

    @Inject(at=@At("HEAD"), method="randomDisplayTick", cancellable = true)
    public void randomTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci){
        if (world.getBlockState(pos.down()).getBlock() instanceof EnhancedDecoratedPotBlock){
            ci.cancel();
        }
    }

}