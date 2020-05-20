package com.quantumsoul.binarymod.world.biomes.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BugVirusConfig implements IFeatureConfig
{
    public final BlockState state;
    public final int radius;

    public BugVirusConfig(BlockState state, int radiusIn)
    {
        this.state = state;
        this.radius = radiusIn;
    }

    public <T> Dynamic<T> serialize(DynamicOps<T> ops)
    {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("state"), BlockState.serialize(ops, this.state).getValue(), ops.createString("radius"), ops.createInt(this.radius))));
    }

    public static <T> BugVirusConfig deserialize(Dynamic<T> data)
    {
        BlockState blockstate = data.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        int rad = data.get("radius").asInt(0);
        return new BugVirusConfig(blockstate, rad);
    }
}