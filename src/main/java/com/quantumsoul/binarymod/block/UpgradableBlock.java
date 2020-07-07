package com.quantumsoul.binarymod.block;

import com.quantumsoul.binarymod.tileentity.IUpgradableMachine;
import com.quantumsoul.binarymod.util.MachineUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class UpgradableBlock extends MachineBlock
{
    public static MachineUtils.LevelInfo CONSTLEVELS; //used in fillStateContainer() which is in super()
    public final MachineUtils.LevelInfo levelInfo;

    public static UpgradableBlock create(Properties builder, Supplier<TileEntity> tile, MachineUtils.LevelInfo levels)
    {
        CONSTLEVELS = levels;
        return new UpgradableBlock(builder, tile);
    }

    protected UpgradableBlock(Properties builder, Supplier<TileEntity> tile)
    {
        super(builder, tile);

        levelInfo = CONSTLEVELS;
        setDefaultState(getDefaultState().with(levelInfo.level, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(CONSTLEVELS.level);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("level", state.get(levelInfo.level));

        ItemStack stack = new ItemStack(this);
        stack.setTagInfo("levelInfo", compound);

        return Arrays.asList(stack);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        CompoundNBT data = context.getItem().getOrCreateChildTag("levelInfo");

        if (data.contains("level"))
            return super.getStateForPlacement(context).with(levelInfo.level, data.getInt("level"));

        return super.getStateForPlacement(context);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (!worldIn.isRemote)
            ((IUpgradableMachine) worldIn.getTileEntity(pos)).setLevel(state.get(levelInfo.level));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        CompoundNBT data = stack.getOrCreateChildTag("levelInfo");
        tooltip.add(levelInfo.formatLevel(data.contains("level") ? data.getInt("level") : 0));
    }
}
