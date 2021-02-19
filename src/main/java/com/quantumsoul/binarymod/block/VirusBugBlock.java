package com.quantumsoul.binarymod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class VirusBugBlock extends VirusBlock
{
    protected static final VoxelShape shape = Block.makeCuboidShape(1D, 1D, 1D, 15D, 16D, 15D);

    public VirusBugBlock(Properties properties)
    {
        super(properties.slipperiness(1.5F));
        properties.slipperiness(0.6F);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        super.onEntityWalk(worldIn, pos, entityIn);
        Vector3d motion = entityIn.getMotion();
        entityIn.setMotion(new Vector3d(motion.getX(), 1, motion.getZ()));
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {}

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return shape;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn)
    {
        if (!(entityIn instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity) entityIn;
        Vector3d mo = entity.getMotion();
        boolean fall = false;

        double y = 0D;
        if (entity.collidedVertically && entity.getPosition().getY() + 1 < pos.getY())
        {
            if (entity instanceof PlayerEntity && !entity.isCrouching())
                y = 0.1D;
            else
                fall = true;
        }
        else
        {
            if (entity instanceof PlayerEntity && entity.isCrouching())
                y = entity.isInWater() ? 0.02D : 0.08D;
            else if (entity.collidedHorizontally)
                y = 0.2D;
            else
                y = Math.max(mo.getY(), -0.07D);
        }

        if (!fall)
            entity.fallDistance = 0F;

        entity.setMotion(mo.getX(), y, mo.getZ());
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity)
    {
        return true;
    }
}
