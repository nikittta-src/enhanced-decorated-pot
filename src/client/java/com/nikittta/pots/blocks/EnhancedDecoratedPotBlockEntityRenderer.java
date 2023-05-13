/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package com.nikittta.pots.blocks;

import com.nikittta.pots.PotsModClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class EnhancedDecoratedPotBlockEntityRenderer
        implements BlockEntityRenderer<EnhancedDecoratedPotBlockEntity> {
    private static final String NECK = "neck";
    private static final String FRONT = "front";
    private static final String BACK = "back";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private final ModelPart neck;
    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart left;
    private final ModelPart right;
    private final ModelPart top;
    private final ModelPart bottom;

    public final Map<Item, String> ITEM_TO_SPRITE_BASE_PATH = Map.ofEntries(
            Map.entry(Items.BRICK, "pots:block/enhanced_pot/decorated_pot_side"),
            Map.entry(Items.ANGLER_POTTERY_SHERD, "pots:block/enhanced_pot/angler_pottery_pattern"),
            Map.entry(Items.ARCHER_POTTERY_SHERD, "pots:block/enhanced_pot/archer_pottery_pattern"),
            Map.entry(Items.ARMS_UP_POTTERY_SHERD, "pots:block/enhanced_pot/arms_up_pottery_pattern"),
            Map.entry(Items.BLADE_POTTERY_SHERD, "pots:block/enhanced_pot/blade_pottery_pattern"),
            Map.entry(Items.BREWER_POTTERY_SHERD, "pots:block/enhanced_pot/brewer_pottery_pattern"),
            Map.entry(Items.BURN_POTTERY_SHERD, "pots:block/enhanced_pot/burn_pottery_pattern"),
            Map.entry(Items.DANGER_POTTERY_SHERD, "pots:block/enhanced_pot/danger_pottery_pattern"),
            Map.entry(Items.EXPLORER_POTTERY_SHERD, "pots:block/enhanced_pot/explorer_pottery_pattern"),
            Map.entry(Items.FRIEND_POTTERY_SHERD, "pots:block/enhanced_pot/friend_pottery_pattern"),
            Map.entry(Items.HEART_POTTERY_SHERD, "pots:block/enhanced_pot/heart_pottery_pattern"),
            Map.entry(Items.HEARTBREAK_POTTERY_SHERD, "pots:block/enhanced_pot/heartbreak_pottery_pattern"),
            Map.entry(Items.HOWL_POTTERY_SHERD, "pots:block/enhanced_pot/howl_pottery_pattern"),
            Map.entry(Items.MINER_POTTERY_SHERD, "pots:block/enhanced_pot/miner_pottery_pattern"),
            Map.entry(Items.MOURNER_POTTERY_SHERD, "pots:block/enhanced_pot/mourner_pottery_pattern"),
            Map.entry(Items.PLENTY_POTTERY_SHERD, "pots:block/enhanced_pot/plenty_pottery_pattern"),
            Map.entry(Items.PRIZE_POTTERY_SHERD, "pots:block/enhanced_pot/prize_pottery_pattern"),
            Map.entry(Items.SHEAF_POTTERY_SHERD, "pots:block/enhanced_pot/sheaf_pottery_pattern"),
            Map.entry(Items.SHELTER_POTTERY_SHERD, "pots:block/enhanced_pot/shelter_pottery_pattern"),
            Map.entry(Items.SKULL_POTTERY_SHERD, "pots:block/enhanced_pot/skull_pottery_pattern"),
            Map.entry(Items.SNORT_POTTERY_SHERD, "pots:block/enhanced_pot/snort_pottery_pattern")
            );




    public EnhancedDecoratedPotBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        ModelPart modelPart = context.getLayerModelPart(ENHANCED_POT_BASE_MODEL_LAYER);
        this.neck = modelPart.getChild(NECK);
        this.top = modelPart.getChild(TOP);
        this.bottom = modelPart.getChild(BOTTOM);
        ModelPart modelPart2 = context.getLayerModelPart(ENHANCED_POT_SIDES_MODEL_LAYER);
        this.front = modelPart2.getChild(FRONT);
        this.back = modelPart2.getChild(BACK);
        this.left = modelPart2.getChild(LEFT);
        this.right = modelPart2.getChild(RIGHT);
    }



    public static TexturedModelData getTopBottomNeckTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(0.2f);
        Dilation dilation2 = new Dilation(-0.1f);
        modelPartData.addChild(EntityModelPartNames.NECK, ModelPartBuilder.create().uv(0, 0).cuboid(4.0f, 17.0f, 4.0f, 8.0f, 3.0f, 8.0f, dilation2).uv(0, 5).cuboid(5.0f, 20.0f, 5.0f, 6.0f, 1.0f, 6.0f, dilation), ModelTransform.of(0.0f, 37.0f, 16.0f, (float)Math.PI, 0.0f, 0.0f));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(-14, 13).cuboid(0.0f, 0.0f, 0.0f, 14.0f, 0.0f, 14.0f);
        modelPartData.addChild(TOP, modelPartBuilder, ModelTransform.of(1.0f, 16.0f, 1.0f, 0.0f, 0.0f, 0.0f));
        modelPartData.addChild(BOTTOM, modelPartBuilder, ModelTransform.of(1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f));
        return TexturedModelData.of(modelData, 32, 32);
    }

    public static TexturedModelData getSidesTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(1, 0).cuboid(0.0f, 0.0f, 0.0f, 14.0f, 16.0f, 0.0f, EnumSet.of(Direction.NORTH));
        modelPartData.addChild(BACK, modelPartBuilder, ModelTransform.of(15.0f, 16.0f, 1.0f, 0.0f, 0.0f, (float)Math.PI));
        modelPartData.addChild(LEFT, modelPartBuilder, ModelTransform.of(1.0f, 16.0f, 1.0f, 0.0f, -1.5707964f, (float)Math.PI));
        modelPartData.addChild(RIGHT, modelPartBuilder, ModelTransform.of(15.0f, 16.0f, 15.0f, 0.0f, 1.5707964f, (float)Math.PI));
        modelPartData.addChild(FRONT, modelPartBuilder, ModelTransform.of(1.0f, 16.0f, 15.0f, (float)Math.PI, 0.0f, 0.0f));
        return TexturedModelData.of(modelData, 16, 16);
    }

    private SpriteIdentifier getBaseTexture(){
        return new SpriteIdentifier(
                SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
                new Identifier("pots:block/enhanced_pot/decorated_pot_base")
            );
    }

    private SpriteIdentifier getTextureIdFromShard(Item item) {
        String basePath = ITEM_TO_SPRITE_BASE_PATH.get(item);
        if (basePath == null) basePath = ITEM_TO_SPRITE_BASE_PATH.get(Items.BRICK);
        return new SpriteIdentifier(
                SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
                new Identifier(basePath)
        );
    }

    @Override
    public void render(EnhancedDecoratedPotBlockEntity potBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        matrixStack.push();
        float[] colorComponents = potBlockEntity.getColor().getColorComponents();
        Direction direction = potBlockEntity.getHorizontalFacing();
        matrixStack.translate(0.5, 0.0, 0.5);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - direction.asRotation()));
        matrixStack.translate(-0.5, 0.0, -0.5);
        VertexConsumer vertexConsumer = getBaseTexture().getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        this.neck.render(matrixStack, vertexConsumer, i, j, colorComponents[0], colorComponents[1], colorComponents[2], 1f);
        this.top.render(matrixStack, vertexConsumer, i, j, colorComponents[0], colorComponents[1], colorComponents[2], 1f);
        this.bottom.render(matrixStack, vertexConsumer, i, j, colorComponents[0], colorComponents[1], colorComponents[2], 1f);
        List<Item> list = potBlockEntity.getShards();
        this.renderDecoratedSide(this.front, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromShard(list.get(3)), colorComponents);
        this.renderDecoratedSide(this.back, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromShard(list.get(0)), colorComponents);
        this.renderDecoratedSide(this.left, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromShard(list.get(1)), colorComponents);
        this.renderDecoratedSide(this.right, matrixStack, vertexConsumerProvider, i, j, getTextureIdFromShard(list.get(2)), colorComponents);
        matrixStack.pop();
    }

    private void renderDecoratedSide(ModelPart part, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, @Nullable SpriteIdentifier textureId, float[] colorComponents) {
        if (textureId != null) {
            part.render(matrices, textureId.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid), light, overlay, colorComponents[0], colorComponents[1], colorComponents[2], 1f);
        }
    }

    public static final EntityModelLayer ENHANCED_POT_BASE_MODEL_LAYER = new EntityModelLayer(
            new Identifier(PotsModClient.MOD_ID, "enhanced_pot_base"),
            "main"
    );

    public static final EntityModelLayer ENHANCED_POT_SIDES_MODEL_LAYER = new EntityModelLayer(
            new Identifier(PotsModClient.MOD_ID, "enhanced_pot_sides"),
            "main"
    );

}

