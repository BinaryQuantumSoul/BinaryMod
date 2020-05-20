package com.quantumsoul.binarymod.world.biomes.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.quantumsoul.binarymod.world.WorldUtils.getGroundLevel;

public class LessThanOnePlacement extends Placement<FrequencyConfig>
{
    public LessThanOnePlacement(Function<Dynamic<?>, ? extends FrequencyConfig> configFactoryIn)
    {
        super(configFactoryIn);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generatorIn, Random random, FrequencyConfig configIn, BlockPos pos)
    {
        return IntStream.of(0).mapToObj((i) ->
        {
            if(random.nextInt(configIn.count) != 0)
                return null;

            int x = random.nextInt(16) + pos.getX();
            int z = random.nextInt(16) + pos.getZ();
            int y = getGroundLevel(worldIn, x, z);
            return new BlockPos(x, y, z);
        }).filter(Objects::nonNull);
    }
}
