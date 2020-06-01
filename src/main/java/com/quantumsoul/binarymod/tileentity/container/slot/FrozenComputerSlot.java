package com.quantumsoul.binarymod.tileentity.container.slot;

import com.quantumsoul.binarymod.tileentity.container.ComputerContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.items.IItemHandler;

public class FrozenComputerSlot extends ComputerSlot
{
    public FrozenComputerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ComputerContainer computerContainer)
    {
        super(itemHandler, index, xPosition, yPosition, computerContainer);
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn)
    {
        return false;
    }
}