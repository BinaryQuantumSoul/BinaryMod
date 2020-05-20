package com.quantumsoul.binarymod.world.biomes.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

import static com.quantumsoul.binarymod.world.WorldUtils.getGroundLevel;
import static com.quantumsoul.binarymod.world.WorldUtils.isBinDimBlock;

public class BugVirusFeature extends Feature<BugVirusConfig>
{
    public BugVirusFeature(Function<Dynamic<?>, ? extends BugVirusConfig> configFactoryIn)
    {
        super(configFactoryIn);
    }

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, BugVirusConfig config)
    {
        //SIZE
        int count = 0;
        int rad = rand.nextInt(config.radius - 4) + 4;

        //SHAPE
        int s1 = rand.nextInt(2) + 1;
        int s2 = rand.nextInt(2) + 1;
        if(s1 == 2 && s2 == 2)
            s1 = 1;

        for (int x = pos.getX() - rad; x <= pos.getX() + rad; ++x)
        {
            for (int z = pos.getZ() - rad; z <= pos.getZ() + rad; ++z)
            {
                int deltaX = x - pos.getX();
                int deltaZ = z - pos.getZ();
                if (Math.pow(s1*deltaX, 2) + Math.pow(s2*deltaZ, 2) <= rad * rad)
                {
                    BlockPos blockpos = new BlockPos(x, getGroundLevel(worldIn, x, z), z);
                    Block block = worldIn.getBlockState(blockpos).getBlock();

                    if (isBinDimBlock(block))
                    {
                        worldIn.setBlockState(blockpos, config.state, 2);
                        ++count;
                    }
                }
            }
        }

        return count > 0;
    }
}
