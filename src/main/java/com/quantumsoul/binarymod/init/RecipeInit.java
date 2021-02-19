package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import com.quantumsoul.binarymod.recipe.SdIngredient;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeInit
{
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BinaryMod.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<DarkWebRecipe>> DARK_WEB_SERIALIZER = RECIPES.register("dark_web", DarkWebRecipe.Serializer::new);
    public static final IRecipeType<DarkWebRecipe> DARK_WEB = IRecipeType.register(BinaryMod.MOD_ID + ":dark_web");

    public static void initIngredients()
    {
        CraftingHelper.register(new ResourceLocation(BinaryMod.MOD_ID, "sd"), SdIngredient.Serializer.INSTANCE_);
    }
}
