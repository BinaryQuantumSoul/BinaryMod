package com.quantumsoul.binarymod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class WireBlock extends RotatedPillarBlock
{
    protected static final VoxelShape shapeX = Block.makeCuboidShape(0D, 3D, 3D, 16D, 13D, 13D);
    protected static final VoxelShape shapeY = Block.makeCuboidShape(3D, 0D, 3D, 13D, 16D, 13D);
    protected static final VoxelShape shapeZ = Block.makeCuboidShape(3D, 3D, 0D, 13D, 13D, 16D);

    public WireBlock(Block.Properties properties)
    {
        super(properties);
    }

    private VoxelShape getShape(BlockState state)
    {
        if(state.get(AXIS) == Direction.Axis.X)
            return shapeX;
        else if(state.get(AXIS) == Direction.Axis.Y)
            return shapeY;
        else if(state.get(AXIS) == Direction.Axis.Z)
            return shapeZ;
        else
            return shapeY;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return this.getShape(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return this.getShape(state);
    }
}
