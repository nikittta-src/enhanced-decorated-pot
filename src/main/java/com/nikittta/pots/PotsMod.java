package com.nikittta.pots;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotsMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("pots");

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(new Identifier("pots:set_block"), (server, player, handler, buf, sender) -> {
			BlockPos pos = buf.readBlockPos(); // reads must be done in the same order
			PlantBlock blockToSet = (PlantBlock) Registries.BLOCK.get(buf.readIdentifier()); // reading using the identifier

			server.execute(() -> {
				LOGGER.info(blockToSet.getClass().toString());
				if (player.getWorld().getBlockState(pos.up()).getBlock() instanceof TallPlantBlock && player.getWorld().getBlockState(pos).getBlock() instanceof TallPlantBlock){
					player.getWorld().setBlockState(pos.up(), Blocks.AIR.getDefaultState());
				}
				if (blockToSet instanceof TallPlantBlock){
					player.getWorld().setBlockState(pos, blockToSet.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER));
					player.getWorld().setBlockState(pos.up(), blockToSet.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
				} else {
					player.getWorld().setBlockState(pos, blockToSet.getDefaultState());
				}
			});
		});

	}
}
