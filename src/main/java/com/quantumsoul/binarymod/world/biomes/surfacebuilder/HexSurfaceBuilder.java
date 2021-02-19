package com.quantumsoul.binarymod.world.biomes.surfacebuilder;

import com.mojang.serialization.Codec;
import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class HexSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
    private static final BlockState[] states = new BlockState[]{
            BlockInit.HEX_BLUE.get().getDefaultState(),
            BlockInit.HEX_RED.get().getDefaultState(),
            BlockInit.HEX_YELLOW.get().getDefaultState(),
            BlockInit.HEX_CYAN.get().getDefaultState(),
            BlockInit.HEX_MAGENTA.get().getDefaultState(),
            BlockInit.HEX_ORANGE.get().getDefaultState(),
            BlockInit.HEX_GREEN.get().getDefaultState(),
            BlockInit.HEX_ORANGE.get().getDefaultState(),
            BlockInit.HEX_MAGENTA.get().getDefaultState(),
            BlockInit.HEX_CYAN.get().getDefaultState(),
            BlockInit.HEX_YELLOW.get().getDefaultState(),
            BlockInit.HEX_RED.get().getDefaultState()};

    public HexSurfaceBuilder(Codec<SurfaceBuilderConfig> configCodec)
    {
        super(configCodec);
    }

    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
    {
        int i = x & 15;
        int j = z & 15;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int y = 127; y >= 0; y--)
        {
            mutable.setPos(i, y, j);
            if (chunkIn.getBlockState(mutable) == BlockInit.BINARY_BLOCK.get().getDefaultState())
            {
                chunkIn.setBlockState(mutable, states[y / 2 % 12], false);
            }
        }
    }
}
