package com.quantumsoul.binarymod.world.biomes.feature;

import com.quantumsoul.binarymod.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WireTreeFeature extends Feature<NoFeatureConfig>
{
    public WireTreeFeature()
    {
        super(NoFeatureConfig.field_236558_a_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        //SIZE
        int nodes = 3;
        int length = 2;
        int deco = 1;

        //SET
        BlockPos[] basePos = new BlockPos[]{pos.up(3), pos.up(3)};
        int[] baseDir = new int[][]{{0, 1}, {0, 2}, {1, 2}}[rand.nextInt(3)];
        int[] baseLength = new int[]{rand.nextInt(length) + 2, rand.nextInt(length) + 2};
        boolean xz = rand.nextBoolean();

        //BUILD
        buildBranch(reader, pos, 1, 2, xz);
        buildNode(reader, pos, 1, 2, xz);
        buildBranch(reader, basePos[0], baseDir[0], baseLength[0], xz);
        buildBranch(reader, basePos[1], baseDir[1], baseLength[1], xz);

        BlockPos[] oldPos = basePos;
        int[] oldDir = baseDir;
        int[] oldLength = baseLength;

        boolean[] stop = new boolean[]{false, false};
        for (int i = 0; i < nodes; i++)
        {
            for (int side = 0; side < 2; side++)
            {
                if (stop[0] && stop[1])
                    break;

                if (!stop[side])
                {
                    int branches = rand.nextInt(4);
                    if (branches == 0 || !buildNode(reader, oldPos[side], oldDir[side], oldLength[side], xz))
                        stop[side] = true;
                    else
                    {
                        BlockPos newPos = calcPos(oldPos[side], oldDir[side], oldLength[side], xz);
                        int newDir = calcDir(oldDir[side], rand.nextInt(3));
                        int newLength = rand.nextInt(length) + 2;

                        if (!buildBranch(reader, newPos, newDir, newLength, xz))
                            stop[side] = true;

                        List<Integer> dirs = new LinkedList<>(Arrays.asList(0, 1, 2, 3));
                        dirs.remove(Integer.valueOf((oldDir[side] + 2) % 4));
                        dirs.remove(Integer.valueOf(newDir));
                        for (int j = 2; j <= branches; j++)
                        {
                            int dir = dirs.get(rand.nextInt(dirs.size()));
                            dirs.remove(Integer.valueOf(dir));

                            if (!buildBranch(reader, newPos, dir, deco, xz))
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

    private boolean buildNode(IWorld reader, BlockPos pos, int dir, int length, boolean xz)
    {
        BlockPos nextPos = calcPos(pos, dir, length, xz);
        if (reader.getBlockState(nextPos) == Blocks.AIR.getDefaultState())
        {
            reader.setBlockState(nextPos, BlockInit.BINARY_BLOCK.get().getDefaultState(), 2);
            return true;
        } else if (reader.getBlockState(nextPos).getBlock() == BlockInit.WIRE_BLOCK.get())
            reader.setBlockState(nextPos, BlockInit.BINARY_BLOCK.get().getDefaultState(), 2);

        return false;
    }

    private boolean buildBranch(IWorld reader, BlockPos pos, int dir, int length, boolean xz)
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

            if (reader.getBlockState(nextPos) == Blocks.AIR.getDefaultState())
                reader.setBlockState(nextPos, state, 2);
            else
            {
                if (reader.getBlockState(nextPos).getBlock() == state.getBlock()) ;
                reader.setBlockState(nextPos, BlockInit.BINARY_BLOCK.get().getDefaultState(), 2);

                return false;
            }
        }
        return true;
    }
}
