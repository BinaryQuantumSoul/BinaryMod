package com.quantumsoul.binarymod.item;

import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
}
