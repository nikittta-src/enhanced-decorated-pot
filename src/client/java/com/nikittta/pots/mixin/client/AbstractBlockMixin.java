package com.nikittta.pots.mixin.client;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockMixin {

    @Inject(at=@At("HEAD"), method="getModelOffset", cancellable = true)
    public void getModelOffset(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3d> cir){
        Block block = world.getBlockState(pos).getBlock();
        Block blockAbove = world.getBlockState(pos.up()).getBlock();
        Block blockBelow = world.getBlockState(pos.down()).getBlock();
        Block blockBelow2 = world.getBlockState(pos.down().down()).getBlock();

        if (block instanceof TallPlantBlock && (
                (blockAbove instanceof TallPlantBlock && blockBelow instanceof EnhancedDecoratedPotBlock) ||
                        (blockBelow instanceof TallPlantBlock && blockBelow2 instanceof EnhancedDecoratedPotBlock)
                )){
            cir.setReturnValue(Vec3d.ZERO);
        }

        if (block instanceof PlantBlock && blockBelow instanceof EnhancedDecoratedPotBlock){
            cir.setReturnValue(Vec3d.ZERO);
        }
    }

//    @Inject(at = @At("HEAD"), method = "getDroppedStacks", cancellable = true)
//    public void getDroppedStacks(LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir){
//        if (((AbstractBlock.AbstractBlockState)(Object)this).getBlock() instanceof PlantBlock){
//            cir.setReturnValue(List.of(
//                    ((AbstractBlock.AbstractBlockState)(Object)this).getBlock().asItem().getDefaultStack()
//            ));
//        }
//    }

}
