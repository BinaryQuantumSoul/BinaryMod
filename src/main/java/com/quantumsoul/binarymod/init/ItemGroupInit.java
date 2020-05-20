package com.quantumsoul.binarymod.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupInit extends ItemGroup
{
    public static ItemGroupInit instance = new ItemGroupInit(ItemGroup.getGroupCountSafe(), "binarymod.tab");
    public ItemGroupInit(int index, String label)
    {
        super(index, label);
    }

    @Override
    public ItemStack createIcon()
    {
        return new ItemStack(BlockInit.BINARY_BLOCK.get());
    }
}
