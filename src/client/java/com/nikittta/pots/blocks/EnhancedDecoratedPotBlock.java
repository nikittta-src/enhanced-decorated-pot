/*
 * Decompiled with CFR 0.2.0 (FabricMC d28b102d).
 */
package com.nikittta.pots.blocks;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnhancedDecoratedPotBlock
        extends BlockWithEntity
        implements Waterloggable, BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final BooleanProperty CRACKED = Properties.CRACKED;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final Identifier SET_BLOCK_CHANNEL_NAME = new Identifier("pots:set_block");

    public EnhancedDecoratedPotBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false).with(CRACKED, false));
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack items = player.getMainHandStack();
        if (items != ItemStack.EMPTY){
            if (items.getItem() instanceof BlockItem){
                if (canBlockBePlaced(
                        ( (BlockItem)items.getItem() ).getBlock()
                )) {
                    if (world.isClient) {
                        sendSetBlockPacket(
                                ((BlockItem) items.getItem()).getBlock(),
                                pos.up()
                        );
                        if (!player.getAbilities().creativeMode) {
                            items.decrement(1);
                        }
                    }
                    return ActionResult.SUCCESS;
                }
            }
//            else if (items.getItem() instanceof DyeItem){
//                EnhancedDecoratedPotBlockEntity entity = (EnhancedDecoratedPotBlockEntity) world.getBlockEntity(pos);
//                if (entity != null) {
//                    entity.setColor(
//                            PotColor.fromDyeColor(((DyeItem) items.getItem()).getColor())
//                    );
//                    entity.markDirty();
//                    return ActionResult.SUCCESS;
//                } else {
//                    return ActionResult.FAIL;
//                }
//            }
        }

        return ActionResult.PASS;
    }

    private boolean canBlockBePlaced(Block block){
        return block instanceof PlantBlock;
    }

    private void sendSetBlockPacket(Block block, BlockPos pos){
        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(pos);
        data.writeIdentifier(Registries.BLOCK.getId(block));
        ClientPlayNetworking.send(SET_BLOCK_CHANNEL_NAME, data);
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(CRACKED, false);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, CRACKED);
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnhancedDecoratedPotBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof EnhancedDecoratedPotBlockEntity potBlockEntity){
            return List.of(potBlockEntity.asStack());
        }
        return super.getDroppedStacks(state, builder);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        ItemStack itemStack = player.getMainHandStack();
        BlockState blockState = state;
        if (itemStack.isIn(ItemTags.BREAKS_DECORATED_POTS) && !EnchantmentHelper.hasSilkTouch(itemStack)) {
            blockState = state.with(CRACKED, true);
            world.setBlockState(pos, blockState, Block.NO_REDRAW);
        }
        if (world.getBlockState(pos.up()).getBlock() instanceof PlantBlock) {
            world.breakBlock(pos.up(), false, null);
        }
        super.onBreak(world, pos, blockState, player);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState state) {
        if (state.get(CRACKED)) {
            return BlockSoundGroup.DECORATED_POT_SHATTER;
        }
        return BlockSoundGroup.DECORATED_POT;
    }
}

