package com.quantumsoul.binarymod.tileentity.container.slot;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class UniqueSlot extends SlotItemHandler
{
    private final Item item;

    public UniqueSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item uniqueItem)
    {
        super(itemHandler, index, xPosition, yPosition);

        item = uniqueItem;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return stack.getItem() == item;
    }
}
