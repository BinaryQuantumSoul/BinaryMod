package com.quantumsoul.binarymod.compat.jei;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static com.quantumsoul.binarymod.util.BitcoinUtils.*;

public class DarkWebCategory implements IRecipeCategory<DarkWebRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(BinaryMod.MOD_ID, "dark_web");

    private final IDrawableStatic background;
    private final IDrawable icon;

    public DarkWebCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/jei.png"), 0, 0, 79, 24);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemInit.DARK_NET.get()));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public Class<DarkWebRecipe> getRecipeClass()
    {
        return DarkWebRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return I18n.format("gui.binarymod.computer_dark_net");
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(@Nonnull DarkWebRecipe recipe, @Nonnull IIngredients ingredients)
    {
        ingredients.setInputs(VanillaTypes.ITEM, BTC_STACKS);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull DarkWebRecipe recipe, @Nonnull IIngredients ingredients)
    {
        recipeLayout.getItemStacks().init(0, false, 3, 3);
        recipeLayout.getItemStacks().set(0, recipe.getRecipeOutput());

        recipeLayout.getItemStacks().init(1, true, 58, 3);
        recipeLayout.getItemStacks().set(1, getBitcoinStack(recipe.getPrice()));
    }

    @Override
    public void draw(@Nonnull DarkWebRecipe recipe, double mouseX, double mouseY)
    {
        Minecraft.getInstance().fontRenderer.drawString(getBitcoinString(recipe.getPrice()).substring(0, 4), 39, 9, 0x4CFF00);
    }
}
