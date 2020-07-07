package com.quantumsoul.binarymod.compat.jei;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.init.RecipeInit;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import com.quantumsoul.binarymod.tileentity.container.BlockProgContainer;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@JeiPlugin
public class BinaryModJEIPlugin implements IModPlugin
{
    private static final Method getRecipes = ObfuscationReflectionHelper.findMethod(RecipeManager.class, "func_215366_a", IRecipeType.class);

    @Nonnull
    @Override
    public ResourceLocation getPluginUid()
    {
        return new ResourceLocation(BinaryMod.MOD_ID, "plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration)
    {
        registration.useNbtForSubtypes(ItemInit.SOURCE.get());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(
                new DarkWebCategory(registration.getJeiHelpers().getGuiHelper()),
                new BlockProgCategory(registration.getJeiHelpers().getGuiHelper()),

                new FeederCategory(registration.getJeiHelpers().getGuiHelper()),
                new HealerCategory(registration.getJeiHelpers().getGuiHelper()),
                new ShooterCategory(registration.getJeiHelpers().getGuiHelper()),
                new RepairerCategory(registration.getJeiHelpers().getGuiHelper()),
                new BitcoinCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registration)
    {
        registration.addRecipes(Collections.singleton((byte) 0), FeederCategory.UID);
        registration.addRecipes(Collections.singleton((byte) 0), HealerCategory.UID);
        registration.addRecipes(Collections.singleton((byte) 0), ShooterCategory.UID);
        registration.addRecipes(Collections.singleton((byte) 0), RepairerCategory.UID);
        registration.addRecipes(Collections.singleton((byte) 0), BitcoinCategory.UID);

        try
        {
            @SuppressWarnings({"unchecked", "ConstantConditions"})
            Collection<IRecipe<IInventory>> darkWebRecipes = ((Map<ResourceLocation, IRecipe<IInventory>>)getRecipes.invoke(Minecraft.getInstance().world.getRecipeManager(), RecipeInit.DARK_WEB)).values();

            registration.addRecipes(darkWebRecipes, DarkWebCategory.UID);
            registration.addRecipes(darkWebRecipes.stream().filter(r -> ((DarkWebRecipe) r).isSource()).collect(Collectors.toList()), BlockProgCategory.UID);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        registration.addIngredientInfo(new ItemStack(ItemInit.UPGRADE.get()), VanillaTypes.ITEM, I18n.format("info.binarymod.upgrade"));
        registration.addIngredientInfo(new ItemStack(ItemInit.ANTIVIRUS.get()), VanillaTypes.ITEM, I18n.format("info.binarymod.antivirus"));
    }

    @Override
    public void registerRecipeTransferHandlers(@Nonnull IRecipeTransferRegistration registration)
    {
        registration.addRecipeTransferHandler(BlockProgContainer.class, BlockProgCategory.UID, 0, 2, 3, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(BlockInit.COMPUTER.get()), DarkWebCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ItemInit.DARK_NET.get()), DarkWebCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.BLOCK_PROGRAMMER.get()), BlockProgCategory.UID);

        registration.addRecipeCatalyst(new ItemStack(BlockInit.FEEDER.get()), FeederCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.HEALER.get()), HealerCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.SHOOTER.get()), ShooterCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.REPAIRER.get()), RepairerCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(BlockInit.BITCOIN_MINER.get()), BitcoinCategory.UID);
    }
}
