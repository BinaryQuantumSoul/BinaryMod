package com.quantumsoul.binarymod.item;

import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SourceItem extends Item
{
    public SourceItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public ITextComponent getDisplayName(@Nonnull ItemStack stack)
    {
        ItemStack result = getSourceItem(stack);
        return result != null ? result.getDisplayName() : super.getDisplayName(stack);
    }

    @Nullable
    public static ItemStack getSourceItem(ItemStack stack)
    {
        if(stack.getItem() == ItemInit.SOURCE.get())
        {
            CompoundNBT tag = stack.getOrCreateTag();
            if (tag.contains("item"))
                return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item"))));
        }

        return null;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        CompoundNBT tag = stack.getOrCreateTag();
        if (tag.contains("item"))
            ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item"))).addInformation(stack, worldIn, tooltip, flagIn);
    }
}
