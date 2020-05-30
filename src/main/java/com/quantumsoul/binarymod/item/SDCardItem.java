package com.quantumsoul.binarymod.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class SDCardItem extends Item
{
    private final SDSize sdsize;

    public SDCardItem(Item.Properties properties, SDSize size)
    {
        super(properties);
        this.sdsize = size;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return new ContainerWrapper(getInventorySize());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        IntNBT nbt = (IntNBT) stack.getOrCreateTag().get("storage");
        if(nbt != null)
            tooltip.add(new StringTextComponent(TextFormatting.GRAY + I18n.format("tooltip.binarymod.sd", nbt.getInt())));
    }

    public int getInventorySize()
    {
        return sdsize.size;
    }

    public int getOrder()
    {
        return sdsize.size / 9 - 1;
    }

    public enum SDSize
    {
        SMALL(9),
        MEDIUM(18),
        BIG(27),
        ULTRA(36);

        private final int size;

        SDSize(int slots)
        {
            size = slots;
        }
    }
}
