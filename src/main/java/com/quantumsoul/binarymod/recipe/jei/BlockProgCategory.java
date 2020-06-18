package com.quantumsoul.binarymod.recipe.jei;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.recipe.DarkWebRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.quantumsoul.binarymod.item.SourceItem.getSourceItem;

public class BlockProgCategory implements IRecipeCategory<DarkWebRecipe>
{
    public static final ResourceLocation UID = new ResourceLocation(BinaryMod.MOD_ID, "block_programmer");

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final IDrawable loading;

    public BlockProgCategory(IGuiHelper guiHelper)
    {
        background = guiHelper.createDrawable(new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/jei.png"), 0, 24, 157, 63);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemInit.SOURCE.get()));

        IDrawableStatic load = guiHelper.createDrawable(new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/jei.png"), 2, 87, 52, 8);
        loading = guiHelper.createAnimatedDrawable(load, 400, IDrawableAnimated.StartDirection.LEFT, false);
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
        return I18n.format("gui.binarymod.block_programmer");
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
        List<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(ItemInit.CODE.get()));
        inputs.add(recipe.getRecipeOutput());

        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setOutput(VanillaTypes.ITEM, Objects.requireNonNull(getSourceItem(recipe.getRecipeOutput())));
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull DarkWebRecipe recipe, @Nonnull IIngredients ingredients)
    {
        recipeLayout.getItemStacks().init(0, true, 1, 7);
        recipeLayout.getItemStacks().set(0, recipe.getRecipeOutput());

        recipeLayout.getItemStacks().init(1, true, 1, 37);
        recipeLayout.getItemStacks().set(1, new ItemStack(ItemInit.CODE.get()));

        recipeLayout.getItemStacks().init(2, false, 138, 24);
        recipeLayout.getItemStacks().set(2, getSourceItem(recipe.getRecipeOutput()));
    }

    @Override
    public void draw(@Nonnull DarkWebRecipe recipe, double mouseX, double mouseY)
    {
        Minecraft.getInstance().fontRenderer.drawString("> Loading", 52, 18, 0x4CFF00);
        loading.draw(50, 30);
    }
}
