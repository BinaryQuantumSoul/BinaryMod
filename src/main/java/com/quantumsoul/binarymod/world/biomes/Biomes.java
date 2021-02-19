package com.quantumsoul.binarymod.world.biomes;

import com.quantumsoul.binarymod.world.biomes.feature.BinDimBiomeFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;

public class Biomes
{
    public static Biome makeBinaryBiome()
    {
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        BinDimBiomeFeatures.addBinDimSpawns(spawns);

        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder();
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

        int color = 0xffffff;

        return new Biome.Builder()
            .precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1F).downfall(0F).scale(0.02F).temperature(1.5F)
            .setEffects(new BiomeAmbience.Builder().setWaterColor(color).setWaterFogColor(color).setFogColor(color).withSkyColor(color).build())
            .withMobSpawnSettings(spawns.copy())
            .withGenerationSettings(new BiomeGenerationSettings.Builder().build()).build();
    }
}
