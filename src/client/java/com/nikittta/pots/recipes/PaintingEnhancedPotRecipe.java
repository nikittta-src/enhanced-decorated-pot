package com.nikittta.pots.recipes;

import com.nikittta.pots.PotsMod;
import com.nikittta.pots.blocks.decorated_pot.EnhancedDecoratedPotBlockEntity;
import com.nikittta.pots.blocks.PotColor;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static com.nikittta.pots.registry.PotsEntities.ENHANCED_POT_BLOCK_ENTITY;
import static com.nikittta.pots.registry.PotsItems.ENHANCED_POT_BLOCK_ITEM;
import static com.nikittta.pots.registry.PotsRecipes.ENHANCED_POT_PAINTING_RECIPE_SERIALIZER;

public class PaintingEnhancedPotRecipe extends SpecialCraftingRecipe {

    public PaintingEnhancedPotRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        PotsMod.LOGGER.info("start matching");
        if (!this.fits(inventory.getWidth(), inventory.getHeight())) {
            return false;
        }
        int dyeCount = 0, potCount = 0, airCount = 0;
        for (ItemStack itemStack: inventory.getInputStacks()){
            if (itemStack.getItem() instanceof DyeItem)
                dyeCount += 1;
            if (itemStack.isOf(ENHANCED_POT_BLOCK_ITEM))
                potCount += 1;
            if (itemStack.isOf(Items.AIR))
                airCount += 1;
        }
        PotsMod.LOGGER.info("end matching");
        return dyeCount == 1 && potCount == 1 && airCount == 7;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        NbtCompound potNbt = new NbtCompound();
        PotColor color = PotColor.ORIGINAL;
        for (int i = 0; i < 9; i++){
            if (inventory.getStack(i).isOf(ENHANCED_POT_BLOCK_ITEM))
                if (BlockItem.getBlockEntityNbt(inventory.getStack(i)) != null){
                    potNbt = BlockItem.getBlockEntityNbt(inventory.getStack(i)).copy();
                }
            if (inventory.getStack(i).getItem() instanceof DyeItem)
                color = PotColor.fromDyeColor(
                        ((DyeItem) inventory.getStack(i).getItem()).getColor()
                );
        }
        ItemStack itemStack = ENHANCED_POT_BLOCK_ITEM.getDefaultStack();
        EnhancedDecoratedPotBlockEntity.writeColorToNbt(color, potNbt);
        BlockItem.setBlockEntityNbt(itemStack, ENHANCED_POT_BLOCK_ENTITY, potNbt);
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width == 3 && height == 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ENHANCED_POT_PAINTING_RECIPE_SERIALIZER;
    }
}
