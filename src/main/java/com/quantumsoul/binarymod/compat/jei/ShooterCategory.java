package com.quantumsoul.binarymod.compat.jei;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.init.BlockInit;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.quantumsoul.binarymod.util.MachineUtils.L_SHOOTER;

public class ShooterCategory extends UpgradableCategory
{
    public static final ResourceLocation UID = new ResourceLocation(BinaryMod.MOD_ID, "shooter");

    private final IDrawable icon;

    public ShooterCategory(IGuiHelper guiHelper)
    {
        super(guiHelper);
        icon = guiHelper.createDrawableIngredient(new ItemStack(BlockInit.SHOOTER.get()));
    }

    @Override
    protected List<Pair<String, ItemStack>> getContents()
    {
        List<Pair<String, ItemStack>> contents = new ArrayList<>();

        for (int i = 0; i < 4; i++)
            contents.add(new ImmutablePair<>(L_SHOOTER.getInfo(i), null));

        return contents;
    }

    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("block.binarymod.shooter");
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setIngredients(Byte recipe, IIngredients ingredients)
    {}
}
