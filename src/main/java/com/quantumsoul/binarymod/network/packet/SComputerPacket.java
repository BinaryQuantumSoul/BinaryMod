package com.quantumsoul.binarymod.network.packet;

import com.quantumsoul.binarymod.init.NetworkInit;
import com.quantumsoul.binarymod.tileentity.ComputerTileEntity;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SComputerPacket
{
    private final BlockPos pos;
    private final boolean load;

    public SComputerPacket(BlockPos pos, boolean load)
    {
        this.pos = pos;
        this.load = load;
    }

    public static void serialize(SComputerPacket packet, PacketBuffer buf)
    {
        buf.writeBlockPos(packet.pos);
        buf.writeBoolean(packet.load);
    }

    public static SComputerPacket deserialize(PacketBuffer buf)
    {
        return new SComputerPacket(buf.readBlockPos(), buf.readBoolean());
    }

    public static void handle(SComputerPacket packet, Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
        {
            TileEntity te = ((ClientPlayNetHandler)context.get().getNetworkManager().getNetHandler()).getWorld().getTileEntity(packet.pos);
            if (te instanceof ComputerTileEntity)
            {
                ((ComputerTileEntity) te).load(packet.load);
                NetworkInit.CHANNEL.sendToServer(new COpenGuiPacket(packet.pos));
            }
         });

        context.get().setPacketHandled(true);
    }
}
