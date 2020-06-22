package com.quantumsoul.binarymod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IExecutableMachine extends ITickableTileEntity
{
    boolean execute(PlayerEntity player);
    void drop(World worldIn, BlockPos pos);
}
