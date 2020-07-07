package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.block.BoolBlock;
import com.quantumsoul.binarymod.util.MachineUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.quantumsoul.binarymod.util.WorldUtils.dropStacks;

public abstract class FactoryTileEntity extends UpgradableTileEntity implements IExecutableMachine, IDroppableMachine, ITickableTileEntity
{
    private static final int DOING_TIME = 1200;

    private int timer = 0;
    private boolean done = false;

    public FactoryTileEntity(TileEntityType<?> tileEntityTypeIn, MachineUtils.LevelInfo levels)
    {
        super(tileEntityTypeIn, levels);
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void tick()
    {
        if (!world.isRemote && !done)
        {
            timer++;

            if (timer >= DOING_TIME)
            {
                done = true;
                world.setBlockState(pos, getBlockState().with(BoolBlock.DONE, done), 2);
                this.markDirty();
            }
        }
    }

    @Override
    public boolean execute(ServerPlayerEntity player)
    {
        if (done)
        {
            doAction(player, level);
            upgradeResets();

            return true;
        }

        return false;
    }

    @Override
    public void drop(World worldIn, BlockPos pos)
    {
        if(done)
        {
            List<ItemStack> drops = getDrops(level);
            if (drops != null)
                dropStacks(worldIn, pos, drops);
        }
    }

    @Override
    protected void upgradeResets()
    {
        timer = 0;
        done = false;
        world.setBlockState(pos, getBlockState().with(BoolBlock.DONE, done), 2);
    }

    public boolean isDone()
    {
        return done;
    }

    abstract void doAction(PlayerEntity player, int level);

    @Nullable
    abstract List<ItemStack> getDrops(int level);

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putInt("timer", timer);
        compound.putBoolean("done", done);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        timer = compound.getInt("timer");
        done = compound.getBoolean("done");
    }
}
