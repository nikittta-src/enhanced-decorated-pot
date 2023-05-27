package com.nikittta.pots.registry;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

import static com.nikittta.pots.registry.PotsBlocks.ENHANCED_POT_BLOCK;

public class PotsEntities {

    public static final BlockEntityType<EnhancedDecoratedPotBlockEntity> ENHANCED_POT_BLOCK_ENTITY =
            FabricBlockEntityTypeBuilder.create(EnhancedDecoratedPotBlockEntity::new, ENHANCED_POT_BLOCK).build();

}
