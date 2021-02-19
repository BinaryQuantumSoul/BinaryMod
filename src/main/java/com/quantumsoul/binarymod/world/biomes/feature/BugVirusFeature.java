package com.quantumsoul.binarymod.world.biomes.feature;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

import static com.quantumsoul.binarymod.util.WorldUtils.getGroundLevel;
import static com.quantumsoul.binarymod.util.WorldUtils.isBinDimBlock;

public class BugVirusFeature extends Feature<BugVirusConfig>
{
    public BugVirusFeature()
    {
        super(BugVirusConfig.CODEC);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BugVirusConfig config)
    {
        //SIZE
        int count = 0;
        int rad = rand.nextInt(config.radius - 4) + 4;

        //SHAPE
        int s1 = rand.nextInt(2) + 1;
        int s2 = rand.nextInt(2) + 1;
        if (s1 == 2 && s2 == 2)
            s1 = 1;

        for (int x = pos.getX() - rad; x <= pos.getX() + rad; ++x)
        {
            for (int z = pos.getZ() - rad; z <= pos.getZ() + rad; ++z)
            {
                int deltaX = x - pos.getX();
                int deltaZ = z - pos.getZ();
                if (Math.pow(s1 * deltaX, 2) + Math.pow(s2 * deltaZ, 2) <= rad * rad)
                {
                    BlockPos blockpos = new BlockPos(x, getGroundLevel(reader, x, z), z);
                    Block block = reader.getBlockState(blockpos).getBlock();

                    if (isBinDimBlock(block))
                    {
                        reader.setBlockState(blockpos, config.state, 2);
                        ++count;
                    }
                }
            }
        }

        return count > 0;
    }
}
