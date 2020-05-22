package com.quantumsoul.binarymod.world;

import com.quantumsoul.binarymod.BinaryMod;
import com.quantumsoul.binarymod.block.HexBlock;
import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.Supplier;

public class WorldUtils
{
    //TELEPORTING
    public static BlockPos teleportPlayer(ServerPlayerEntity player, DimensionType destinationType, BlockPos destinationPos)
    {
        if(destinationType == null)
            BinaryMod.LOGGER.error(destinationType.toString());

        //LOAD WORLD
        ServerWorld nextWorld = player.getServer().getWorld(destinationType);
        IChunk chunk = nextWorld.getChunk(destinationPos);
        int x = destinationPos.getX();
        int z = destinationPos.getZ();
        int maxHeight = nextWorld.getMaxHeight();

        //FIND SPAWN
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for(int y = maxHeight; y >= 0; y--)
        {
            pos.setPos(x, y-1, z);
            BlockState state = chunk.getBlockState(pos);
            if(state != Blocks.AIR.getDefaultState() && state.getBlockHardness(nextWorld, pos) != -1F)
            {
                pos.setPos(x, y, z);
                if (chunk.getBlockState(pos) == Blocks.AIR.getDefaultState())
                {
                    pos.setPos(x, y+1, z);
                    if (chunk.getBlockState(pos) == Blocks.AIR.getDefaultState())
                    {
                        player.teleport(nextWorld, x, y, z, player.rotationYaw, player.rotationPitch);
                        return new BlockPos(x, y, z);
                    }
                }
            }
        }

        //CREATE 3x3 SPAWN
        int y = maxHeight / 2;
        pos.setPos(x, y, z);
        BlockState biomeTopBlock = nextWorld.getBiome(pos).getSurfaceBuilderConfig().getTop();
        for(int i = x - 1; i <= x + 1; i++)
        {
            for(int j = z - 1; j <= z + 1; j++)
            {
                pos.setPos(i, y-1, j);
                nextWorld.setBlockState(pos, biomeTopBlock);
                for(int y1 = y; y1 <= y + 2; y1++)
                {
                    pos.setPos(i, y1, j);
                    nextWorld.setBlockState(pos, Blocks.AIR.getDefaultState());
                }
            }
        }
        player.teleport(nextWorld, pos.getX(), pos.getY(), pos.getZ(), player.rotationYaw, player.rotationPitch);
        return pos;
    }

    //GROUND CALCULATING
    private static int getGroundLevelFrom(IWorld worldIn, int x, int z, int yStart, boolean isAir)
    {
        int level = yStart;
        int yStop = worldIn.getMaxHeight();
        if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000)
        {
            if (worldIn.chunkExists(x >> 4, z >> 4))
            {
                BlockPos.Mutable pos = new BlockPos.Mutable(x, yStart, z);
                for (int y = yStart; isAir == (worldIn.getBlockState(pos) == Blocks.AIR.getDefaultState()); y++)
                {
                    pos.setPos(x, y, z);
                    level = y;
                    if(y == yStop)
                        return yStart - 1;
                }
            }
        }
        else
            return worldIn.getSeaLevel() + 1;

        return level - 1;
    }

    public static int getNthGroundLevel(IWorld worldIn, int x, int z, int n)
    {
        int base = 0;
        int old = 0;
        int level = 0;
        for (int i = 0; i <= n; i++)
        {
            old = level;
            level = getGroundLevelFrom(worldIn, x, z, level+1, true);
            if(level == old && level != 0)
                return level;

            old = level;
            level = getGroundLevelFrom(worldIn, x, z, level+1, false);
            if(level == old)
                return base;
            base = level;
        }
        return level;
    }

    public static int getGroundLevel(IWorld worldIn, int x, int z)
    {
        return getGroundLevelFrom(worldIn, x, z, 0, false);
    }

    //MOBS
    public static boolean canBinDimAnimalSpawn(EntityType<? extends MobEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random)
    {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        return isBinDimBlock(block);
    }

    public static boolean canBinDimMonsterSpawn(EntityType<? extends MobEntity> entity, IWorld worldIn, SpawnReason reason, BlockPos pos, Random random)
    {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && (reason == SpawnReason.SPAWNER || isBinDimBlock(block));
    }

    public static boolean onBinDimLivingFall(IWorld worldIn, BlockPos pos, Supplier<Boolean> def)
    {
        return worldIn.getBlockState(pos.down()) == BlockInit.ON_BINARY_BLOCK.get().getDefaultState() ? def.get() : false;
    }

    //BLOCK CHECKING
    public static boolean isBinDimBlock(Block block)
    {
        return block == BlockInit.BINARY_BLOCK.get().getBlock() || block instanceof HexBlock;
    }
}
