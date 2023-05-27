package com.nikittta.pots.mixin.client;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlock;
import net.minecraft.block.MushroomPlantBlock;
import net.minecraft.item.BoneMealItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoneMealItem.class)
public class ShroomsGrowingParticlesRemovingMixin {

    @Inject(at=@At("HEAD"), method="createParticles", cancellable = true)
    private static void createParticles(WorldAccess world, BlockPos pos, int count, CallbackInfo ci){
        if (world.getBlockState(pos).getBlock() instanceof MushroomPlantBlock
                && world.getBlockState(pos.down()).getBlock() instanceof EnhancedDecoratedPotBlock){
            ci.cancel();
        }
    }

}
