package com.quantumsoul.binarymod.world.dimension;

import com.quantumsoul.binarymod.init.DimensionInit;
import com.quantumsoul.binarymod.world.WorldUtils;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.storage.DimensionSavedDataManager;

import javax.annotation.Nullable;

public class BinaryDimension extends Dimension
{
    private static final int WORLD_HEIGHT = 128;
    private static final int SEA_LEVEL = 64;

    public BinaryDimension(World world, DimensionType dimensionType)
    {
        super(world, dimensionType, 0.3F);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator()
    {
        return new BinDimChunkGenerator(this.world, new BinDimBiomeProvider(this.world), WORLD_HEIGHT);
    }

    @Nullable
    @Override
    public MusicTicker.MusicType getMusicType()
    {
        return MusicTicker.MusicType.GAME;
    }

    @Override
    public boolean canRespawnHere()
    {
        //todo decide
        return true;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid)
    {
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid)
    {
        return null;
    }

    public static void teleport(ServerPlayerEntity player)
    {
        // todo? write pos, dimension and spawn point
        BlockPos spawnPos = WorldUtils.teleportPlayer(player, DimensionInit.DIM_BINARY_TYPE, player.getPosition());
        player.setSpawnPoint(spawnPos, true, false, DimensionInit.DIM_BINARY_TYPE);
    }

    public static void teleportBack(ServerPlayerEntity player)
    {
        // todo? read pos, dimension and spawn point
        WorldUtils.teleportPlayer(player, DimensionType.OVERWORLD, player.getPosition());
    }

    @Override
    public int getHeight()
    {
        return WORLD_HEIGHT;
    }

    @Override
    public int getSeaLevel()
    {
        return SEA_LEVEL;
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.5F;
    }

    @Override
    public boolean doesXZShowFog(int x, int z)
    {
        return false;
    }

    @Override
    public Vec3d getFogColor(float celestialAngle, float partialTicks)
    {
        return new Vec3d(0, 0, 0);
    }
}
