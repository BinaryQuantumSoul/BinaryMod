package com.quantumsoul.binarymod.block;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class VirusBlock extends Block
{
    public VirusBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if(entityIn instanceof PlayerEntity)
            if(((PlayerEntity)entityIn).getHeldItemMainhand().getItem() != ItemInit.ANTIVIRUS_TOOL.get())
                spread(worldIn, pos);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        super.onBlockHarvested(worldIn, pos, state, player);
        if(player.getHeldItemMainhand().getItem() != ItemInit.ANTIVIRUS_TOOL.get())
            spread(worldIn, pos);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_)
    {
        if(worldIn.isRemote())
            return ActionResultType.PASS;

        if(player.getHeldItemMainhand().getItem() == ItemInit.ANTIVIRUS_TOOL.get())
        {
            player.getHeldItemMainhand().damageItem(1, player, (p) -> {
                p.sendBreakAnimation(handIn);
            });

            if(this instanceof VirusBugBlock)
                worldIn.setBlockState(pos, BlockInit.BUG_BLOCK.get().getDefaultState(), 3);
            else
                worldIn.setBlockState(pos, BlockInit.VIRUS_DEAD_BLOCK.get().getDefaultState(), 3);

            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    private void spread(IWorld worldIn, BlockPos pos)
    {
        if(worldIn.isRemote())
            return;

        for(int i = pos.getX() - 1; i <= pos.getX() + 1; i++)
            for(int j = pos.getY() - 1; j <= pos.getY() + 1; j++)
                for(int k = pos.getZ() -1; k <= pos.getZ() + 1; k++)
                {
                    BlockPos npos = new BlockPos(i, j, k);
                    BlockState block = worldIn.getBlockState(npos);
                    block.getRenderType();
                    if(block.isSolid() && block.getShape(worldIn, pos) == VoxelShapes.fullCube() && !block.hasTileEntity() && !(block.getBlock() instanceof VirusBlock) && block.getBlock() != BlockInit.VIRUS_DEAD_BLOCK.get() && block.getBlockHardness(worldIn, npos) != -1)
                    {
                        if (this instanceof VirusBugBlock || block.getBlock() instanceof BugBlock)
                            worldIn.setBlockState(npos, BlockInit.VIRUS_BUG_BLOCK.get().getDefaultState(), 3);
                        else
                            worldIn.setBlockState(npos, BlockInit.VIRUS_BLOCK.get().getDefaultState(), 3);
                    }
                }
    }
}
