package com.turtywurty.tutorialmod.recipes;

import javax.annotation.Nonnull;

import com.turtywurty.tutorialmod.TutorialMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IExampleRecipe extends IRecipe<RecipeWrapper> {

	ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(TutorialMod.MOD_ID, "example");
	
	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getValue(RECIPE_TYPE_ID).get();
	}
	
	@Override
	default boolean canFit(int width, int height) {
		return false;
	}
	
	Ingredient getInput();
}
