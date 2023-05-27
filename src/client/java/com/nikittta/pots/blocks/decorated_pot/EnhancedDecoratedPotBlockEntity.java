package com.nikittta.pots.blocks.decorated_pot;

import com.nikittta.pots.blocks.PotColor;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

import static com.nikittta.pots.registry.PotsEntities.ENHANCED_POT_BLOCK_ENTITY;
import static com.nikittta.pots.registry.PotsItems.ENHANCED_POT_BLOCK_ITEM;

public class EnhancedDecoratedPotBlockEntity
        extends BlockEntity {
    public static final String SHARDS_NBT_KEY = "shards";

    public static final String COLOR_NBT_KEY = "pot_color";
    private static final int SHARD_COUNT = 4;

    private final List<Item> shards = Util.make(new ArrayList(4), shards -> {
        shards.add(Items.BRICK);
        shards.add(Items.BRICK);
        shards.add(Items.BRICK);
        shards.add(Items.BRICK);
    });
    private PotColor color = PotColor.ORIGINAL;

    public PotColor getColor(){
        return color;
    }

    public EnhancedDecoratedPotBlockEntity(BlockPos pos, BlockState state) {
        super(ENHANCED_POT_BLOCK_ENTITY, pos, state);
    }

    public static void writeShardsToNbt(List<Item> shards, NbtCompound nbt) {
        NbtList nbtList = new NbtList();
        for (Item item : shards) {
            nbtList.add(NbtString.of(Registries.ITEM.getId(item).toString()));
        }
        nbt.put(SHARDS_NBT_KEY, nbtList);
    }
    public static void writeColorToNbt(PotColor color, NbtCompound nbt){
        nbt.putInt(COLOR_NBT_KEY, color.getId());
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        EnhancedDecoratedPotBlockEntity.writeShardsToNbt(this.shards, nbt);
        EnhancedDecoratedPotBlockEntity.writeColorToNbt(this.color, nbt);
    }

    public static List<Item> getSherdsFromNbt(NbtCompound nbt){
        List<Item> shards = new ArrayList<>(List.of());
        int i = 0, j;
        if (nbt.contains(SHARDS_NBT_KEY, NbtElement.LIST_TYPE)) {
            NbtList nbtList = nbt.getList(SHARDS_NBT_KEY, NbtElement.STRING_TYPE);
            shards.clear();
            i = Math.min(SHARD_COUNT, nbtList.size());
            for (j = 0; j < i; ++j) {
                NbtElement nbtElement = nbtList.get(j);
                if (nbtElement instanceof NbtString nbtString) {
                    shards.add(Registries.ITEM.get(new Identifier(nbtString.asString())));
                    continue;
                }
                shards.add(Items.BRICK);
            }

        }
        j = 4 - i;
        for (int k = 0; k < j; ++k) {
            shards.add(Items.BRICK);
        }
        return shards;
    }

    public static PotColor getColorFromNbt(NbtCompound nbt){
        PotColor color = PotColor.ORIGINAL;
        if (nbt.contains(COLOR_NBT_KEY, NbtElement.INT_TYPE)){
            color = PotColor.byId(nbt.getInt(COLOR_NBT_KEY));
        }
        return color;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.shards.clear();
        this.shards.addAll(getSherdsFromNbt(nbt));

        this.color = getColorFromNbt(nbt);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public ItemStack asStack() {
        ItemStack itemStack = new ItemStack(ENHANCED_POT_BLOCK_ITEM);
        NbtCompound nbtCompound = new NbtCompound();
        EnhancedDecoratedPotBlockEntity.writeShardsToNbt(this.shards, nbtCompound);
        EnhancedDecoratedPotBlockEntity.writeColorToNbt(this.color, nbtCompound);
        BlockItem.setBlockEntityNbt(itemStack, ENHANCED_POT_BLOCK_ENTITY, nbtCompound);
        return itemStack;
    }

    public List<Item> getShards() {
        return this.shards;
    }

    public Direction getHorizontalFacing() {
        return this.getCachedState().get(Properties.HORIZONTAL_FACING);
    }

    public ItemStack getPickStack() {
        ItemStack itemStack = new ItemStack(ENHANCED_POT_BLOCK_ITEM);
        if (this.shards != null && !this.shards.isEmpty()) {
            NbtCompound nbtCompound = new NbtCompound();
            writeShardsToNbt(this.shards, nbtCompound);
            nbtCompound.putInt(COLOR_NBT_KEY, this.color.getId());
            BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);
        }

        return itemStack;
    }

    public void readNbtFromStack(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null) {
            this.readNbt(nbtCompound);
        } else {
            this.shards.clear();
            for (int i = 0; i < 4; ++i) {
                this.shards.add(Items.BRICK);
            }
            this.color = PotColor.ORIGINAL;
        }
    }
}

