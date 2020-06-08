package com.quantumsoul.binarymod.tileentity.container.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.items.IItemHandler;

import java.util.function.BooleanSupplier;

public class FreezableUniqueSlot extends UniqueSlot
{
    private final BooleanSupplier frozen;

    public FreezableUniqueSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item uniqueItem, BooleanSupplier isFrozen)
    {
        super(itemHandler, index, xPosition, yPosition, uniqueItem);
        frozen = isFrozen;
    }

    @Override
    public boolean canTakeStack(PlayerEntity playerIn)
    {
        return frozen.getAsBoolean();
    }
}
