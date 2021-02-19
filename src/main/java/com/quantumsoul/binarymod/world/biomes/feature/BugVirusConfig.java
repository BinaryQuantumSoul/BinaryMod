package com.quantumsoul.binarymod.world.biomes.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BugVirusConfig implements IFeatureConfig
{
    public static final Codec<BugVirusConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("state").forGetter(config -> config.state),
            Codec.INT.fieldOf("radius").forGetter(config -> config.radius)
    ).apply(instance, BugVirusConfig::new));

    public final BlockState state;
    public final int radius;

    public BugVirusConfig(BlockState state, int radiusIn)
    {
        this.state = state;
        this.radius = radiusIn;
    }
}