package com.quantumsoul.binarymod.world.dimension;

import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.spawner.WorldEntitySpawner;

import java.util.List;
import java.util.Random;

//I DON'T UNDERSTAND ANYTHING ABOUT GENERATION
public class BinDimChunkGenerator extends NoiseChunkGenerator<BinDimGenSettings>
{
    private final double[] field_222573_h = Util.make(new double[this.noiseSizeY()], (adouble) ->
    {
        for (int i = 0; i < this.noiseSizeY(); ++i)
        {
            adouble[i] = Math.cos((double) i * Math.PI * 6.0D / (double) this.noiseSizeY()) * 2.0D;
            double d0 = (double) i;
            if (i > this.noiseSizeY() / 2)
            {
                d0 = (double) (this.noiseSizeY() - 1 - i);
            }

            if (d0 < 4.0D)
            {
                d0 = 4.0D - d0;
                adouble[i] -= d0 * d0 * d0 * 10.0D;
            }
        }
    });
    private static final float[] field_222576_h = Util.make(new float[25], (afloat) ->
    {
        for (int i = -2; i <= 2; ++i)
        {
            for (int j = -2; j <= 2; ++j)
            {
                float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
                afloat[i + 2 + (j + 2) * 5] = f;
            }
        }
    });

    private final OctavesNoiseGenerator depthNoise;


    public BinDimChunkGenerator(World world, BiomeProvider biomeProvider, int worldHeight)
    {
        super(world, biomeProvider, 4, 8, worldHeight, BinDimGenSettings.create(), false);

        this.randomSeed.skip(2620);
        this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);
    }

    @Override
    public int getGroundHeight()
    {
        return 32;
    }

    @Override
    public int getSeaLevel()
    {
        return 0;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos)
    {
        return super.getPossibleCreatures(creatureType, pos);
    }

    @Override
    public void spawnMobs(WorldGenRegion region)
    {
        int i = region.getMainChunkX();
        int j = region.getMainChunkZ();
        Biome biome = region.getBiome((new ChunkPos(i, j)).asBlockPos());
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        sharedseedrandom.setDecorationSeed(region.getSeed(), i << 4, j << 4);
        WorldEntitySpawner.performWorldGenSpawning(region, biome, i, j, sharedseedrandom);
    }

    @Override
    protected void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ)
    {
        double d0 = 684.412D;
        double d1 = 2053.236D;
        double d2 = 8.555150000000001D;
        double d3 = 34.2206D;
        int i = 3;
        int j = -10;
        this.calcNoiseColumn(noiseColumn, noiseX, noiseZ, d0, d1, d2, d3, i, j);
    }

    //WORLD SHAPE
    @Override
    protected double func_222545_a(double p1, double p2, int p3)
    {
        return this.field_222573_h[p3];
    }

    @Override
    protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ)
    {
        double[] adouble = new double[2];
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;
        int i = 2;
        int j = this.getSeaLevel();
        float f3 = this.biomeProvider.getNoiseBiome(noiseX, j, noiseZ).getDepth();

        for (int k = -2; k <= 2; ++k)
        {
            for (int l = -2; l <= 2; ++l)
            {
                Biome biome = this.biomeProvider.getNoiseBiome(noiseX + k, j, noiseZ + l);
                float f4 = biome.getDepth();
                float f5 = biome.getScale();

                float f6 = field_222576_h[k + 2 + (l + 2) * 5] / (f4 + 2.0F);
                if (biome.getDepth() > f3)
                {
                    f6 /= 2.0F;
                }

                f += f5 * f6;
                f1 += f4 * f6;
                f2 += f6;
            }
        }

        f = f / f2;
        f1 = f1 / f2;
        f = f * 0.9F + 0.1F;
        f1 = (f1 * 4.0F - 1.0F) / 8.0F;
        adouble[0] = (double) f1 + this.getNoiseDepthAt(noiseX, noiseZ);
        adouble[1] = (double) f;
        return adouble;
    }

    private double getNoiseDepthAt(int noiseX, int noiseZ)
    {
        double d0 = this.depthNoise.getValue((double) (noiseX * 200), 10.0D, (double) (noiseZ * 200), 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
        if (d0 < 0.0D)
        {
            d0 = -d0 * 0.3D;
        }

        d0 = d0 * 3.0D - 2.0D;
        if (d0 < 0.0D)
        {
            d0 = d0 / 28.0D;
        } else
        {
            if (d0 > 1.0D)
            {
                d0 = 1.0D;
            }

            d0 = d0 / 40.0D;
        }

        return d0;
    }

    @Override
    protected void makeBedrock(IChunk chunkIn, Random rand)
    {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int i = chunkIn.getPos().getXStart();
        int j = chunkIn.getPos().getZStart();
        BinDimGenSettings t = this.getSettings();
        int k = t.getBedrockFloorHeight();
        int l = t.getBedrockRoofHeight();

        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(i, 0, j, i + 15, 0, j + 15))
        {
            for (int i1 = l; i1 >= l - 4; --i1)
            {
                if (i1 >= l - rand.nextInt(5))
                {
                    chunkIn.setBlockState(blockpos$mutable.setPos(blockpos.getX(), i1, blockpos.getZ()), BlockInit.FIREWALL_BLOCK.get().getDefaultState(), false);
                }
            }

            for (int j1 = k + 4; j1 >= k; --j1)
            {
                if (j1 <= k + rand.nextInt(5))
                {
                    chunkIn.setBlockState(blockpos$mutable.setPos(blockpos.getX(), j1, blockpos.getZ()), BlockInit.FIREWALL_BLOCK.get().getDefaultState(), false);
                }
            }
        }
    }
}
