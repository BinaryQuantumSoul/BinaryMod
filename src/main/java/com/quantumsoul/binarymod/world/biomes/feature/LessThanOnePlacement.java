package com.quantumsoul.binarymod.world.biomes.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.quantumsoul.binarymod.util.WorldUtils.getGroundLevel;
import static com.quantumsoul.binarymod.util.WorldUtils.getWorldFromWorldDecoratingHelper;

public class LessThanOnePlacement extends Placement<ChanceConfig>
{
    public LessThanOnePlacement()
    {
        super(ChanceConfig.CODEC);
    }

    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, ChanceConfig config, BlockPos pos)
    {
        return IntStream.of(0).mapToObj((i) ->
        {
            if (rand.nextInt(config.chance) != 0)
                return null;

            int x = rand.nextInt(16) + pos.getX();
            int z = rand.nextInt(16) + pos.getZ();
            int y = getGroundLevel(getWorldFromWorldDecoratingHelper(helper), x, z);
            return new BlockPos(x, y, z);
        }).filter(Objects::nonNull);
    }
}
