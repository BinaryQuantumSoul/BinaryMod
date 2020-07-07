package com.quantumsoul.binarymod.block;

import com.quantumsoul.binarymod.util.MachineUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

public class BoolBlock extends UpgradableBlock
{
    public static final BooleanProperty DONE = BooleanProperty.create("done");

    public static BoolBlock create(Properties builder, Supplier<TileEntity> tile, MachineUtils.LevelInfo levels)
    {
        CONSTLEVELS = levels;
        return new BoolBlock(builder, tile);
    }

    protected BoolBlock(Properties builder, Supplier<TileEntity> tile)
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
