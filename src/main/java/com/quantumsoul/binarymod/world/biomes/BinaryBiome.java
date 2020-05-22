package com.quantumsoul.binarymod.world.biomes;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.world.biomes.feature.BinDimBiomeFeatures;
import com.quantumsoul.binarymod.world.biomes.surfacebuilder.BinarySurfaceBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BinaryBiome extends Biome
{
    public BinaryBiome()
    {
        super(new Builder().surfaceBuilder(new BinarySurfaceBuilder(SurfaceBuilderConfig::deserialize), new SurfaceBuilderConfig(BlockInit.BINARY_BLOCK.get().getDefaultState(), BlockInit.ON_BINARY_BLOCK.get().getDefaultState(), null))
                .precipitation(Biome.RainType.NONE)
                .category(Category.NONE)
                .depth(0.1F)
                .downfall(0F)
                .scale(0.02F)
                .temperature(1.5F)
                .waterColor(0x00FF00).waterFogColor(0x00FF00)
                .parent(null));

        BinDimBiomeFeatures.addOres(this);
        BinDimBiomeFeatures.addBugVirus(this);
        BinDimBiomeFeatures.addStructures(this);
    }
}
