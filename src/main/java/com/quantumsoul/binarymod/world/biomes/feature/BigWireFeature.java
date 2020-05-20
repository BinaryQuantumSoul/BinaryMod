package com.quantumsoul.binarymod.world.biomes.feature;

import com.mojang.datafixers.Dynamic;
import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class BigWireFeature extends Feature<NoFeatureConfig>
{
    public BigWireFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        int size = 0;
        BlockPos.Mutable nextPos = new BlockPos.Mutable();
        for(int y = 0; y <= 127; y++)
        {
            nextPos.setPos(pos.getX(), y, pos.getZ());
            if(worldIn.getBlockState(nextPos) == Blocks.AIR.getDefaultState())
            {
                worldIn.setBlockState(nextPos, BlockInit.WIRE_BLOCK.get().getDefaultState(), 2);
                size++;
            }
        }
        return size > 0;
    }
}
