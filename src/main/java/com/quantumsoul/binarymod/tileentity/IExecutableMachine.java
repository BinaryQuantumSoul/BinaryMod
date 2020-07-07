package com.quantumsoul.binarymod.tileentity;

import net.minecraft.entity.player.ServerPlayerEntity;

public interface IExecutableMachine
{
    boolean execute(ServerPlayerEntity player);
}