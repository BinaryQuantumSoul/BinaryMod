package com.quantumsoul.binarymod.world.biomes.feature;

import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.EntityInit;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;

public class BinDimBiomeFeatures
{
    private static final Placement<ChanceConfig> LESS_THAN_ONE_PLACEMENT = new LessThanOnePlacement();
    private static final Placement<ChanceConfig> MULTI_LEVEL_PLACEMENT = new MultiLevelPlacement();
    private static final Feature<BugVirusConfig> BUG_VIRUS_FEATURE = new BugVirusFeature();
    private static final Feature<NoFeatureConfig> BIG_WIRE = new BigWireFeature();
    private static final Feature<NoFeatureConfig> WIRE_TREE = new WireTreeFeature();

    public static void addOres(BiomeGenerationSettings.Builder builder)
    {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(IsBinDimBlockRuleTest.INSTANCE, BlockInit.CODE_BLOCK.get().getDefaultState(), 6)).range(128).square());
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(IsBinDimBlockRuleTest.INSTANCE, BlockInit.MYSTERY_BOX.get().getDefaultState(), 4)).range(128).square().func_242731_b(1));
    }

    public static void addBugVirus(BiomeGenerationSettings.Builder builder)
    {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.VIRUS_BLOCK.get().getDefaultState(), 7)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new ChanceConfig(8))));
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.BUG_BLOCK.get().getDefaultState(), 6)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new ChanceConfig(12))));
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.VIRUS_DEAD_BLOCK.get().getDefaultState(), 5)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new ChanceConfig(16))));
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.VIRUS_BUG_BLOCK.get().getDefaultState(), 5)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new ChanceConfig(20))));
    }

    public static void addStructures(BiomeGenerationSettings.Builder builder)
    {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, BIG_WIRE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new ChanceConfig(50))));
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, WIRE_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MULTI_LEVEL_PLACEMENT.configure(new ChanceConfig(30))));
    }

    public static void addManyWires(BiomeGenerationSettings.Builder builder)
    {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, BIG_WIRE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new ChanceConfig(10))));
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, WIRE_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MULTI_LEVEL_PLACEMENT.configure(new ChanceConfig(1))));
    }

    public static void addBinDimSpawns(MobSpawnInfo.Builder builder)
    {
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityInit.ONE.get(), 75, 3, 5));
        builder.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityInit.ZERO.get(), 75, 3, 5));

        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityInit.BUG.get(), 25, 2, 3));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityInit.VIRUS.get(), 13, 1, 1));
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityInit.WORM.get(), 5, 1, 2));
    }

    public static void addVoidSpawns(MobSpawnInfo.Builder builder)
    {
        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityInit.VOID_SOUL.get(), 15, 1, 1));
    }
}
