package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.init.TileEntityInit;
import com.quantumsoul.binarymod.item.SDCardItem;
import com.quantumsoul.binarymod.tileentity.container.ComputerContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class ComputerTileEntity extends ProgrammerTileEntity
{
    public enum ComputerState
    {
        BASE,
        SD,
        BATTERY,
        DARK_NET
    }

    public static int SLOT_NUMBER = 1;
    private ComputerState state = ComputerState.BASE;

    public ComputerTileEntity()
    {
        super(TileEntityInit.COMPUTER.get(), SLOT_NUMBER);
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new ComputerContainer(id, playerInventory, this);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("container.binarymod.computer");
    }

    //=================================================== PROCESS ===================================================
    public ComputerState getState()
    {
        return state;
    }

    public void setState(ComputerState state)
    {
        this.state = state;
        this.markDirty();
    }

    protected ComputerState checkState(ItemStack stack)
    {
        if (stack.getItem() instanceof SDCardItem)
            return ComputerState.SD;
//        else if (stack.getItem() == ItemInit.BATTERY.get()) todo
//            return ComputerState.BATTERY;
        else if (stack.getItem() == ItemInit.DARK_NET.get())
            return ComputerState.DARK_NET;
        else
            return ComputerState.BASE;
    }

    public void load(boolean load)
    {
        if (load)
            setState(checkState(contents.getStackInSlot(0)));
        else
            setState(ComputerState.BASE);
    }

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putInt("state", state.ordinal());

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        state = ComputerState.values()[compound.getInt("state")];
    }
}
