package com.quantumsoul.binarymod.compat.hwyla;

import com.quantumsoul.binarymod.init.BlockInit;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class BackdoorProvider implements IComponentProvider
{
    @Override
    public ItemStack getStack(IDataAccessor accessor, IPluginConfig config)
    {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        mutable.setPos(accessor.getPosition());
        Block block;
        do
        {
            mutable.setPos(mutable.getX(), mutable.getY() - 1.0D, mutable.getZ());
            block = accessor.getWorld().getBlockState(mutable).getBlock();
        }
        while (block == BlockInit.BACKDOOR.get());

        return new ItemStack(block);
    }
}
