package com.quantumsoul.binarymod.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupInit
{
    public static final ItemGroup BINDIM_TAB = new ItemGroup("binarymod.tab")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(BlockInit.BINARY_BLOCK.get());
        }
    };
}
