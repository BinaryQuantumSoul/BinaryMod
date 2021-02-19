package com.quantumsoul.binarymod.init;

import com.quantumsoul.binarymod.BinaryMod;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class DimensionInit
{
    public static final RegistryKey<DimensionType> BINARY_DIMENSION_TYPE = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(BinaryMod.MOD_ID, "binary_dimension"));
    public static final RegistryKey<World> BINARY_DIMENSION = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(BinaryMod.MOD_ID, "binary_dimension"));
}
