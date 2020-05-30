package com.quantumsoul.binarymod.tileentity.container;

import com.quantumsoul.binarymod.init.ContainerInit;
import com.quantumsoul.binarymod.item.SDCardItem;
import com.quantumsoul.binarymod.tileentity.ComputerTileEntity;
import com.quantumsoul.binarymod.tileentity.container.slot.ComputerSlot;
import com.quantumsoul.binarymod.tileentity.container.slot.FrozenComputerSlot;
import com.quantumsoul.binarymod.tileentity.container.slot.SDSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static com.quantumsoul.binarymod.tileentity.ComputerTileEntity.ComputerState.SD;

public class ComputerContainer extends ProgrammerContainer
{
    private final ComputerTileEntity tileEntity;

    @OnlyIn(Dist.CLIENT)
    public ComputerContainer(int id, PlayerInventory playerInventory, PacketBuffer extraData)
    {
        this(id, playerInventory, (ComputerTileEntity) Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos()));
    }

    public ComputerContainer(int id, PlayerInventory playerInventory, ComputerTileEntity tileEntity)
    {
        super(ContainerInit.COMPUTER.get(), id, ComputerTileEntity.SLOT_NUMBER);

        this.tileEntity = tileEntity;
        ComputerTileEntity.ComputerState state = tileEntity.getState();
        IItemHandler contents = tileEntity.getContents();

        switch(state)
        {
            case SD:
                addSlot(new FrozenComputerSlot(contents, 0, 92, 19, this));
                bindSDInventory();
                bindPlayerInventory(playerInventory, 8, 70 + 17 * getSDOrder());
                break;

            case BATTERY:
                addSlot(new FrozenComputerSlot(contents, 0, 70, 19, this));
                break;

            case DARK_NET:
                addSlot(new FrozenComputerSlot(contents, 0, 92, 19, this));//todo
                break;

            default:
                addSlot(new ComputerSlot(contents, 0, 62, 41, this));
                bindPlayerInventory(playerInventory, 8, 95);
                break;
        }
    }

    private void bindSDInventory()
    {
        ItemStack stack = getStack();

        if (!(stack.getItem() instanceof SDCardItem))
            throw new IllegalArgumentException("Expected a SDCard ItemStack");
        else
        {
            ItemStackHandler inv = (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(IllegalArgumentException::new);
            CompoundNBT contents = stack.getChildTag("contents");
            if(contents != null)
                inv.deserializeNBT(contents);

            for (int i = 0; i < ((SDCardItem) stack.getItem()).getInventorySize(); i++)
                this.addSlot(new SDSlot(inv, i, 12 + (i % 9) * 17, 42 + (i / 9) * 17, stack));
        }
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        if(getState() != SD)
            return super.transferStackInSlot(playerIn, index);

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            itemstack = slotStack.copy();

            int sd = size + getSDSize()  - 1;
            if (index >= sd)
            {
                if (!this.mergeItemStack(slotStack, size, sd, false))
                    return ItemStack.EMPTY;
            }
            else if (index >= size)
            {
                if (!this.mergeItemStack(slotStack, sd + 1, this.inventorySlots.size(), false))
                    return ItemStack.EMPTY;
            }

            if (slotStack.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }

    private ItemStack getStack()
    {
        return getSlot(0).getStack();
    }

    public int getSDOrder()
    {
        return ((SDCardItem)getStack().getItem()).getOrder();
    }

    public int getSDSize()
    {
        return ((SDCardItem)getStack().getItem()).getInventorySize();
    }

    public ComputerTileEntity.ComputerState getState()
    {
        return tileEntity.getState();
    }

    public BlockPos getPos()
    {
        return tileEntity.getPos();
    }

    public void load(boolean load)
    {
        if (load)
            tileEntity.load();
        else
            tileEntity.unload();
    }
}
