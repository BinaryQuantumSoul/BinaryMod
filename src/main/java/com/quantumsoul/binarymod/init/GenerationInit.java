package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.compat.config.OreConfig;
import com.quantumsoul.binarymod.entity.VoidSoulEntity;
import com.quantumsoul.binarymod.util.WorldUtils;
import com.quantumsoul.binarymod.world.biomes.*;
import com.quantumsoul.binarymod.world.biomes.surfacebuilder.BinarySurfaceBuilder;
import com.quantumsoul.binarymod.world.biomes.surfacebuilder.HexSurfaceBuilder;
import com.quantumsoul.binarymod.world.biomes.surfacebuilder.VoidSurfaceBuilder;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.quantumsoul.binarymod.init.BlockInit.BINARY_ORE;

@Mod.EventBusSubscriber(modid = BinaryMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GenerationInit
{
    //DIMENSION
    public static final RegistryKey<World> BINARY_DIMENSION = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(BinaryMod.MOD_ID, "binary_dimension"));

    //SURFACE_BUILDERS
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, BinaryMod.MOD_ID);

    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> BINARY_SURFACE_BUILDER = SURFACE_BUILDERS.register("binary_surface_builder", () -> new BinarySurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> HEX_SURFACE_BUILDER = SURFACE_BUILDERS.register("hex_surface_builder", () -> new HexSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> VOID_SURFACE_BUILDER = SURFACE_BUILDERS.register("void_surface_builder", () -> new VoidSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));

    //BIOMES
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, BinaryMod.MOD_ID);

    public static final RegistryObject<Biome> BINARY_BIOME = BIOMES.register("binary_biome", Biomes::makeBinaryBiome);
    public static final RegistryObject<Biome> HEX_BIOME = BIOMES.register("hex_biome", Biomes::makeHexBiome);
    public static final RegistryObject<Biome> VOID_BIOME = BIOMES.register("void_biome", Biomes::makeVoidBiome);
    public static final RegistryObject<Biome> WIRE_BIOME = BIOMES.register("wire_biome", Biomes::makeWireBiome);

    //ORES
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void biomeLoading(final BiomeLoadingEvent event)
    {
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BINARY_ORE.get().getDefaultState(), 8)).range(48).square().func_242731_b(OreConfig.binaryOreCountRange.get()));
    }
}
