package com.nikittta.pots.registry;

import com.nikittta.pots.recipes.CraftingEnhancedPotRecipe;
import com.nikittta.pots.recipes.PaintingEnhancedPotRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;

public class PotsRecipes {

    public static final RecipeSerializer<CraftingEnhancedPotRecipe> ENHANCED_POT_RECIPE_SERIALIZER = new SpecialRecipeSerializer<>(CraftingEnhancedPotRecipe::new);
    public static final RecipeSerializer<PaintingEnhancedPotRecipe> ENHANCED_POT_PAINTING_RECIPE_SERIALIZER = new SpecialRecipeSerializer<>(PaintingEnhancedPotRecipe::new);


}
