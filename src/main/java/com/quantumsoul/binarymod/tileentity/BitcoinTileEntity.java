package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.init.TileEntityInit;
import com.quantumsoul.binarymod.network.packet.SBtcResetValuePacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.quantumsoul.binarymod.util.BitcoinUtils.getBitcoinStacks;
import static com.quantumsoul.binarymod.util.MachineUtils.L_BITCOIN;
import static com.quantumsoul.binarymod.util.WorldUtils.dropStacks;

public class BitcoinTileEntity extends UpgradableTileEntity implements IExecutableMachine, IDroppableMachine, ITickableTileEntity
{
    private static final double MINUTE = 1200D;

    private double value = 0.0D;

    public BitcoinTileEntity()
    {
        super(TileEntityInit.BITCOIN_MINER.get(), L_BITCOIN);
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void tick()
    {
        value += L_BITCOIN.get(level) / MINUTE;
    }

    @Override
    public boolean execute(ServerPlayerEntity player)
    {
        if (value >= 1.0D)
        {
            for (ItemStack stack : getBitcoinStacks(value))
                player.addItemStackToInventory(stack);

            value = value % 1.0D;
            NetworkInit.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SBtcResetValuePacket(pos, (float)value));

            return true;
        }

        return false;
    }

    @Override
    public void drop(World worldIn, BlockPos pos)
    {
        if (value >= 1.0D)
            dropStacks(worldIn, pos, getBitcoinStacks(value));
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

        compound.putDouble("value", value);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        value = compound.getDouble("value");
    }
}
