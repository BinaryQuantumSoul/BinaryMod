package com.quantumsoul.binarymod.util;

import com.quantumsoul.binarymod.block.HexBlock;
import com.quantumsoul.binarymod.init.BlockInit;
import com.quantumsoul.binarymod.init.DimensionInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class WorldUtils
{
    //TELEPORTING
    public static BlockPos teleportPlayer(ServerPlayerEntity player, RegistryKey<World> destination, BlockPos destinationPos)
    {
        if (destination == null)
            throw new IllegalArgumentException("Destination dimension key is null !");

        //LOAD WORLD
        ServerWorld nextWorld = player.getServer().getWorld(destination);
        nextWorld.getChunk(destinationPos);

        int x = destinationPos.getX();
        int z = destinationPos.getZ();
        int maxHeight = nextWorld.getHeight();

        //FIND SPAWN
        BlockPos.Mutable pos = new BlockPos.Mutable();
        boolean flag = false;
        int y = 0;

        for (int h = maxHeight; h >= 0; h--)
        {
            pos.setPos(x, h, z);

            if (!nextWorld.isAirBlock(pos.down()) && nextWorld.getBlockState(pos.down()).getBlockHardness(nextWorld, pos.down()) != -1F && nextWorld.isAirBlock(pos) && nextWorld.isAirBlock(pos.up()))
            {
                flag = true;
                y = h;
            }
        }

        //CREATE 3x3 SPAWN
        if (!flag)
        {
            y = maxHeight / 2;

            pos.setPos(x, y, z);
            BlockState biomeTopBlock = nextWorld.getBiome(pos).getGenerationSettings().getSurfaceBuilderConfig().getTop();
            for (int i = x - 1; i <= x + 1; i++)
            {
                for (int j = z - 1; j <= z + 1; j++)
                {
                    for (int d = -1; d <= 2; d++)
                    {
                        pos.setPos(i, y + d, j);
                        nextWorld.setBlockState(pos, d >= 0 ? Blocks.AIR.getDefaultState() : biomeTopBlock);
                    }
                }
            }
        }

        player.teleport(nextWorld, x, y, z, player.rotationYaw, player.rotationPitch);
        return new BlockPos(x, y, z);
    }

    public static void teleportToBinDim(ServerPlayerEntity player)
    {
        BlockPos spawnPos = teleportPlayer(player, DimensionInit.BINARY_DIMENSION, player.getPosition());
        //todo player.setSpawnPoint(spawnPos, true, true, DimensionInit.DIM_BINARY_TYPE);
    }

    public static void teleportFromBinDim(ServerPlayerEntity player)
    {
        teleportPlayer(player, World.OVERWORLD, player.getPosition());
    }

    //GROUND CALCULATING
    private static int getGroundLevelFrom(IWorld worldIn, int x, int z, int yStart, boolean isAir)
    {
        int level = yStart;
        int yStop = worldIn.getHeight();
        if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000)
        {
            if (worldIn.chunkExists(x >> 4, z >> 4))
            {
                BlockPos.Mutable pos = new BlockPos.Mutable(x, yStart, z);
                for (int y = yStart; isAir == (worldIn.getBlockState(pos) == Blocks.AIR.getDefaultState()); y++)
                {
                    pos.setPos(x, y, z);
                    level = y;
                    if (y == yStop)
                        return yStart - 1;
                }
            }
        } else
            return worldIn.getSeaLevel() + 1;

        return level - 1;
    }

    public static int getNthGroundLevel(IWorld worldIn, int x, int z, int n)
    {
        int base = 0;
        int old;
        int level = 0;
        for (int i = 0; i <= n; i++)
        {
            old = level;
            level = getGroundLevelFrom(worldIn, x, z, level + 1, true);
            if (level == old && level != 0)
                return level;

            old = level;
            level = getGroundLevelFrom(worldIn, x, z, level + 1, false);
            if (level == old)
                return base;
            base = level;
        }
        return level;
    }

    public static int getGroundLevel(IWorld worldIn, int x, int z)
    {
        return getGroundLevelFrom(worldIn, x, z, 0, false);
    }

    public static boolean isOnGround(Entity entity)
    {
        int x = MathHelper.floor(entity.getPosX());
        int y = MathHelper.floor(entity.getPosY() - 0.01);
        int z = MathHelper.floor(entity.getPosZ());

        BlockPos pos = new BlockPos(x, y, z);
        BlockState s = entity.world.getBlockState(pos);
        VoxelShape shape = s.getShape(entity.world, pos);
        if (shape.isEmpty())
            return false;

        AxisAlignedBB playerBox = entity.getBoundingBox();
        return !s.isAir(entity.world, pos) && playerBox.offset(0, -0.01, 0).intersects(shape.getBoundingBox().offset(pos));
    }

    //WORLD DECORATING HELPER
    private static final Field field_242889_a = ObfuscationReflectionHelper.findField(WorldDecoratingHelper.class, "field_242889_a");
    public static IWorld getWorldFromWorldDecoratingHelper(WorldDecoratingHelper wdh)
    {
        try
        {
            return ((ISeedReader)field_242889_a.get(wdh)).getWorld();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
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

    public static boolean neverSpawn(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, EntityType<?> entityType)
    {
        return false;
    }

    //BLOCK CHECKING
    public static boolean isBinDimBlock(Block block)
    {
        return block == BlockInit.BINARY_BLOCK.get().getBlock() || block instanceof HexBlock;
    }

    //LOOT
    public static void dropInventoryItems(World worldIn, BlockPos pos, IItemHandler items)
    {
        dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), items);
    }

    public static void dropInventoryItems(World worldIn, double x, double y, double z, IItemHandler items)
    {
        for (int i = 0; i < items.getSlots(); ++i)
            spawnItemStack(worldIn, x, y, z, items.getStackInSlot(i));
    }

    public static void dropStacks(World worldIn, BlockPos pos, List<ItemStack> stacks)
    {
        dropStacks(worldIn, pos.getX(), pos.getY(), pos.getZ(), stacks);
    }

    public static void dropStacks(World worldIn, double x, double y, double z, List<ItemStack> stacks)
    {
        for (ItemStack stack : stacks)
            spawnItemStack(worldIn, x, y, z, stack);
    }

    private static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack)
    {
        Random rand = worldIn.rand;

        double d0 = EntityType.ITEM.getWidth();
        double d1 = 1.0D - d0;
        double d2 = d0 / 2.0D;
        double d3 = Math.floor(x) + rand.nextDouble() * d1 + d2;
        double d4 = Math.floor(y) + rand.nextDouble() * d1;
        double d5 = Math.floor(z) + rand.nextDouble() * d1 + d2;

        while (!stack.isEmpty())
        {
            ItemEntity itementity = new ItemEntity(worldIn, d3, d4, d5, stack.split(rand.nextInt(21) + 10));
            double d = 0.05F;
            itementity.setMotion(rand.nextGaussian() * d, rand.nextGaussian() * d + 0.2F, rand.nextGaussian() * d);
            worldIn.addEntity(itementity);
        }
    }
}
