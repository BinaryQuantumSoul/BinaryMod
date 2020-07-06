package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.init.TileEntityInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Collections;
import java.util.List;

public class FeederTileEntity extends FactoryTileEntity
{
    public FeederTileEntity()
    {
        super(TileEntityInit.FEEDER.get(), 2);
    }

    @Override
    void doAction(PlayerEntity player, int level)
    {
        player.addItemStackToInventory(new ItemStack(level == 0 ? Items.COOKIE : ItemInit.ONION.get()));
    }

    @Override
    List<ItemStack> getDrops(int level)
    {
        return Collections.singletonList(new ItemStack(level == 0 ? Items.COOKIE : ItemInit.ONION.get()));
    }
}
