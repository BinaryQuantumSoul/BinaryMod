package com.quantumsoul.binarymod.tileentity.container.slot;

import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.item.SDCardItem;
import com.quantumsoul.binarymod.tileentity.container.ComputerContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ComputerSlot extends SlotItemHandler
{
    private final ComputerContainer container;

    public ComputerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ComputerContainer computerContainer)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.container = computerContainer;
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        Item item = stack.getItem();
        return super.isItemValid(stack) && (item instanceof SDCardItem || item == ItemInit.DARK_NET.get());

        //todo add battery
    }
}