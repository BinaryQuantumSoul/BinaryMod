package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.entity.VoidSoulEntity;
import com.quantumsoul.binarymod.entity.WormEntity;
import com.quantumsoul.binarymod.util.WorldUtils;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static com.quantumsoul.binarymod.init.BlockInit.BINARY_ORE;

public class GenerationInit
{
    public static void initOres()
    {
        for(Biome b : ForgeRegistries.BIOMES)
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BINARY_ORE.get().getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 48))));
    }

    public static void initSpawns()
    {
        //SPAWN PLACEMENT
        EntitySpawnPlacementRegistry.register(EntityInit.ONE.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimAnimalSpawn);
        EntitySpawnPlacementRegistry.register(EntityInit.ZERO.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimAnimalSpawn);

        EntitySpawnPlacementRegistry.register(EntityInit.BUG.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimMonsterSpawn);
        EntitySpawnPlacementRegistry.register(EntityInit.VIRUS.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimMonsterSpawn);
        EntitySpawnPlacementRegistry.register(EntityInit.WORM.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WorldUtils::canBinDimMonsterSpawn);

        EntitySpawnPlacementRegistry.register(EntityInit.VOID_SOUL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, VoidSoulEntity::canVoidSoulSpawn);


        //SPAWN BIOME
        BiomeInit.BIOMES.getEntries().stream().map(RegistryObject::get).filter(biome -> biome != BiomeInit.VOID_BIOME.get()).forEach(b ->
        {
            List<Biome.SpawnListEntry> creatures = b.getSpawns(EntityClassification.CREATURE);
            creatures.add(new Biome.SpawnListEntry(EntityInit.ONE.get(), 75, 3, 5));
            creatures.add(new Biome.SpawnListEntry(EntityInit.ZERO.get(), 75, 3, 5));

            List<Biome.SpawnListEntry> monsters = b.getSpawns(EntityClassification.MONSTER);
            monsters.add(new Biome.SpawnListEntry(EntityInit.BUG.get(), 25, 2, 3));
            monsters.add(new Biome.SpawnListEntry(EntityInit.VIRUS.get(), 13, 1, 1));
            monsters.add(new Biome.SpawnListEntry(EntityInit.WORM.get(), 5, 1, 2));
        });

        BiomeInit.VOID_BIOME.get().getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(EntityInit.VOID_SOUL.get(), 15, 1, 1));
    }
}
