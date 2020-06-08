package com.quantumsoul.binarymod.item;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerWrapper implements ICapabilityProvider
{
    protected ItemStackHandler contents;
    protected LazyOptional<IItemHandler> holder;

    public ContainerWrapper(int size)
    {
        this.contents = new ItemStackHandler(size);
        this.holder = LazyOptional.of(() -> contents);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return holder.cast();

        return LazyOptional.empty();
    }
}
