package com.quantumsoul.binarymod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TrojanBlock extends Block
{
    protected static final VoxelShape shape = Block.makeCuboidShape(1D, 0D, 7D, 13D, 13D, 10D);

    public TrojanBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        super.tick(state, worldIn, pos, rand);

        List<Entity> entities = worldIn.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(pos.add(-8D, -4D, -8D), pos.add(8D, 4D, 8D)));
        for (final Entity e : entities)
        {
            double x = (pos.getX() - e.getPosX()) / 10D;
            double y = (pos.getY() - e.getPosY()) / 10D;
            double z = (pos.getZ() - e.getPosZ()) / 10D;

            e.setMotion(x, y, z);
        }

        worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
    }

    @Override
    public int tickRate(IWorldReader worldIn)
    {
        return 5;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, this.tickRate(worldIn));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new StringTextComponent(TextFormatting.GRAY + I18n.format("tooltip.binarymod.trojan")));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return shape;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return shape;
    }
}
