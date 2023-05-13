package com.nikittta.pots.items;

import com.nikittta.pots.blocks.EnhancedDecoratedPotBlockEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static com.nikittta.pots.registry.PotsBlocks.ENHANCED_POT_BLOCK;

public class EnhancedDecoratedPotItemDynamicRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        EnhancedDecoratedPotBlockEntity entity = new EnhancedDecoratedPotBlockEntity(
                BlockPos.ORIGIN, ENHANCED_POT_BLOCK.getDefaultState()
        );
        entity.readNbtFromStack(stack);
        MinecraftClient.getInstance().getBlockEntityRenderDispatcher().renderEntity(
                entity,
                matrices,
                vertexConsumers,
                light,
                overlay
        );
    }
}
