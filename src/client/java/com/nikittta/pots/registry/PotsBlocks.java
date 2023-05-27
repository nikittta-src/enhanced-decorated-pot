package com.nikittta.pots.registry;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;

public class PotsBlocks {

    public static final Block ENHANCED_POT_BLOCK =
            new EnhancedDecoratedPotBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_RED).strength(0.0F, 0.0F).pistonBehavior(PistonBehavior.DESTROY).nonOpaque());


}
