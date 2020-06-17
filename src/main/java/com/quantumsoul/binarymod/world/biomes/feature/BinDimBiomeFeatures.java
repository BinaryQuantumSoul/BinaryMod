package com.quantumsoul.binarymod.world.biomes.feature;

import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;

import static com.quantumsoul.binarymod.util.WorldUtils.isBinDimBlock;

public class BinDimBiomeFeatures
{
    private static final OreFeatureConfig.FillerBlockType FILLER_BINARY = OreFeatureConfig.FillerBlockType.create("BINARY_DIM", "binary_dim", (state) -> state != null && isBinDimBlock(state.getBlock()));
    private static final Placement<FrequencyConfig> LESS_THAN_ONE_PLACEMENT = new LessThanOnePlacement(FrequencyConfig::deserialize);
    private static final Placement<FrequencyConfig> MULTI_LEVEL_PLACEMENT = new MultiLevelPlacement(FrequencyConfig::deserialize);
    private static final Feature<BugVirusConfig> BUG_VIRUS_FEATURE = new BugVirusFeature(BugVirusConfig::deserialize);
    private static final Feature<NoFeatureConfig> BIG_WIRE = new BigWireFeature(NoFeatureConfig::deserialize);
    private static final Feature<NoFeatureConfig> WIRE_TREE = new WireTreeFeature(NoFeatureConfig::deserialize);

    public static void addOres(Biome biome)
    {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(FILLER_BINARY, BlockInit.CODE_BLOCK.get().getDefaultState(), 6)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 128))));
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(FILLER_BINARY, BlockInit.MYSTERY_BOX.get().getDefaultState(), 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 128))));
    }

    public static void addBugVirus(Biome biome)
    {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.VIRUS_BLOCK.get().getDefaultState(), 7)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new FrequencyConfig(8))));
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.BUG_BLOCK.get().getDefaultState(), 6)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new FrequencyConfig(12))));
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.VIRUS_DEAD_BLOCK.get().getDefaultState(), 5)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new FrequencyConfig(16))));
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, BUG_VIRUS_FEATURE.withConfiguration(new BugVirusConfig(BlockInit.VIRUS_BUG_BLOCK.get().getDefaultState(), 5)).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new FrequencyConfig(20))));
    }

    public static void addStructures(Biome biome)
    {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, BIG_WIRE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new FrequencyConfig(50))));
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, WIRE_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MULTI_LEVEL_PLACEMENT.configure(new FrequencyConfig(30))));
    }

    public static void addManyWires(Biome biome)
    {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, BIG_WIRE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(LESS_THAN_ONE_PLACEMENT.configure(new FrequencyConfig(10))));
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, WIRE_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(MULTI_LEVEL_PLACEMENT.configure(new FrequencyConfig(1))));
    }
}
