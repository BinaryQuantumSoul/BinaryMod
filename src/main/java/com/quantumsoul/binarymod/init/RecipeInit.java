package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import com.quantumsoul.binarymod.recipe.SDRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeInit
{
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, BinaryMod.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<DarkWebRecipe>> DARK_WEB_SERIALIZER = RECIPES.register("dark_web", DarkWebRecipe.Serializer::new);
    public static final IRecipeType<DarkWebRecipe> DARK_WEB = IRecipeType.register(BinaryMod.MOD_ID + ":dark_web");

    public static final RegistryObject<IRecipeSerializer<SDRecipe>> SD_RECIPE_SERIALIZER = RECIPES.register("sd", SDRecipe.Serializer::new);
    public static final IRecipeType<SDRecipe> SD = IRecipeType.register(BinaryMod.MOD_ID + ":sd");
}
