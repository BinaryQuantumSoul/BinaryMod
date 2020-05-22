package com.quantumsoul.binarymod.world.biomes;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.world.biomes.surfacebuilder.VoidSurfaceBuilder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class VoidBiome extends Biome
{
    public VoidBiome()
    {
        super(new Builder().surfaceBuilder(new VoidSurfaceBuilder(SurfaceBuilderConfig::deserialize), new SurfaceBuilderConfig(BlockInit.VOID_BLOCK.get().getDefaultState(), BlockInit.VOID_BLOCK.get().getDefaultState(), null))
                .precipitation(Biome.RainType.NONE)
                .category(Category.NONE)
                .depth(0.1F)
                .downfall(0F)
                .scale(0.02F)
                .temperature(1.5F)
                .waterColor(0xFFFFFF).waterFogColor(0xFFFFFF)
                .parent(null));
    }
}
