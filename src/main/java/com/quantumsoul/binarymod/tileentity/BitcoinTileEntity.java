package com.quantumsoul.binarymod.tileentity;

import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.init.TileEntityInit;
import com.quantumsoul.binarymod.network.packet.SBtcResetValuePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import static com.quantumsoul.binarymod.util.BitcoinUtils.getBitcoinStacks;
import static com.quantumsoul.binarymod.util.WorldUtils.dropStacks;

public class BitcoinTileEntity extends UpgradableTileEntity
{
    private static final double MINUTE = 1200D;

    private double value = 0.0D;

    public BitcoinTileEntity()
    {
        super(TileEntityInit.BITCOIN_MINER.get(), 4);
    }

    //=================================================== PROCESS ===================================================
    @Override
    public void tick()
    {
        super.tick();

        value += 0.5D * Math.pow(7, level) / MINUTE;
    }

    @Override
    public boolean execute(PlayerEntity player)
    {
        if (value >= 1.0D)
        {
            for (ItemStack stack : getBitcoinStacks(value))
                player.addItemStackToInventory(stack);

            value = value % 1.0D;
            NetworkInit.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SBtcResetValuePacket(pos, (float)value));

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
