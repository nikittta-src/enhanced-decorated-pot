package com.nikittta.pots;

import com.nikittta.pots.registry.RegistryHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;

import static com.nikittta.pots.registry.PotsItems.ENHANCED_POT_BLOCK_ITEM;

public class PotsModClient implements ClientModInitializer {

	public static final String MOD_ID = "pots";

	@Override
	public void onInitializeClient() {
		RegistryHandler.registerBlocks();
		RegistryHandler.registerEntities();
		RegistryHandler.registerItems();
		RegistryHandler.registerRenderingStuff();
		RegistryHandler.registerRecipes();

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content ->
			content.addAfter(Items.DECORATED_POT, ENHANCED_POT_BLOCK_ITEM)
		);

	}

}