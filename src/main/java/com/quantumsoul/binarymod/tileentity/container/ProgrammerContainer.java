package com.quantumsoul.binarymod.tileentity.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ProgrammerContainer extends Container
{
    protected final int size;

    protected ProgrammerContainer(ContainerType<?> type, int id, int slotNumber)
    {
        super(type, id);
        this.size = slotNumber;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }

    protected void bindPlayerInventory(PlayerInventory playerInventory, int x, int y)
    {
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));

        for (int k = 0; k < 9; ++k)
            this.addSlot(new Slot(playerInventory, k, x + k * 18, y + 58));
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            itemstack = slotStack.copy();

            if (index >= size)
            {
                if (!this.mergeItemStack(slotStack, 0, size, false))
                    return ItemStack.EMPTY;
            }
            else
            {
                if (!this.mergeItemStack(slotStack, size, this.inventorySlots.size(), false))
                    return ItemStack.EMPTY;
            }

            if (slotStack.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }
}
