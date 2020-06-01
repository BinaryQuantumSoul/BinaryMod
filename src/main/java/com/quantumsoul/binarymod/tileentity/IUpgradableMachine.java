package com.quantumsoul.binarymod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;

public interface IUpgradableMachine extends ITickableTileEntity
{
    boolean upgrade();
    void setLevel(int level);
    boolean execute(PlayerEntity player);
    void drop(BlockPos pos);
}
