package com.quantumsoul.binarymod.tileentity.container;

import com.quantumsoul.binarymod.init.ContainerInit;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.tileentity.BlockProgTileEntity;
import com.quantumsoul.binarymod.tileentity.container.slot.FreezableUniqueSlot;
import com.quantumsoul.binarymod.tileentity.container.slot.ResultSlot;
import com.quantumsoul.binarymod.tileentity.container.slot.UniqueSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.IItemHandler;

import static com.quantumsoul.binarymod.item.SourceItem.getSourceItem;

public class BlockProgContainer extends ProgrammerContainer
{
    public final BlockProgTileEntity tileEntity;

    public BlockProgContainer(int id, PlayerInventory playerInventory, PacketBuffer extraData)
    {
        this(id, playerInventory, (BlockProgTileEntity) Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos()));
    }

    public BlockProgContainer(int id, PlayerInventory playerInv, BlockProgTileEntity tile)
    {
        super(ContainerInit.BLOCK_PROGRAMMER.get(), id, 3);
        tileEntity = tile;

        IItemHandler tileInv = tile.getContents();

        addSlot(new FreezableUniqueSlot(tileInv, 0, 11, 18, ItemInit.SOURCE.get(), () -> !tile.isDoing()));
        addSlot(new UniqueSlot(tileInv, 1, 11, 48, ItemInit.CODE.get()));

        addSlot(new ResultSlot(tileInv, 2, 148, 35));

        bindPlayerInventory(playerInv, 8, 84);
    }

    public boolean canDo()
    {
        ItemStack result = getSourceItem(getSlot(0).getStack());
        ItemStack resultSlot = getSlot(2).getStack();

        return getSlot(1).getHasStack() && result != null && (resultSlot.isEmpty() || (resultSlot.getItem() == result.getItem() && resultSlot.getCount() < resultSlot.getMaxStackSize())) ;
    }
}
