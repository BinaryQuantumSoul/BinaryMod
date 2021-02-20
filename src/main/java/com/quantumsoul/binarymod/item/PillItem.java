package com.quantumsoul.binarymod.item;

import com.quantumsoul.binarymod.init.GenerationInit;
import com.quantumsoul.binarymod.util.WorldUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

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
        RegistryKey<World> dim = entityLiving.world.getDimensionKey();
        if (this.mPill == Pill.RED && dim != GenerationInit.BINARY_DIMENSION)
        {
            if (entityLiving instanceof ServerPlayerEntity)
                WorldUtils.teleportToBinDim((ServerPlayerEntity) entityLiving);
        }
        else if (this.mPill == Pill.BLUE && dim == GenerationInit.BINARY_DIMENSION)
        {
            if (entityLiving instanceof ServerPlayerEntity)
                WorldUtils.teleportFromBinDim((ServerPlayerEntity) entityLiving);
        }
        else
            return stack;

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
}
