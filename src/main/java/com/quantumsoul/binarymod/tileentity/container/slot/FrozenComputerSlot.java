package com.quantumsoul.binarymod.tileentity.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.items.IItemHandler;

public class FrozenComputerSlot extends ComputerSlot
{
    public FrozenComputerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn)
    {
        return false;
    }
}
