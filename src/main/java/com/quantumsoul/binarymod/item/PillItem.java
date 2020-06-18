package com.quantumsoul.binarymod.item;

import com.quantumsoul.binarymod.init.DimensionInit;
import com.quantumsoul.binarymod.world.dimension.BinaryDimension;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import javax.annotation.Nullable;
import java.util.List;

public class PillItem extends Item
{
    public enum Pill
    {
        RED,
        BLUE
    }

    private final Pill mPill;

    public PillItem(Pill p, Properties properties)
    {
        super(properties);
        mPill = p;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        for (int i = 0; i < 4; i++)
            tooltip.add(new StringTextComponent((mPill == Pill.RED ? TextFormatting.RED : TextFormatting.BLUE) + I18n.format("tooltip.binarymod.pill_" + mPill.name().toLowerCase() + i)));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving)
    {
        DimensionType dim = entityLiving.dimension;
        if (this.mPill == Pill.RED && dim != DimensionInit.DIM_BINARY_TYPE)
        {
            if (entityLiving instanceof ServerPlayerEntity)
                BinaryDimension.teleport((ServerPlayerEntity) entityLiving);
        }
        else if (this.mPill == Pill.BLUE && dim == DimensionInit.DIM_BINARY_TYPE)
        {
            if (entityLiving instanceof ServerPlayerEntity)
                BinaryDimension.teleportBack((ServerPlayerEntity) entityLiving);
        }
        else
            return stack;

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
