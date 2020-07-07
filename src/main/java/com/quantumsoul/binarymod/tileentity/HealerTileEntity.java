package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.TileEntityInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

import static com.quantumsoul.binarymod.util.MachineUtils.L_HEALER;

public class HealerTileEntity extends FactoryTileEntity
{
    public HealerTileEntity()
    {
        super(TileEntityInit.HEALER.get(), L_HEALER);
    }

    @Override
    void doAction(PlayerEntity player, int level)
    {
        player.heal(L_HEALER.get(level));
    }

    @Nullable
    @Override
    List<ItemStack> getDrops(int level)
    {
        return null;
    }
}
