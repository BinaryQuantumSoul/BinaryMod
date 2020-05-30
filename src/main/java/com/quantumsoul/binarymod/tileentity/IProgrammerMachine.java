package com.quantumsoul.binarymod.tileentity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IProgrammerMachine extends INamedContainerProvider
{
    void openGui(ServerPlayerEntity player);
    void dropAllContents(World world, BlockPos blockPos);
}
