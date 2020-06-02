package com.quantumsoul.binarymod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class SourceItem extends Item
{
    public SourceItem(Item.Properties properties)
    {
        super(properties);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack)
    {
        CompoundNBT tag = stack.getOrCreateTag();
        if(tag.contains("item"))
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item"))).getDisplayName(ItemStack.EMPTY);

        return super.getDisplayName(stack);
    }
}
