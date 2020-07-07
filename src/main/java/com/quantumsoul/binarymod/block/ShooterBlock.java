package com.quantumsoul.binarymod.block;

import com.quantumsoul.binarymod.util.MachineUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import java.util.function.Supplier;

public class ShooterBlock extends BoolBlock
{
    VoxelShape shape = Block.makeCuboidShape(6D, 0D, 6D, 10D, 14D, 10D);

    public static ShooterBlock create(Properties builder, Supplier<TileEntity> tile, MachineUtils.LevelInfo levels)
    {
        CONSTLEVELS = levels;
        return new ShooterBlock(builder, tile);
    }

    protected ShooterBlock(Properties builder, Supplier<TileEntity> tile)
    {
        super(builder, tile);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return shape;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return shape;
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return shape;
    }
}
