package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.TileEntityInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class HealerTileEntity extends FactoryTileEntity
{
    public HealerTileEntity()
    {
        super(TileEntityInit.HEALER.get(), 4);
    }

    @Override
    void doAction(PlayerEntity player, int level)
    {
        player.heal((float) (2.5F * Math.pow(2, level)));
    }

    @Nullable
    @Override
    List<ItemStack> getDrops(int level)
    {
        return null;
    }
}
