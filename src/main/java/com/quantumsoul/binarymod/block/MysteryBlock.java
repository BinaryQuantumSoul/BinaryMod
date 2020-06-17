package com.quantumsoul.binarymod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.quantumsoul.binarymod.util.WorldUtils.dropStacks;

public class MysteryBlock extends Block
{
    public MysteryBlock(Block.Properties properties)
    {
        super(properties);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (worldIn.isRemote)
            return;

        if (worldIn.rand.nextInt(3) == 0)
            worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.5F + worldIn.rand.nextInt(4) * 1F, Explosion.Mode.BREAK);
        else
        {
            LootContext.Builder builder = new LootContext.Builder((ServerWorld) worldIn);
            builder.withParameter(LootParameters.POSITION, pos);
            builder.withLuck(player.getLuck()).withParameter(LootParameters.THIS_ENTITY, player);

            Set<ResourceLocation> tables = LootTables.func_215796_a();
            LootTable table1 = worldIn.getServer().getLootTableManager().getLootTableFromLocation(tables.stream().skip(worldIn.rand.nextInt(tables.size() - 1)).findFirst().get());
            LootTable table2 = worldIn.getServer().getLootTableManager().getLootTableFromLocation(tables.stream().skip(worldIn.rand.nextInt(tables.size() - 1)).findFirst().get());
            List<ItemStack> loot1 = table1.generate(builder.build(LootParameterSets.CHEST));
            List<ItemStack> loot2 = table2.generate(builder.build(LootParameterSets.CHEST));

            List<ItemStack> loots = Stream.concat(loot1.stream(), loot2.stream()).collect(Collectors.toList());

            dropStacks(worldIn, pos, loots);
        }
    }
}
