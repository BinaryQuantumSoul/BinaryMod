package com.quantumsoul.binarymod.tileentity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import static com.quantumsoul.binarymod.util.WorldUtils.dropInventoryItems;

public abstract class ProgrammerTileEntity extends MachineTileEntity implements IExecutableMachine, IDroppableMachine, INamedContainerProvider
{
    private final int numberOfSlots;

    protected ItemStackHandler contents;
    private final LazyOptional<IItemHandler> holder;

    public ProgrammerTileEntity(TileEntityType<?> tileEntityTypeIn, int slotNumber)
    {
        super(tileEntityTypeIn);

        this.numberOfSlots = slotNumber;

        this.contents = new ItemStackHandler(slotNumber);
        this.holder = LazyOptional.of(() -> contents);
    }

    //=================================================== PROCESS ===================================================
    @Override
    public boolean execute(ServerPlayerEntity player)
    {
        if (!world.isRemote)
            NetworkHooks.openGui(player, this, this.pos);

        return true;
    }

    @Override
    public void drop(World world, BlockPos blockPos)
    {
        dropInventoryItems(world, blockPos, this.contents);
    }

    public IItemHandler getContents()
    {
        return this.contents;
    }

    //=================================================== DATA ===================================================
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return holder.cast();

        return super.getCapability(capability, facing);
    }

    @Override
    public void remove()
    {
        super.remove();
        holder.invalidate();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.put("contents", contents.serializeNBT());

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        contents.deserializeNBT(compound.getCompound("contents"));
        if (contents.getSlots() != numberOfSlots)
            throw new IllegalArgumentException("Corrupted NBT: Number of inventory slots did not match expected.");
    }
}
