package com.nikittta.pots.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;

import static com.nikittta.pots.registry.PotsBlocks.ENHANCED_POT_BLOCK;

public class PotsItems {

    public static final BlockItem ENHANCED_POT_BLOCK_ITEM =
            new BlockItem(ENHANCED_POT_BLOCK, new FabricItemSettings().maxCount(1));

}
