package com.quantumsoul.binarymod.world.biomes.feature;

import com.mojang.datafixers.Dynamic;
import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class WireTreeFeature extends Feature<NoFeatureConfig>
{
    public WireTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn)
    {
        super(configFactoryIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config)
    {
        //SIZE
        int nodes = 3;
        int length = 2;
        int deco = 1;

        //SET
        BlockPos[] basePos = new BlockPos[]{pos.up(3), pos.up(3)};
        int[] baseDir = new int[][]{{0, 1}, {0, 2}, {1, 2}}[random.nextInt(3)];
        int[] baseLength = new int[]{random.nextInt(length) + 2, random.nextInt(length) + 2};
        boolean xz = random.nextBoolean();

        //BUILD
        buildBranch(worldIn, pos, 1, 2, xz);
        buildNode(worldIn, pos, 1, 2, xz);
        buildBranch(worldIn, basePos[0], baseDir[0], baseLength[0], xz);
        buildBranch(worldIn, basePos[1], baseDir[1], baseLength[1], xz);

        BlockPos[] oldPos = basePos;
        int[] oldDir = baseDir;
        int[] oldLength = baseLength;

        boolean[] stop = new boolean[]{false, false};
        for (int i = 0; i < nodes; i++)
        {
            for (int side = 0; side < 2; side++)
            {
                if(stop[0] && stop[1])
                    break;

                if(!stop[side])
                {
                    int branches = random.nextInt(4);
                    if (branches == 0 || !buildNode(worldIn, oldPos[side], oldDir[side], oldLength[side], xz))
                        stop[side] = true;
                    else
                    {
                        BlockPos newPos = calcPos(oldPos[side], oldDir[side], oldLength[side], xz);
                        int newDir = calcDir(oldDir[side], random.nextInt(3));
                        int newLength = random.nextInt(length) + 2;

                        if(!buildBranch(worldIn, newPos, newDir, newLength, xz))
                            stop[side] = true;

                        List<Integer> dirs = new LinkedList<>(Arrays.asList(0, 1, 2, 3));
                        dirs.remove(Integer.valueOf((oldDir[side] + 2) % 4));
                        dirs.remove(Integer.valueOf(newDir));
                        for(int j = 2; j <= branches; j++)
                        {
                            int dir = dirs.get(random.nextInt(dirs.size()));
                            dirs.remove(Integer.valueOf(dir));

                            if(!buildBranch(worldIn, newPos, dir, deco, xz))
                                stop[side] = true;
                        }

                        oldPos[side] = newPos;
                        oldDir[side] = newDir;
                        oldLength[side] = newLength;
                    }
                }
            }
        }
        return true;
    }

    private int calcDir(int old, int k)
    {
        List<Integer> dirs = new LinkedList<>(Arrays.asList(0, 1, 2, 3));
        dirs.remove(Integer.valueOf((old + 2) % 4));
        return dirs.get(k);
    }
    
    private BlockPos calcPos(BlockPos pos, int dir, int length, boolean xz)
    {
        int distance = length + 1;
        if (dir == 0)
            return xz ? pos.west(distance) : pos.north(distance);
        else if (dir == 1)
            return pos.up(distance);
        else if (dir == 2)
            return xz ? pos.east(distance) : pos.south(distance);
        else
            return pos.down(distance);
    }

    private boolean buildNode(IWorld worldIn, BlockPos pos, int dir, int length, boolean xz)
    {
        BlockPos nextPos = calcPos(pos, dir, length, xz);
        if (worldIn.getBlockState(nextPos) == Blocks.AIR.getDefaultState())
        {
            worldIn.setBlockState(nextPos, BlockInit.BINARY_BLOCK.get().getDefaultState(), 2);
            return true;
        }
        else if (worldIn.getBlockState(nextPos).getBlock() == BlockInit.WIRE_BLOCK.get())
            worldIn.setBlockState(nextPos, BlockInit.BINARY_BLOCK.get().getDefaultState(), 2);

        return false;
    }

    private boolean buildBranch(IWorld worldIn, BlockPos pos, int dir, int length, boolean xz)
    {
        //WIRE STATE BY DIRECTION
        BlockState state = BlockInit.WIRE_BLOCK.get().getDefaultState();
        if (dir == 0 || dir == 2)
            state = state.with(BlockStateProperties.AXIS, xz ? Direction.Axis.X : Direction.Axis.Z);
        else
            state = state.with(BlockStateProperties.AXIS, Direction.Axis.Y);

        //BUILD
        for (int i = 0; i < length; i++)
        {
            BlockPos nextPos = calcPos(pos, dir, i, xz);

            if (worldIn.getBlockState(nextPos) == Blocks.AIR.getDefaultState())
                worldIn.setBlockState(nextPos, state, 2);
            else
            {
                if (worldIn.getBlockState(nextPos).getBlock() == state.getBlock());
                    worldIn.setBlockState(nextPos, BlockInit.BINARY_BLOCK.get().getDefaultState(), 2);

                return false;
            }
        }
        return true;
    }
}
