package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.util.MachineUtils;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public class UpgradableTileEntity extends MachineTileEntity implements IUpgradableMachine
{
    public final MachineUtils.LevelInfo levelInfo;
    public int level = 0;

    public UpgradableTileEntity(TileEntityType<?> tileEntityTypeIn, MachineUtils.LevelInfo levels)
    {
        super(tileEntityTypeIn);
        levelInfo = levels;
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void setLevel(int lev)
    {
        level = lev;
    }

    @Override
    public boolean upgrade()
    {
        if (level < levelInfo.getMax())
        {
            level++;
            world.setBlockState(pos, getBlockState().with(levelInfo.level, level), 2);
            upgradeResets();
            markDirty();

            return true;
        }

        return false;
    }

    protected void upgradeResets(){}

    @Override
    public ITextComponent getLevelMessage()
    {
        return levelInfo.formatLevel(level);
    }

    @Override
    public ITextComponent getInfoMessage()
    {
        return levelInfo.formatInfo(level);
    }

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putInt("level", level);

        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);

        level = compound.getInt("level");
    }
}