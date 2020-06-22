package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.block.UpgradableBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.TileEntityType;

public class UpgradableTileEntity extends MachineTileEntity implements IUpgradableMachine
{
    public final int maxLevel;
    public int level = 0;

    public IntegerProperty LEVEL;
    private boolean init = false;

    public UpgradableTileEntity(TileEntityType<?> tileEntityTypeIn, int levels)
    {
        super(tileEntityTypeIn);
        maxLevel = levels - 1;
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void tick()
    {
        if (!init)
        {
            LEVEL = ((UpgradableBlock) world.getBlockState(pos).getBlock()).LEVEL;
            init = true;
        }
    }

    @Override
    public void setLevel(int lev)
    {
        level = lev;
    }

    @Override
    public boolean upgrade()
    {
        if (level < maxLevel)
        {
            level++;
            world.setBlockState(pos, getBlockState().with(LEVEL, level), 2);
            upgradeResets();
            markDirty();

            return true;
        }

        return false;
    }

    protected void upgradeResets(){}

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putInt("level", level);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        level = compound.getInt("level");
    }
}