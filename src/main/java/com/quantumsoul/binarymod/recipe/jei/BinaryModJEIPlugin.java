package com.quantumsoul.binarymod.recipe.jei;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.RecipeInit;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@JeiPlugin
public class BinaryModJEIPlugin implements IModPlugin
{
    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(BinaryMod.MOD_ID, "plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(
                new DarkWebCategory(registration.getJeiHelpers().getGuiHelper()),
                new BlockProgCategory(registration.getJeiHelpers().getGuiHelper()),

                new FeederCategory(registration.getJeiHelpers().getGuiHelper()),
                new HealerCategory(registration.getJeiHelpers().getGuiHelper()),
                new RepairerCategory(registration.getJeiHelpers().getGuiHelper()),
                new BitcoinCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration)
    {
        registration.addRecipes(Collections.singleton((byte) 0), FeederCategory.UID);
        registration.addRecipes(Collections.singleton((byte) 0), HealerCategory.UID);
        registration.addRecipes(Collections.singleton((byte) 0), RepairerCategory.UID);
        registration.addRecipes(Collections.singleton((byte) 0), BitcoinCategory.UID);

        try
        {
            Method getRecipes = RecipeManager.class.getDeclaredMethod("getRecipes", IRecipeType.class);
            getRecipes.setAccessible(true);
            @SuppressWarnings({"unchecked", "ConstantConditions"})
            Collection<IRecipe<IInventory>> darkWebRecipes = ((Map<ResourceLocation, IRecipe<IInventory>>)getRecipes.invoke(Minecraft.getInstance().world.getRecipeManager(), RecipeInit.DARK_WEB)).values();

            registration.addRecipes(darkWebRecipes, DarkWebCategory.UID);
            registration.addRecipes(darkWebRecipes.stream().filter(r -> ((DarkWebRecipe) r).isSource()).collect(Collectors.toList()), BlockProgCategory.UID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(BlockInit.COMPUTER.get()), DarkWebCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.BLOCK_PROGRAMMER.get()), BlockProgCategory.UID);

        registration.addRecipeCatalyst(new ItemStack(BlockInit.FEEDER.get()), FeederCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.HEALER.get()), HealerCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.REPAIRER.get()), RepairerCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.BITCOIN_MINER.get()), BitcoinCategory.UID);
    }
}
