package com.quantumsoul.binarymod.world.biomes;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.GenerationInit;
import com.quantumsoul.binarymod.world.biomes.feature.BinDimBiomeFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.util.Lazy;

public class Biomes
{
    private static final Lazy<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> BINARY_SURFACE_BUILDER = () -> GenerationInit.BINARY_SURFACE_BUILDER.get().func_242929_a(new SurfaceBuilderConfig(BlockInit.BINARY_BLOCK.get().getDefaultState(), BlockInit.ON_BINARY_BLOCK.get().getDefaultState(), null));
    private static final Lazy<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> HEX_SURFACE_BUILDER = () -> GenerationInit.HEX_SURFACE_BUILDER.get().func_242929_a(new SurfaceBuilderConfig(BlockInit.HEX_BLUE.get().getDefaultState(), BlockInit.HEX_GREEN.get().getDefaultState(), null));
    private static final Lazy<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> VOID_SURFACE_BUILDER = () -> GenerationInit.VOID_SURFACE_BUILDER.get().func_242929_a(new SurfaceBuilderConfig(BlockInit.VOID_BLOCK.get().getDefaultState(), BlockInit.VOID_BLOCK.get().getDefaultState(), null));

    public static Biome makeBinaryBiome()
    {
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        BinDimBiomeFeatures.addBinDimSpawns(spawns);

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder();
        gen.withSurfaceBuilder(BINARY_SURFACE_BUILDER::get);

        BinDimBiomeFeatures.addOres(gen);
        BinDimBiomeFeatures.addBugVirus(gen);
        BinDimBiomeFeatures.addStructures(gen);

        int color = 0x00ff00;

        return new Biome.Builder()
            .precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1F).downfall(0F).scale(0.02F).temperature(1.5F)
            .setEffects(new BiomeAmbience.Builder().setWaterColor(color).setWaterFogColor(color).setFogColor(color).withSkyColor(color).build())
            .withMobSpawnSettings(spawns.copy())
            .withGenerationSettings(gen.build()).build();
    }

    public static Biome makeWireBiome()
    {
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        BinDimBiomeFeatures.addBinDimSpawns(spawns);

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder();
        gen.withSurfaceBuilder(BINARY_SURFACE_BUILDER::get);

        BinDimBiomeFeatures.addOres(gen);
        BinDimBiomeFeatures.addManyWires(gen);

        int color = 0x00ff00;

        return new Biome.Builder()
            .precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1F).downfall(0F).scale(0.02F).temperature(1.5F)
            .setEffects(new BiomeAmbience.Builder().setWaterColor(color).setWaterFogColor(color).setFogColor(color).withSkyColor(color).build())
            .withMobSpawnSettings(spawns.copy())
            .withGenerationSettings(gen.build()).build();
    }

    public static Biome makeHexBiome()
    {
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        BinDimBiomeFeatures.addBinDimSpawns(spawns);

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder();
        gen.withSurfaceBuilder(HEX_SURFACE_BUILDER::get);

        BinDimBiomeFeatures.addOres(gen);
        BinDimBiomeFeatures.addStructures(gen);

        int color = 0x000000;

        return new Biome.Builder()
            .precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1F).downfall(0F).scale(0.02F).temperature(1.5F)
            .setEffects(new BiomeAmbience.Builder().setWaterColor(color).setWaterFogColor(color).setFogColor(color).withSkyColor(color).build())
            .withMobSpawnSettings(spawns.copy())
            .withGenerationSettings(gen.build()).build();
    }

    public static Biome makeVoidBiome()
    {
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        BinDimBiomeFeatures.addVoidSpawns(spawns);

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder();
        gen.withSurfaceBuilder(VOID_SURFACE_BUILDER::get);

        int color = 0xffffff;

        return new Biome.Builder()
            .precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1F).downfall(0F).scale(0.02F).temperature(1.5F)
            .setEffects(new BiomeAmbience.Builder().setWaterColor(color).setWaterFogColor(color).setFogColor(color).withSkyColor(color).build())
            .withMobSpawnSettings(spawns.copy())
            .withGenerationSettings(gen.build()).build();
    }
}
