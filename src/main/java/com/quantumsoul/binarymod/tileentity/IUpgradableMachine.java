package com.quantumsoul.binarymod.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public interface IUpgradableMachine
{
    boolean upgrade();
    void setLevel(int level);
    boolean execute(PlayerEntity player);
    void drop(BlockPos pos);
}
