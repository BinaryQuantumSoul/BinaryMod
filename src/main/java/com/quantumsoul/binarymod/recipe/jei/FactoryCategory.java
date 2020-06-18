package com.quantumsoul.binarymod.recipe.jei;

import com.quantumsoul.binarymod.BinaryMod;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class FactoryCategory implements IRecipeCategory<Byte>
{
    private final IDrawableStatic background;
    private final List<Pair<String, ItemStack>> content;

    public FactoryCategory(IGuiHelper guiHelper)
    {
        content = getContents();
        background = guiHelper.createDrawable(new ResourceLocation(BinaryMod.MOD_ID, "textures/gui/jei.png"), 0, 128, 175, 20 * content.size());
    }

    protected abstract List<Pair<String, ItemStack>> getContents();

    @Nonnull
    @Override
    public Class<Byte> getRecipeClass()
    {
        return Byte.class;
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull Byte recipe, @Nonnull IIngredients ingredients)
    {
        FontRenderer font =  Minecraft.getInstance().fontRenderer;
        int dist = 8 + font.getStringWidth(I18n.format("tooltip.binarymod.level", 0, 0));

        for(int i = 0; i < content.size(); i++)
        {
            Pair<String, ItemStack> pair = content.get(i);
            String string = pair.getKey();
            ItemStack stack = pair.getValue();

            if (stack != null)
            {
                recipeLayout.getItemStacks().init(i, false, dist + (string != null ? font.getStringWidth(" -> " + string) : 0), 1 + 20 * i);
                recipeLayout.getItemStacks().set(i, stack);
            }
        }
    }

    @Override
    public void draw(Byte recipe, double mouseX, double mouseY)
    {
        for(int i = 0; i < content.size(); i++)
        {
            String string = content.get(i).getKey();
            Minecraft.getInstance().fontRenderer.drawString(I18n.format("tooltip.binarymod.level", i + 1, content.size()) + (string != null ? " -> " + string : ""), 7, 6 + 20 * i, 0x3F3F3F);
        }
    }
}
