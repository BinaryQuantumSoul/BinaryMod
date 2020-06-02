package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.block.UpgradableBlock;
import com.quantumsoul.binarymod.init.ItemInit;
import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.init.TileEntityInit;
import com.quantumsoul.binarymod.network.packet.BtcResetValuePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

import static com.quantumsoul.binarymod.util.BitcoinUtils.getBitcoinStacks;
import static com.quantumsoul.binarymod.world.WorldUtils.dropStacks;

public class BitcoinTileEntity extends MachineTileEntity implements IUpgradableMachine
{
    private static final double MINUTE = 1200D;

    private int level = 0;
    private double value = 0.0D;

    private IntegerProperty LEVEL;
    private boolean init = false;

    public BitcoinTileEntity()
    {
        super(TileEntityInit.BITCOIN_MINER.get());
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

        value += 0.5D * Math.pow(7, level) / MINUTE;
    }

    @Override
    public boolean upgrade()
    {
        int maxLevel = 3;
        if (level < maxLevel)
        {
            level++;
            world.setBlockState(pos, getBlockState().with(LEVEL, level), 3);
            markDirty();
        }

        return false;
    }

    @Override
    public void setLevel(int level)
    {
        this.level = level;
    }

    @Override
    public boolean execute(PlayerEntity player)
    {
        if (value >= 1.0D)
        {
            for (ItemStack stack : getBitcoinStacks(value))
                player.addItemStackToInventory(stack);

            value = value % 1.0D;
            NetworkInit.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new BtcResetValuePacket(pos, (float)value));

            return true;
        }

        return false;
    }

    @Override
    public void drop(BlockPos pos)
    {
        if (value >= 1.0D)
            dropStacks(world, pos, getBitcoinStacks(value));
    }

    public double getValue()
    {
        return value;
    }

    public void resetValue(double remainder)
    {
        value = remainder;
    }

    //=================================================== DATA ===================================================
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        compound.putInt("level", level);
        compound.putDouble("value", value);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        level = compound.getInt("level");
        value = compound.getDouble("value");
    }
}
