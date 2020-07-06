package com.quantumsoul.binarymod.world.dimension;

import com.google.common.collect.ImmutableSet;
import com.quantumsoul.binarymod.init.BiomeInit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.IslandLayer;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.LongFunction;

//I DON'T UNDERSTAND ANYTHING ABOUT GENERATION
public class BinDimBiomeProvider extends BiomeProvider
{
    private final Layer genBiomes;
    private static final Set<Biome> biomes = ImmutableSet.of(BiomeInit.BINARY_BIOME.get(), BiomeInit.HEX_BIOME.get(), BiomeInit.VOID_BIOME.get(), BiomeInit.WIRE_BIOME.get());
    private final SimplexNoiseGenerator generator;

    public BinDimBiomeProvider(World world)
    {
        super(biomes);
        this.genBiomes = makeTheWorld(world.getSeed());
        this.generator = new SimplexNoiseGenerator(world.rand);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z)
    {
        return this.genBiomes.func_215738_a(x, z);
    }

    //BIOMES SIZE
    private Layer makeTheWorld(long seed)
    {
        LongFunction<IExtendedNoiseRandom<LazyArea>> contextFactory = l -> new LazyAreaLayerContext(25, seed, l);

        IAreaFactory<LazyArea> parentLayer = IslandLayer.INSTANCE.apply(contextFactory.apply(1));
        IAreaFactory<LazyArea> biomeLayer = new BinDimLayer().apply(contextFactory.apply(200), parentLayer);
        biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1000), biomeLayer);
        biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1001), biomeLayer);
        biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1002), biomeLayer);
        biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1003), biomeLayer);
        biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1004), biomeLayer);
        biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1005), biomeLayer);

        return new Layer(biomeLayer);
    }

    //ADD BIOMES BY PROBABILITY
    private static class BinDimLayer implements IC0Transformer
    {
        @Override
        public int apply(INoiseRandom context, int value)
        {
            int rand = context.random(8);
            if (rand <= 3)
                return ((ForgeRegistry) ForgeRegistries.BIOMES).getID(BiomeInit.BINARY_BIOME.getId());
            else if (rand <= 5)
                return ((ForgeRegistry) ForgeRegistries.BIOMES).getID(BiomeInit.HEX_BIOME.getId());
            else if (rand <= 6)
                return ((ForgeRegistry) ForgeRegistries.BIOMES).getID(BiomeInit.VOID_BIOME.getId());
            else
                return ((ForgeRegistry) ForgeRegistries.BIOMES).getID(BiomeInit.WIRE_BIOME.getId());
        }
    }

    @Override
    public List<Biome> getBiomesToSpawnIn()
    {
        return Collections.singletonList(BiomeInit.BINARY_BIOME.get());
    }

    //NETHER-LIKE GENERATION
    @Override
    public float func_222365_c(int a, int b)
    {
        int i = a / 2;
        int j = b / 2;
        int k = a % 2;
        int l = b % 2;
        float f = 100.0F - MathHelper.sqrt((float) (a * a + b * b)) * 8.0F;
        f = MathHelper.clamp(f, -100.0F, 80.0F);

        for (int i1 = -12; i1 <= 12; ++i1)
        {
            for (int j1 = -12; j1 <= 12; ++j1)
            {
                long k1 = i + i1;
                long l1 = j + j1;
                if (k1 * k1 + l1 * l1 > 4096L && this.generator.getValue((double) k1, (double) l1) < (double) -0.9F)
                {
                    float f1 = (MathHelper.abs((float) k1) * 3439.0F + MathHelper.abs((float) l1) * 147.0F) % 13.0F + 9.0F;
                    float f2 = (float) (k - i1 * 2);
                    float f3 = (float) (l - j1 * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
                    f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
                    f = Math.max(f, f4);
                }
            }
        }

        return f;
    }
}
