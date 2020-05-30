package com.quantumsoul.binarymod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

public class FactoryBlock extends UpgradableBlock
{
    public static final BooleanProperty DONE = BooleanProperty.create("done");

    public static FactoryBlock create(Properties builder, Supplier<TileEntity> tile, int levels)
    {
        CONSTLEVELS = levels;
        return new FactoryBlock(builder, tile);
    }

    protected FactoryBlock(Properties builder, Supplier<TileEntity> tile)
    {
        super(builder, tile);
        setDefaultState(getDefaultState().with(DONE, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(DONE);
    }
}
