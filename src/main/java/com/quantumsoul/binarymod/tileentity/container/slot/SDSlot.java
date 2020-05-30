package com.quantumsoul.binarymod.tileentity.container.slot;

import com.quantumsoul.binarymod.item.SDCardItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.IntNBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SDSlot extends SlotItemHandler
{
    ItemStack sdCard;

    public SDSlot(ItemStackHandler itemHandler, int index, int xPosition, int yPosition, ItemStack sd)
    {
        super(itemHandler, index, xPosition, yPosition);

        if(!(sd.getItem() instanceof SDCardItem))
            throw new IllegalArgumentException("Expected SDCard ItemStack");

        sdCard = sd;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return !(stack.getItem() instanceof SDCardItem);
    }

    @Override
    public void onSlotChanged()
    {
        super.onSlotChanged();

        ItemStackHandler inv = (ItemStackHandler) getItemHandler();

        sdCard.setTagInfo("contents", inv.serializeNBT());

        int count = 0;
        for(int i = 0; i < inv.getSlots(); i++)
            if(!inv.getStackInSlot(i).isEmpty())
                count++;

        sdCard.setTagInfo("storage", IntNBT.valueOf((int) ((float)count / (float)((SDCardItem)sdCard.getItem()).getInventorySize() * 100)));
    }
}
