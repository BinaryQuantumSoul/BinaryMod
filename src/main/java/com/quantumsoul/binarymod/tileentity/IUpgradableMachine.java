package com.quantumsoul.binarymod.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;

public interface IUpgradableMachine extends ITickableTileEntity
{
    boolean upgrade();
    void setLevel(int level);
}
