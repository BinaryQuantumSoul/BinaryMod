package com.quantumsoul.binarymod.world.biomes;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.world.biomes.feature.BinDimBiomeFeatures;
import com.quantumsoul.binarymod.world.biomes.surfacebuilder.HexSurfaceBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class HexBiome extends Biome
{
    public HexBiome()
    {
        super(new Builder().surfaceBuilder(new HexSurfaceBuilder(SurfaceBuilderConfig::deserialize), new SurfaceBuilderConfig(BlockInit.HEX_BLUE.get().getDefaultState(), BlockInit.HEX_GREEN.get().getDefaultState(), null))
                .precipitation(Biome.RainType.NONE)
                .category(Category.NONE)
                .depth(0.1F)
                .downfall(0F)
                .scale(0.02F)
                .temperature(1.5F)
                .waterColor(0x000000).waterFogColor(0x000000)
                .parent(null));

        BinDimBiomeFeatures.addOres(this);
        BinDimBiomeFeatures.addStructures(this);
    }
}
