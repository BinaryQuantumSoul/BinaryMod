package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.block.FactoryBlock;
import com.quantumsoul.binarymod.block.UpgradableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

import static com.quantumsoul.binarymod.world.WorldUtils.dropStacks;

public abstract class FactoryTileEntity extends AbstractMachineTileEntity implements ITickableTileEntity, IUpgradableMachine
{
    private static final int DOING_TIME = 1200;

    private final int maxLevel;
    private int level = 0;

    private int timer = 0;
    private boolean done = false;

    private IntegerProperty LEVEL;
    private boolean init = false;

    public FactoryTileEntity(TileEntityType<?> tileEntityTypeIn, int levels)
    {
        super(tileEntityTypeIn);
        this.maxLevel = levels - 1;
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void tick()
    {
        if(!init)
        {
            LEVEL = ((UpgradableBlock)world.getBlockState(pos).getBlock()).LEVEL;
            init = true;
        }

        if (!done)
        {
            timer++;

            if (timer >= DOING_TIME)
            {
                done = true;
                world.setBlockState(pos, getBlockState().with(FactoryBlock.DONE, done), 3);
                this.markDirty();
            }
        }
    }

    @Override
    public boolean upgrade()
    {
        if (level < maxLevel)
        {
            level++;
            world.setBlockState(pos, getBlockState().with(LEVEL, level), 3);
            reset();

            return true;
        }

        return false;
    }

    @Override
    public boolean execute(PlayerEntity player)
    {
        if (done)
        {
            doAction(player, level);
            reset();

            return true;
        }

        return false;
    }

    @Override
    public void setLevel(int lev)
    {
        level = lev;
    }

    @Override
    public void drop(BlockPos pos)
    {
        if(done)
        {
            List<ItemStack> drops = getDrops(level);
            if (drops != null)
                dropStacks(world, pos, drops);
        }
    }

    private void reset()
    {
        timer = 0;
        done = false;
        world.setBlockState(pos, getBlockState().with(FactoryBlock.DONE, done), 3);
        this.markDirty();
    }

    abstract void doAction(PlayerEntity player, int level);

    @Nullable
    abstract List<ItemStack> getDrops(int level);

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putInt("level", level);
        compound.putInt("timer", timer);
        compound.putBoolean("done", done);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        level = compound.getInt("level");
        timer = compound.getInt("timer");
        done = compound.getBoolean("done");
    }
}
