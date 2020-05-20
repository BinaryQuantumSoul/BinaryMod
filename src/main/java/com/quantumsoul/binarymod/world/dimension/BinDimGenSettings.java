package com.quantumsoul.binarymod.world.dimension;

import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationSettings;

public class BinDimGenSettings extends GenerationSettings
{
    public int getBedrockFloorHeight()
    {
        return 0;
    }

    public int getBedrockRoofHeight()
    {
        return 127;
    }

    @Override
    public BlockState getDefaultBlock()
    {
        return BlockInit.BINARY_BLOCK.get().getDefaultState();
    }

    @Override
    public BlockState getDefaultFluid()
    {
        return BlockInit.ON_BINARY_BLOCK.get().getDefaultState();
    }

    public static BinDimGenSettings create()
    {
        return new BinDimGenSettings();
    }
}