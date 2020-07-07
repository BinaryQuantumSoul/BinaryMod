package com.quantumsoul.binarymod.tileentity;

import net.minecraft.util.text.ITextComponent;

public interface IUpgradableMachine
{
    boolean upgrade();
    void setLevel(int level);
    ITextComponent getLevelMessage();
    ITextComponent getInfoMessage();
}
