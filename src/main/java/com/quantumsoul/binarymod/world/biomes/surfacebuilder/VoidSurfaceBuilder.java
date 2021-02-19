package com.quantumsoul.binarymod.world.biomes.surfacebuilder;

import com.mojang.serialization.Codec;
import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class VoidSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
    private final static BlockState VOID = BlockInit.VOID_BLOCK.get().getDefaultState();

    public VoidSurfaceBuilder(Codec<SurfaceBuilderConfig> configCodec)
    {
        super(configCodec);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
    {
        int i = x & 15;
        int j = z & 15;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int y = 127; y >= 0; y--)
        {
            mutable.setPos(i, y, j);
            BlockState tmp = chunkIn.getBlockState(mutable);
            if (tmp != Blocks.AIR.getDefaultState() && tmp.getBlock() != BlockInit.FIREWALL_BLOCK.get())
                chunkIn.setBlockState(mutable, VOID, false);
        }
    }
}
