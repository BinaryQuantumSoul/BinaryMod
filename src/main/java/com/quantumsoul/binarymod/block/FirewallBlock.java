package com.quantumsoul.binarymod.block;

import com.quantumsoul.binarymod.entity.WormEntity;
import com.quantumsoul.binarymod.init.EntityInit;
import com.quantumsoul.binarymod.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FirewallBlock extends Block
{
    public static final BooleanProperty USED = BooleanProperty.create("used");

    public FirewallBlock(Properties properties)
    {
        super(properties);
        setDefaultState(getDefaultState().with(USED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(USED);
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
    {
        if (worldIn.isRemote())
            return;

        if(player.isCrouching() && !state.get(USED))
        {
            worldIn.setBlockState(pos, state.with(USED, true), 2);
            player.addItemStackToInventory(new ItemStack(ItemInit.FIREBRICK.get()));
        }
    }

    @Override
    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type)
    {
        return false;
    }
}
