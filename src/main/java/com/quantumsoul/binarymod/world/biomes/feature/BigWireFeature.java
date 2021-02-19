package com.quantumsoul.binarymod.world.biomes.feature;

import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class BigWireFeature extends Feature<NoFeatureConfig>
{
    public BigWireFeature()
    {
        super(NoFeatureConfig.field_236558_a_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        int size = 0;
        BlockPos.Mutable nextPos = new BlockPos.Mutable();
        for (int y = 0; y <= 127; y++)
        {
            nextPos.setPos(pos.getX(), y, pos.getZ());
            if (reader.getBlockState(nextPos) == Blocks.AIR.getDefaultState())
            {
                reader.setBlockState(nextPos, BlockInit.WIRE_BLOCK.get().getDefaultState(), 2);
                size++;
            }
        }
        return size > 0;
    }
}
