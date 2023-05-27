package com.nikittta.pots.recipes;

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
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

import static com.nikittta.pots.registry.PotsEntities.ENHANCED_POT_BLOCK_ENTITY;
import static com.nikittta.pots.registry.PotsItems.ENHANCED_POT_BLOCK_ITEM;
import static com.nikittta.pots.registry.PotsRecipes.ENHANCED_POT_RECIPE_SERIALIZER;

public class CraftingEnhancedPotRecipe extends SpecialCraftingRecipe {

    private final int[] dyeSlots = new int[]{0,2,6,8};

    public CraftingEnhancedPotRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }


    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        if (!this.fits(inventory.getWidth(), inventory.getHeight())) {
            return false;
        }
        block3: for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            switch (i) {
                case 1, 3, 5, 7 -> {
                    if (itemStack.isIn(ItemTags.DECORATED_POT_INGREDIENTS)) continue block3;
                    return false;
                }
                case 0, 2, 6, 8 -> {
                    if (itemStack.getItem() instanceof DyeItem && isTheOnlyDyeItem(i, inventory) || itemStack.isOf(Items.AIR)) continue block3;
                    return false;
                }
                default -> {
                    if (itemStack.isOf(Items.AIR)) continue block3;
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isTheOnlyDyeItem(int j, RecipeInputInventory craftingInventory){
        for (int i: dyeSlots){
            if (i != j && craftingInventory.getStack(i).getItem() instanceof DyeItem){
                return false;
            }
        }
        return true;
    }

    private PotColor getDyeColor(RecipeInputInventory craftingInventory){
        for (int i: dyeSlots){
            if (craftingInventory.getStack(i).getItem() instanceof DyeItem){
                return PotColor.fromDyeColor(
                        ((DyeItem) craftingInventory.getStack(i).getItem()).getColor()
                );
            }
        }
        return PotColor.ORIGINAL;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        ItemStack itemStack = ENHANCED_POT_BLOCK_ITEM.getDefaultStack();
        NbtCompound nbtCompound = new NbtCompound();
        EnhancedDecoratedPotBlockEntity.writeShardsToNbt(List.of(inventory.getStack(1).getItem(), inventory.getStack(3).getItem(), inventory.getStack(5).getItem(), inventory.getStack(7).getItem()), nbtCompound);
        EnhancedDecoratedPotBlockEntity.writeColorToNbt(getDyeColor(inventory), nbtCompound);
        BlockItem.setBlockEntityNbt(itemStack, ENHANCED_POT_BLOCK_ENTITY, nbtCompound);
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width == 3 && height == 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ENHANCED_POT_RECIPE_SERIALIZER;
    }
}
