package com.nikittta.pots.mixin.client;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlock;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlantBlock.class)
public class FlowerClientMixin {

	@Inject(at = @At("HEAD"), method = "canPlaceAt", cancellable = true)
	private void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (world.getBlockState(pos.down()).getBlock() instanceof EnhancedDecoratedPotBlock)
			if (!isForbiddenPlant(
					(PlantBlock)((Object)this)
			)) {
				cir.setReturnValue(true);
			}
	}

	private boolean isForbiddenPlant(PlantBlock plant){
		return plant instanceof CropBlock ||
				plant instanceof PitcherCropBlock ||
				plant instanceof NetherWartBlock ||
				plant instanceof TallSeagrassBlock ||
				plant instanceof SeagrassBlock ||
				plant instanceof SeaPickleBlock ||
				plant instanceof SproutsBlock ||
				plant instanceof FlowerbedBlock ||
				plant instanceof LilyPadBlock;

	}

}