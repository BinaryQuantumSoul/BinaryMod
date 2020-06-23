package com.quantumsoul.binarymod.recipe.jei;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.ItemInit;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RepairerCategory extends UpgradableCategory
{
    public static final ResourceLocation UID = new ResourceLocation(BinaryMod.MOD_ID, "repairer");

    private final IDrawable icon;

    public RepairerCategory(IGuiHelper guiHelper)
    {
        super(guiHelper);
        icon = guiHelper.createDrawableIngredient(new ItemStack(ItemInit.REPAIR.get()));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return I18n.format("block.binarymod.repairer");
    }

    @Nonnull
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(@Nonnull Byte recipe, @Nonnull IIngredients ingredients)
    {}

    @Override
    protected List<Pair<String, ItemStack>> getContents()
    {
        List<Pair<String, ItemStack>> contents = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            contents.add(new ImmutablePair<>(I18n.format("jei.binarymod.repairer", (int) (64D * Math.pow(4, i))), null));

        return contents;
    }
}
