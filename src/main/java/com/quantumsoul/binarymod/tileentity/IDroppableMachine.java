package com.quantumsoul.binarymod.tileentity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IDroppableMachine
{
    void drop(World worldIn, BlockPos pos);
}
