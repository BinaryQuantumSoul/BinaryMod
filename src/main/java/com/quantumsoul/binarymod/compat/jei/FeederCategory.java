package com.quantumsoul.binarymod.compat.jei;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.ItemInit;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class FeederCategory extends UpgradableCategory
{
    public static final ResourceLocation UID = new ResourceLocation(BinaryMod.MOD_ID, "feeder");

    private final IDrawable icon;

    public FeederCategory(IGuiHelper guiHelper)
    {
        super(guiHelper);
        icon = guiHelper.createDrawableIngredient(new ItemStack(Items.COOKIE));
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
        return I18n.format("block.binarymod.feeder");
    }

    @Nonnull
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(@Nonnull Byte recipe, @Nonnull IIngredients ingredients)
    {
        List<ItemStack> outputs = new ArrayList<>();
        outputs.add(new ItemStack(Items.COOKIE));
        outputs.add(new ItemStack(ItemInit.ONION.get()));

        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    protected List<Pair<String, ItemStack>> getContents()
    {
        List<Pair<String, ItemStack>> contents = new ArrayList<>();
        contents.add(new ImmutablePair<>(null, new ItemStack(Items.COOKIE)));
        contents.add(new ImmutablePair<>(null, new ItemStack(ItemInit.ONION.get())));

        return contents;
    }
}