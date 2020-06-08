package com.quantumsoul.binarymod.block;

import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.tileentity.IProgrammerMachine;
import com.quantumsoul.binarymod.tileentity.IUpgradableMachine;
import com.quantumsoul.binarymod.tileentity.ProgrammerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class MachineBlock extends Block
{
    public static final DirectionProperty DIRECTION = HorizontalBlock.HORIZONTAL_FACING;

    private final Supplier<TileEntity> tile;

    public MachineBlock(Properties builder, Supplier<TileEntity> tile)
    {
        super(builder);
        this.tile = tile;
        setDefaultState(stateContainer.getBaseState().with(DIRECTION, Direction.NORTH));
    }

    //=================================================== TILE ENTITY ===================================================
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return tile.get();
    }

    //todo get light value if energy

    //=================================================== INTERACTIONS ===================================================
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult)
    {
        if (!world.isRemote)
        {
            final TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof IUpgradableMachine)
            {
                IUpgradableMachine upgradableMachine = (IUpgradableMachine) tileEntity;

                ItemStack holdItem = player.getHeldItem(hand);
                if (holdItem.getItem() == ItemInit.UPGRADE.get() && upgradableMachine.upgrade())
                {
                    if (!player.abilities.isCreativeMode)
                        holdItem.shrink(1);

                    upgradableMachine.execute(player);

                    return ActionResultType.CONSUME;
                }
                else if (!upgradableMachine.execute(player))
                    return ActionResultType.FAIL;
            }
            else if (tileEntity instanceof IProgrammerMachine)
                ((IProgrammerMachine) tileEntity).openGui((ServerPlayerEntity) player);
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            if (!world.isRemote)
            {
                final TileEntity tileEntity = world.getTileEntity(pos);
                if(tileEntity instanceof IUpgradableMachine)
                    ((IUpgradableMachine) tileEntity).drop(world, pos);
                else if (tileEntity instanceof IProgrammerMachine)
                    ((IProgrammerMachine) tileEntity).dropAllContents(world, pos);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    //=================================================== ROTATION ===================================================
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(DIRECTION);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(DIRECTION, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.with(DIRECTION, rot.rotate(state.get(DIRECTION)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.toRotation(state.get(DIRECTION)));
    }
}
