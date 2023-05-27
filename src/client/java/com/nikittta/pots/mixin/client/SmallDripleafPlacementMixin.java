package com.nikittta.pots.mixin.client;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SmallDripleafBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SmallDripleafBlock.class)
public class SmallDripleafPlacementMixin {

    @Inject(at=@At("HEAD"), method="canPlantOnTop", cancellable = true)
    public void canPlaceOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if (world.getBlockState(pos.down()).getBlock() instanceof EnhancedDecoratedPotBlock){
            cir.setReturnValue(true);
        }
    }

}
