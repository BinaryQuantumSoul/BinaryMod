package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.world.biomes.BinaryBiome;
import com.quantumsoul.binarymod.world.biomes.HexBiome;
import com.quantumsoul.binarymod.world.biomes.VoidBiome;
import com.quantumsoul.binarymod.world.biomes.WireBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BiomeInit
{
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, BinaryMod.MOD_ID);

    public static final RegistryObject<Biome> BINARY_BIOME = BIOMES.register("binary_biome", BinaryBiome::new);
    public static final RegistryObject<Biome> HEX_BIOME = BIOMES.register("hex_biome", HexBiome::new);
    public static final RegistryObject<Biome> VOID_BIOME = BIOMES.register("void_biome", VoidBiome::new);
    public static final RegistryObject<Biome> WIRE_BIOME = BIOMES.register("wire_biome", WireBiome::new);
}
