package com.nikittta.pots.registry;

import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlockEntityRenderer;
import com.nikittta.pots.items.EnhancedDecoratedPotItemDynamicRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.nikittta.pots.PotsModClient.MOD_ID;
import static com.nikittta.pots.registry.PotsBlocks.ENHANCED_POT_BLOCK;
import static com.nikittta.pots.registry.PotsEntities.ENHANCED_POT_BLOCK_ENTITY;
import static com.nikittta.pots.registry.PotsItems.ENHANCED_POT_BLOCK_ITEM;
import static com.nikittta.pots.registry.PotsRecipes.ENHANCED_POT_PAINTING_RECIPE_SERIALIZER;
import static com.nikittta.pots.registry.PotsRecipes.ENHANCED_POT_RECIPE_SERIALIZER;

public class RegistryHandler {

    public static void registerItems(){
        Registry.register(
                Registries.ITEM,
                new Identifier(MOD_ID, "enhanced_pot"),
                ENHANCED_POT_BLOCK_ITEM
        );
    }

    public static void registerBlocks(){
        Registry.register(
                Registries.BLOCK,
                new Identifier(MOD_ID, "enhanced_pot"),
                ENHANCED_POT_BLOCK
        );
    }

    public static void registerEntities(){
        Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "enhanced_pot"),
                ENHANCED_POT_BLOCK_ENTITY
        );
    }

    public static void registerRenderingStuff(){
        BlockEntityRendererFactories.register(ENHANCED_POT_BLOCK_ENTITY, EnhancedDecoratedPotBlockEntityRenderer::new);
        BuiltinItemRendererRegistry.INSTANCE.register(
                ENHANCED_POT_BLOCK_ITEM, new EnhancedDecoratedPotItemDynamicRenderer()
        );

        EntityModelLayerRegistry.registerModelLayer(
                EnhancedDecoratedPotBlockEntityRenderer.ENHANCED_POT_BASE_MODEL_LAYER,
                EnhancedDecoratedPotBlockEntityRenderer::getTopBottomNeckTexturedModelData
        );

        EntityModelLayerRegistry.registerModelLayer(
                EnhancedDecoratedPotBlockEntityRenderer.ENHANCED_POT_SIDES_MODEL_LAYER,
                EnhancedDecoratedPotBlockEntityRenderer::getSidesTexturedModelData
        );
    }

    public static void registerRecipes(){
        Registry.register(
                Registries.RECIPE_SERIALIZER,
                new Identifier(MOD_ID, "crafting_enhanced_pot"),
                ENHANCED_POT_RECIPE_SERIALIZER
        );
        Registry.register(
                Registries.RECIPE_SERIALIZER,
                new Identifier(MOD_ID, "painting_enhanced_pot"),
                ENHANCED_POT_PAINTING_RECIPE_SERIALIZER
        );
    }

}
