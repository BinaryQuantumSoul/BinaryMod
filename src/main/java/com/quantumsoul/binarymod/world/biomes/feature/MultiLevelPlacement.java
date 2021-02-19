package com.quantumsoul.binarymod.world.biomes.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.quantumsoul.binarymod.util.WorldUtils.getNthGroundLevel;
import static com.quantumsoul.binarymod.util.WorldUtils.getWorldFromWorldDecoratingHelper;

public class MultiLevelPlacement extends Placement<ChanceConfig>
{
    public MultiLevelPlacement()
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
            int y = getNthGroundLevel(getWorldFromWorldDecoratingHelper(helper), x, z, rand.nextInt(3));
            BlockPos nextPos = new BlockPos(x, y, z);
            return nextPos.getY() != 0 ? nextPos : null;
        }).filter(Objects::nonNull);
    }
}
