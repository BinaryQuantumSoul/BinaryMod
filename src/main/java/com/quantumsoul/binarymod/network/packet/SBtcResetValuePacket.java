package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.tileentity.BitcoinTileEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SBtcResetValuePacket
{
    private final BlockPos pos;
    private final float rem;

    public SBtcResetValuePacket(BlockPos position, float remainder)
    {
        pos = position;
        rem = remainder;
    }

    public static void serialize(SBtcResetValuePacket packet, PacketBuffer buf)
    {
        buf.writeBlockPos(packet.pos);
        buf.writeFloat(packet.rem);
    }

    public static SBtcResetValuePacket deserialize(PacketBuffer buf)
    {
        BlockPos pos = buf.readBlockPos();
        float rem = buf.readFloat();

        return new SBtcResetValuePacket(pos, rem);
    }

    public static void handle(SBtcResetValuePacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            TileEntity te = ((ClientPlayNetHandler)context.get().getNetworkManager().getNetHandler()).getWorld().getTileEntity(packet.pos);
            if (te instanceof BitcoinTileEntity)
                ((BitcoinTileEntity) te).resetValue(packet.rem);
        });

        context.get().setPacketHandled(true);
    }
}
